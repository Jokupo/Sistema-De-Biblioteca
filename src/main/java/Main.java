import java.util.Scanner;
import javax.swing.JOptionPane;




public class Main {
	private static Biblioteca biblioteca;
	private static Scanner scanner;
	private static WebcamCapture capture; 
	
	public static void main(String[] args) {
		biblioteca = new Biblioteca();
		scanner = new Scanner(System.in);
		capture = new WebcamCapture();
		
		exibirMenuPrincipal();
		
		scanner.close();
	}
	
	private static void exibirMenuPrincipal() {
		boolean executando = true;
		
		while (executando) {
			System.out.println("\n" + "=".repeat(50));
			System.out.println("    SISTEMA DE BIBLIOTECA - MENU PRINCIPAL");
			System.out.println("=".repeat(50));
			System.out.println("1. Registrar livro por ISBN");
			System.out.println("2. Registrar livro por WEBCAM 📸");
			System.out.println("3. Registrar livro manualmente");
			System.out.println("4. Listar todos os livros");
			System.out.println("5. Buscar livro por título");
			System.out.println("6. Buscar livro por ISBN");
			System.out.println("7. Gerenciar usuários");
			System.out.println("8. Listar ISBNs disponíveis");
			System.out.println("0. Sair");
			System.out.println("=".repeat(50));
			System.out.print("Escolha uma opção: ");
			
			String opcao = scanner.nextLine().trim();
			
			switch (opcao) {
				case "1":
					registrarLivroPorIsbn();
					break;
				case "2":
					registrarLivroComWebcam();
					break;
				case "3":
					registrarLivroManual();
					break;
				case "4":
					listarLivros();
					break;
				case "5":
					buscarPorTitulo();
					break;
				case "6":
					buscarPorIsbn();
					break;
				case "7":
					gerenciarUsuarios();
					break;
				case "8":
					BaseDadosLivros.listarTodosIsbn();
					break;
				case "0":
					System.out.println("\n✓ Encerrando o sistema. Até logo!");
					executando = false;
					break;
				default:
					System.out.println("❌ Opção inválida! Tente novamente.");
			}
		}
	}
	
	private static void registrarLivroPorIsbn() {
		System.out.println("\n--- REGISTRAR LIVRO---");
		System.out.print("Digite o ISBN do livro (ex: 978-0451524935): ");
		String isbn = scanner.nextLine().trim();
		
		if (isbn.isEmpty()) {
			System.out.println("❌ ISBN não pode ser vazio!");
			return;
		}
		
		// Verifica se  o livro já existe na biblioteca
		if (biblioteca.buscarPorIsbn(isbn) != null) {
			System.out.println("❌ Este livro já foi registrado na biblioteca!");
			return;
		}
		
		// Busca na base de dados
		BaseDadosLivros.LivroInfo livroInfo = BaseDadosLivros.buscarPorIsbn(isbn);
		
		if (livroInfo == null) {
			System.out.println("❌ ISBN não encontrado na base de dados!");
			System.out.println("Livros disponíveis:");
			BaseDadosLivros.listarTodosIsbn();
			return;
		}
		
		// Exibe as informações encontradas
		System.out.println("\n✓ Livro encontrado na base de dados:");
		System.out.println("  Título: " + livroInfo.titulo);
		System.out.println("  Autor: " + livroInfo.autor);
		System.out.println("  Data de Publicação: " + livroInfo.dataPublicacao);
		System.out.println("  Editora: " + livroInfo.editora);
		
		// Cria e adiciona o livro
		Livro novoLivro = new Livro(
			livroInfo.titulo,
			livroInfo.autor,
			isbn,
			livroInfo.dataPublicacao,
			livroInfo.editora
		);
		
		biblioteca.adicionarLivro(novoLivro);
	}
	
	private static void registrarLivroComWebcam() {
		System.out.println("\n--- REGISTRAR LIVRO COM WEBCAM 📸 ---");
		System.out.println("Certifique-se de que sua webcam está conectada!");
		System.out.println("aponte o código de barras para a câmera...\n");
		System.out.print("Pressione ENTER para começar: ");
		scanner.nextLine();
		
		// Criar instância de captura
		
		if (!capture.isWebcamAvailable()) {
			System.out.println("❌ Webcam não encontrada!");
			System.out.println("💡 Alternativas:");
			System.out.println("   - Use a opção 1: Registrar livro por ISBN");
			System.out.println("   - Use a opção 3: Registrar livro manualmente");
			return;
		}
		
		// Capturar e decodificar
		String isbn = capture.captureAndDecode();
		
		if (isbn == null || isbn.isEmpty()) {
			System.out.println("❌ Não foi possível ler o código de barras!");
			System.out.println("💡 Dicas:");
			System.out.println("   - O código deve estar bem visível");
			System.out.println("   - A câmera deve estar bem iluminada");
			System.out.println("   - Tente apontar a câmera diretamente para o código");
			return;
		}
		
		// Limpar ISBN
		isbn = isbn.trim();
		
		APIBuscadorDelivro.LivroTemp livroConfirmado = APIBuscadorDelivro.buscarEConfirmarLivro(isbn);

		// 3. Se o usuário confirmou na janela Swing
		if (livroConfirmado != null) {
		    
		    // Transforma o "Candidato" no seu objeto "Livro" real do sistema
		    Livro novoLivro = new Livro(
		        livroConfirmado.titulo,
		        livroConfirmado.autor,
		        livroConfirmado.isbn,
		        livroConfirmado.ano,
		        livroConfirmado.editora
		    );
		    
		    // Salva na sua biblioteca (colection ou banco)
		    biblioteca.adicionarLivro(novoLivro);
		    
		    JOptionPane.showMessageDialog(null, "✓ Livro '" + novoLivro.getTitulo() + "' cadastrado com sucesso!");
	}
}
	private static void registrarLivroManual() {
		System.out.println("\n--- REGISTRAR LIVRO MANUALMENTE ---");
		
		System.out.print("Digite o título do livro: ");
		String titulo = scanner.nextLine().trim();
		
		System.out.print("Digite o autor do livro: ");
		String autor = scanner.nextLine().trim();
		
		System.out.print("Digite o ISBN do livro: ");
		String isbn = scanner.nextLine().trim();
		
		System.out.print("Digite a data de publicação (AAAA): ");
		String dataPublicacao = scanner.nextLine().trim();
		
		System.out.print("Digite a editora: ");
		String editora = scanner.nextLine().trim();
		
		if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
			System.out.println("❌ Título, autor e ISBN são obrigatórios!");
			return;
		}
		
		Livro novoLivro = new Livro(titulo, autor, isbn, dataPublicacao, editora);
		biblioteca.adicionarLivro(novoLivro);
	}
	
	private static void listarLivros() {
		biblioteca.listarLivros();
	}
	
	private static void buscarPorTitulo() {
		System.out.println("\n--- BUSCAR LIVRO POR TÍTULO ---");
		System.out.print("Digite o título do livro: ");
		String titulo = scanner.nextLine().trim();
		
		if (titulo.isEmpty()) {
			System.out.println("❌ Título não pode ser vazio!");
			return;
		}
		
		biblioteca.procurarLivro(titulo);
	}
	
	private static void buscarPorIsbn() {
		System.out.println("\n--- BUSCAR LIVRO POR ISBN ---");
		System.out.print("Digite o ISBN do livro: ");
		String isbn = scanner.nextLine().trim();
		
		if (isbn.isEmpty()) {
			System.out.println("❌ ISBN não pode ser vazio!");
			return;
		}
		
		Livro livro = biblioteca.buscarPorIsbn(isbn);
		if (livro != null) {
			System.out.println("\n✓ Livro encontrado:");
			System.out.println(livro);
		} else {
			System.out.println("❌ Livro não encontrado!");
		}
	}
	
	private static void gerenciarUsuarios() {
		boolean voltarMenu = false;
		
		while (!voltarMenu) {
			System.out.println("\n--- GERENCIAR USUÁRIOS ---");
			System.out.println("1. Registrar novo usuário");
			System.out.println("2. Listar usuários");
			System.out.println("0. Voltar ao menu principal");
			System.out.print("Escolha uma opção: ");
			
			String opcao = scanner.nextLine().trim();
			
			switch (opcao) {
				case "1":
					registrarUsuario();
					break;
				case "2":
					listarUsuarios();
					break;
				case "0":
					voltarMenu = true;
					break;
				default:
					System.out.println("❌ Opção inválida!");
			}
		}
	}
	
	private static void registrarUsuario() {
		System.out.println("\n--- REGISTRAR NOVO USUÁRIO ---");
		
		System.out.print("Digite o nome do usuário: ");
		String nome = scanner.nextLine().trim();
		
		System.out.print("Digite o email do usuário: ");
		String email = scanner.nextLine().trim();
		
		System.out.print("Digite o telefone do usuário: ");
		String telefone = scanner.nextLine().trim();
		
		if (nome.isEmpty() || email.isEmpty()) {
			System.out.println("❌ Nome e email são obrigatórios!");
			return;
		}
		
		Usuario novoUsuario = new Usuario(nome, email, telefone);
		biblioteca.adicionarUsuario(novoUsuario);
	}
	
	private static void listarUsuarios() {
		if (biblioteca.getUsuarios().isEmpty()) {
			System.out.println("\n❌ Nenhum usuário cadastrado.");
			return;
		}
		
		System.out.println("\n=== USUÁRIOS CADASTRADOS ===");
		for (int i = 0; i < biblioteca.getUsuarios().size(); i++) {
			System.out.println((i+1) + ". " + biblioteca.getUsuarios().get(i));
		}
	}
}