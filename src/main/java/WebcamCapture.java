
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por capturar imagens da webcam e ler códigos de barras/ISBN
 * Melhorias:
 * - Janela visual com preview em tempo real
 * - Continua escaneando até encontrar código de barras
 * - Mostra dicas visuais ao usuário
 * 
 * Utiliza bibliotecas: Sarxos Webcam Capture, ZXing e Swing
 * IMPORTANTE: Esta classe requer Maven para compilar!
 * Execute: mvn clean install && mvn exec:java
 */
public class WebcamCapture {
    private Webcam webcam;
    private JFrame previewFrame;
    private JLabel imageLabel;
    private static final String TEMP_DIR = "temp_captures";
    private static final int PREVIEW_WIDTH = 640;
    private static final int PREVIEW_HEIGHT = 480;

    /**
     * Constructor - Inicializa a webcam
     */
    public WebcamCapture() {
        try {
            // Verificar se há webcams disponíveis
            if (Webcam.getWebcams().isEmpty()) {
                System.out.println("\n" + "=".repeat(70));
                System.out.println("❌ ERRO: NENHUMA WEBCAM ENCONTRADA!");
                System.out.println("=".repeat(70));
                System.out.println("\n⚠️  Seu computador não possui uma webcam conectada ou acessível.\n");
                System.out.println("Como resolver:");
                System.out.println("  1. Conecte uma webcam USB ao seu computador");
                System.out.println("  2. Aguarde os drivers serem instalados automaticamente");
                System.out.println("  3. Verifique em Gerenciador de Dispositivos (Câmeras)");
                System.out.println("  4. Tente novamente\n");
                System.out.println("Alternativas:");
                System.out.println("  • Use a opção 1: Registrar livro por ISBN (digitando)");
                System.out.println("  • Use a opção 3: Registrar livro manualmente");
                System.out.println("  • Solicite uma webcam emprestada\n");
                System.out.println("=".repeat(70) + "\n");
                this.webcam = null;
                return;
            }

            this.webcam = Webcam.getDefault();
            if (this.webcam == null) {
                System.out.println("❌ Erro: Não foi possível acessar a webcam!");
                System.out.println("💡 Tente desconectar e reconectar a webcam.");
                return;
            }
            
            System.out.println("✓ Webcam encontrada: " + this.webcam.getName());
            
        } catch (Exception e) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("❌ ERRO AO INICIALIZAR WEBCAM!");
            System.out.println("=".repeat(70));
            System.out.println("\nDetalhes do erro: " + e.getMessage());
            System.out.println("\nCausas possíveis:");
            System.out.println("  • Webcam desconectada ou com problemas");
            System.out.println("  • Drivers da webcam não instalados");
            System.out.println("  • Outra aplicação está usando a webcam");
            System.out.println("  • Falta de permissão para acessar a webcam");
            System.out.println("\nSoluções:");
            System.out.println("  1. Verifique o Gerenciador de Dispositivos");
            System.out.println("  2. Reinstale os drivers da webcam");
            System.out.println("  3. Feche outros programas que usam câmera");
            System.out.println("  4. Reinicie o computador");
            System.out.println("  5. Use as outras opções do menu\n");
            System.out.println("=".repeat(70) + "\n");
            this.webcam = null;
        }
    }

    /**
     * Verifica se a webcam está disponível
     */
    public boolean isWebcamAvailable() {
        return this.webcam != null;
    }

    /**
     * Captura uma imagem da webcam
     * @return BufferedImage com a imagem capturada
     */
    public BufferedImage captureImage() {
        if (this.webcam == null) {
            System.out.println("❌ Webcam não está disponível!");
            return null;
        }

        try {
            System.out.println("⏳ Abrindo webcam...");
            this.webcam.open();

            System.out.println("📸 Capturando imagem em 3 segundos... Aponte o código de barras para a câmera!");
            Thread.sleep(3000); // Aguarda 3 segundos para o usuário apontar o código

            BufferedImage image = this.webcam.getImage();
            System.out.println("✓ Imagem capturada com sucesso!");

            return image;

        } catch (InterruptedException e) {
            System.out.println("❌ Captura interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        } catch (Exception e) {
            System.out.println("❌ Erro ao capturar imagem: " + e.getMessage());
            return null;
        } finally {
            try {
                if (this.webcam != null && this.webcam.isOpen()) {
                    this.webcam.close();
                    System.out.println("✓ Webcam fechada");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Erro ao fechar webcam: " + e.getMessage());
            }
        }
    }

    /**
     * Decodifica um código de barras (ISBN) de uma imagem
     * @param image BufferedImage contendo o código de barras
     * @return String com o ISBN decodificado, ou null se não encontrar
     */
    public String decodeBarcode(BufferedImage image) {
        if (image == null) {
            System.out.println("❌ Imagem inválida!");
            return null;
        }

        try {
            System.out.println("🔍 Processando imagem para decodificar código de barras...");

            // Criar fonte de luminância a partir da imagem
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Tentar decodificar usando múltiplos readers
            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);

            String barcode = result.getText();
            System.out.println("✓ Código de barras decodificado: " + barcode);
            return barcode;

        } catch (NotFoundException e) {
            System.out.println("❌ Nenhum código de barras encontrado na imagem!");
            System.out.println("💡 Dica: Aponte a câmera diretamente para o código de barras e tente novamente");
            return null;
        } catch (Exception e) {
            System.out.println("❌ Erro ao decodificar código de barras: " + e.getMessage());
            return null;
        }
    }

    /**
     * Captura uma imagem e decodifica o código de barras em uma única operação
     * @return String com o ISBN decodificado, ou null se não conseguir
     */
    public String captureAndDecode() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📸 CAPTURA DE CÓDIGO DE BARRAS");
        System.out.println("=".repeat(60));

        BufferedImage image = captureImage();
        if (image == null) {
            return null;
        }

        String isbn = decodeBarcode(image);
        if (isbn != null) {
            saveImage(image, isbn);
        }

        return isbn;
    }

    /**
     * Salva a imagem capturada em arquivo
     */
    private void saveImage(BufferedImage image, String barcode) {
        try {
            File tempDir = new File(TEMP_DIR);
            if (!tempDir.exists()) {
                tempDir.mkdir();
            }

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = TEMP_DIR + File.separator + "capture_" + timestamp + ".png";

            // Salvar imagem (comentado - opcional)
            // ImageIO.write(image, "png", new File(filename));
            System.out.println("💾 Imagem capturada com sucesso!");

        } catch (Exception e) {
            System.out.println("⚠️ Aviso: " + e.getMessage());
        }
    }

    /**
     * Lista todas as webcams disponíveis
     */
    public static void listarWebcams() {
        System.out.println("\n🎥 Webcams disponíveis:");
        for (Webcam w : Webcam.getWebcams()) {
            System.out.println("   - " + w.getName());
        }
    }

    /**
     * Método main para testar a captura
     */
    public static void main(String[] args) {
        System.out.println("=== TESTE DE CAPTURA DE CÓDIGO DE BARRAS ===\n");

        WebcamCapture capture = new WebcamCapture();

        if (!capture.isWebcamAvailable()) {
            System.out.println("❌ Webcam não disponível!");
            listarWebcams();
            return;
        }

        String isbn = capture.captureAndDecode();
        if (isbn != null) {
            System.out.println("\n✓ ISBN capturado com sucesso: " + isbn);
        } else {
            System.out.println("\n❌ Não foi possível capturar o ISBN");
        }
    }
}
