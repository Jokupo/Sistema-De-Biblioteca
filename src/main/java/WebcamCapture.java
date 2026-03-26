import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.common.GlobalHistogramBinarizer;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import javax.imageio.ImageIO; 
import javax.swing.*;

public class WebcamCapture {
    private Webcam webcam;
    private static final String TEMP_DIR = "temp_captures";

    public WebcamCapture() {
        try {
            if (Webcam.getWebcams().isEmpty()) {
                System.out.println("\n❌ ERRO: NENHUMA WEBCAM ENCONTRADA!");
                this.webcam = null;
                return;
            }
            this.webcam = Webcam.getDefault();
            this.webcam.setViewSize(new Dimension(640, 480));
            System.out.println("✓ Webcam pronta: " + this.webcam.getName());
        } catch (Exception e) {
            System.out.println("❌ ERRO AO INICIALIZAR WEBCAM: " + e.getMessage());
            this.webcam = null;
        }
    }

    public boolean isWebcamAvailable() {
        return this.webcam != null;
    }

    public String decodeBarcode(BufferedImage image) {
        if (image == null) return null;
        
        String result = tryDecoding(image, true);
        if (result != null) return result;
        
        return tryDecoding(image, false);
    }
    
    private String tryDecoding(BufferedImage image, boolean useHybrid) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap;
            
            if (useHybrid) {
                bitmap = new BinaryBitmap(new HybridBinarizer(source));
            } else {
                bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
            }

            Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.of(BarcodeFormat.EAN_13, BarcodeFormat.CODE_128, BarcodeFormat.QR_CODE));
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);

            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap, hints);
            return result.getText();
        } catch (NotFoundException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String captureAndDecode() {
        if (!isWebcamAvailable()) return null;

        JFrame window = null;
        try {
            if (this.webcam.isOpen()) {
                this.webcam.close();
            }
            Thread.sleep(500);
            this.webcam.open();

            WebcamPanel panel = new WebcamPanel(this.webcam);
            
            // --- INÍCIO DO OVERLAY INTELIGENTE ---
            panel.setPainter(new WebcamPanel.Painter() {
                private java.awt.Rectangle ultimoRetanguloValido = null;

                @Override
                public void paintPanel(WebcamPanel panel, java.awt.Graphics2D g2) {
                    panel.getDefaultPainter().paintPanel(panel, g2);
                }

                @Override
                public void paintImage(WebcamPanel panel, BufferedImage image, java.awt.Graphics2D g2) {
                    panel.getDefaultPainter().paintImage(panel, image, g2);
                    
                    String isbnAtual = decodeBarcode(image);
                    if (isbnAtual != null) {
                        try {
                            MultiFormatReader reader = new MultiFormatReader();
                            LuminanceSource source = new BufferedImageLuminanceSource(image);
                            BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
                            
                            Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
                            hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.of(BarcodeFormat.EAN_13));
                            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

                            Result zxingResult = reader.decode(bitmap, hints);
                            ResultPoint[] points = zxingResult.getResultPoints();
                            
                            if (points != null && points.length >= 2) {
                                int x = (int) points[0].getX();
                                int y = (int) points[0].getY();
                                int width = (int) (points[1].getX() - points[0].getX());
                                int height = 150; 

                                ultimoRetanguloValido = new java.awt.Rectangle(x, y - 20, width, height);
                                g2.setColor(java.awt.Color.GREEN);
                                g2.setStroke(new java.awt.BasicStroke(4));
                                g2.draw(ultimoRetanguloValido);
                                
                                g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
                                g2.drawString("DETECTADO: " + zxingResult.getText(), x, y - 30);
                            }
                        } catch (Exception e) {}
                    } else if (ultimoRetanguloValido != null) {
                        g2.setColor(java.awt.Color.WHITE);
                        g2.setStroke(new java.awt.BasicStroke(2));
                        g2.draw(ultimoRetanguloValido);
                        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
                        g2.drawString("Posicione o ISBN aqui", ultimoRetanguloValido.x, ultimoRetanguloValido.y - 10);
                    }
                }
            });
            // --- FIM DO OVERLAY INTELIGENTE ---

            panel.setFPSDisplayed(true);
            panel.setImageSizeDisplayed(true);
            panel.setMirrored(false);

            window = new JFrame("Scanner de Livros - Overlay Inteligente");
            window.add(panel);
            window.setResizable(false);
            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);

            System.out.println("📸 Scanner ativo! Aproxime o código de barras...");

            while (window.isDisplayable()) {
                BufferedImage image = this.webcam.getImage();
                if (image != null) {
                    String isbn = decodeBarcode(image);
                    if (isbn != null) {
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("✓ Lido: " + isbn);
                        saveImage(image, isbn);
                        
                        // Espera meio segundo pra você conseguir ver o quadrado verde na tela
                        Thread.sleep(500); 
                        window.dispose();
                        return isbn;
                    }
                }
                Thread.sleep(150);
            }
            return null;
        } catch (Exception e) {
            System.out.println("❌ Erro na captura: " + e.getMessage());
            return null;
        } finally {
            if (window != null && window.isDisplayable()) {
                window.dispose();
            }
            if (this.webcam != null && this.webcam.isOpen()) {
                this.webcam.close();
            }
        }
    }

    private void saveImage(BufferedImage image, String isbn) {
        try {
            File dir = new File(TEMP_DIR);
            if (!dir.exists()) dir.mkdirs();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File outputFile = new File(dir, "capture_" + isbn + "_" + timestamp + ".png");
            ImageIO.write(image, "PNG", outputFile);
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao salvar foto.");
        }
    }

    public static void main(String[] args) {
        WebcamCapture capture = new WebcamCapture();
        if (capture.isWebcamAvailable()) {
            capture.captureAndDecode();
        }
    }
}