import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Classe principal do Sistema de Biblioteca
 * Gerencia o menu e fluxo de navegação do aplicativo
 */
public class Main {
	private static Biblioteca biblioteca;
	private static Scanner scanner;
	private static WebcamCapture capture;
	
	// Constantes de mensagens
	private static final String MENU_INVALIDO = "❌ Opção inválida! Tente novamente.";
	private static final String SEPARADOR = "=".repeat(60);
	private static final String LINHA_FINA = "─".repeat(60);
	
	public static void main(String[] args) {
		inicializarSistema();
		exibirMenuPrincipal();
		finalizarSistema();
	}
	
	/**
	 * Inicializa os componentes do sistema
	 */
	private static void inicializarSistema() {
		biblioteca = new Biblioteca();
		scanner = new Scanner(System.in);
		capture = new WebcamCapture();
		System.out.println("✓ Sistema de Biblioteca iniciado com sucesso!");
	}
	
	/**
	 * Finaliza o sistema fechando recursos
	 */
	private static void finalizarSistema() {
		if (scanner != null) {
			scanner.close();
		}
		System.out.println("\n✓ Encerrando o sistema. Até logo!");
	}
	
	/**
	 * Menu principal com 3 áreas diferentes
	 */
	private static void exibirMenuPrincipal() {
		boolean executando = true;
		
		while (executando) {
			System.out.println("\n" + SEPARADOR);
			System.out.println("          SISTEMA DE BIBLIOTECA - MENU PRINCIPAL");
			System.out.println(SEPARADOR);
			System.out.println("1. 📚 REGISTRAR LIVROS");
			System.out.println("2. 👥 GERENCIAR USUÁRIOS");
			System.out.println("3. 🔄 EMPRESTAR/DEVOLVER LIVROS");
			System.out.println("4. 🔍 CONSULTAS");
			System.out.println("0. ❌ SAIR");
			System.out.println(SEPARADOR);
			System.out.print("Escolha uma opção: ");
			
			String opcao = scanner.nextLine().trim();
			
			switch (opcao) {
				case "1":
					menuRegistrarLivros();
					break;
				case "2":
					menuGerenciarUsuarios();
					break;
				case "3":
					menuEmprestarDevolverLivros();
					break;
				case "4":
					menuConsultas();
					break;
				case "0":
					executando = false;
					break;
				default:
					System.out.println(MENU_INVALIDO);
			}
		}
	}
	
	/**
	 * Menu para registrar livros (3 opções)
	 */
	private static void menuRegistrarLivros() {
		boolean voltarMenu = false;
		
		while (!voltarMenu) {
			System.out.println("\n" + SEPARADOR);
			System.out.println("              MENU REGISTRAR LIVROS");
			System.out.println(SEPARADOR);
			System.out.println("1. 📸 Registrar por WEBCAM (QR Code/Código de Barras)");
			System.out.println("2. 🔍 Registrar por ISBN (Buscar na Base)");
			System.out.println("3. ✏️  Registrar Manualmente");
			System.out.println("4. 📋 Listar Todos os Livros Cadastrados");
			System.out.println("5. 📂 Ver ISBNs Disponíveis para Registro");
			System.out.println("0. ⬅️  Voltar ao Menu Principal");
			System.out.println(SEPARADOR);
			System.out.print("Escolha uma opção: ");
			
			String opcao = scanner.nextLine().trim();
			
			switch (opcao) {
				case "1":
					registrarLivroComWebcam();
					break;
				case "2":
					registrarLivroPorIsbn();
					break;
				case "3":
					registrarLivroManual();
					break;
				case "4":
					listarLivros();
					break;
				case "5":
					BaseDadosLivros.listarTodosIsbn();
					break;
				case "0":
					voltarMenu = true;
					break;
				default:
					System.out.println(MENU_INVALIDO);
			}
		}
	}
	
	/**
	 * Menu para gerenciar usuários
	 */
	private static void menuGerenciarUsuarios() {
		boolean voltarMenu = false;
		
		while (!voltarMenu) {
			System.out.println("\n" + SEPARADOR);
			System.out.println("            MENU GERENCIAR USUÁRIOS");
			System.out.println(SEPARADOR);
			System.out.println("1. ➕ Registrar Novo Usuário");
			System.out.println("2. 📋 Listar Todos os Usuários");
			System.out.println("3. 🔍 Buscar Usuário por Email");
			System.out.println("0. ⬅️  Voltar ao Menu Principal");
			System.out.println(SEPARADOR);
			System.out.print("Escolha uma opção: ");
			
			String opcao = scanner.nextLine().trim();
			
			switch (opcao) {
				case "1":
					registrarUsuario();
					break;
				case "2":
					listarUsuarios();
					break;
				case "3":
					buscarUsuarioPorEmail();
					break;
				case "0":
					voltarMenu = true;
					break;
				default:
					System.out.println(MENU_INVALIDO);
			}
		}
	}
	
	/**
	 * Menu para emprestar e devolver livros
	 */
	private static void menuEmprestarDevolverLivros() {
		boolean voltarMenu = false;
		
		while (!voltarMenu) {
			System.out.println("\n" + SEPARADOR);
			System.out.println("          MENU EMPRESTAR/DEVOLVER LIVROS");
			System.out.println(SEPARADOR);
			System.out.println("1. 📤 Emprestar Livro");
			System.out.println("2. 📥 Devolver Livro");
			System.out.println("3. 📊 Ver Empréstimos de um Usuário");
			System.out.println("0. ⬅️  Voltar ao Menu Principal");
			System.out.println(SEPARADOR);
			System.out.print("Escolha uma opção: ");
			
			String opcao = scanner.nextLine().trim();
			
			switch (opcao) {
				case "1":
					emprestarLivro();
					break;
				case "2":
					devolverLivro();
					break;
				case "3":
					verEmprestimosUsuario();
					break;
				case "0":
					voltarMenu = true;
					break;
				default:
					System.out.println(MENU_INVALIDO);
			}
		}
	}
	
	/**
	 * Menu de consultas
	 */
	private static void menuConsultas() {
		boolean voltarMenu = false;
		
		while (!voltarMenu) {
			System.out.println("\n" + SEPARADOR);
			System.out.println("                MENU CONSULTAS");
			System.out.println(SEPARADOR);
			System.out.println("1. 🔍 Buscar Livro por Título");
			System.out.println("2. 🔍 Buscar Livro por ISBN");
			System.out.println("3. 📚 Listar Livros Disponíveis");
			System.out.println("4. 📚 Listar Livros Emprestados");
			System.out.println("0. ⬅️  Voltar ao Menu Principal");
			System.out.println(SEPARADOR);
			System.out.print("Escolha uma opção: ");
			
			String opcao = scanner.nextLine().trim();
			
			switch (opcao) {
				case "1":
					buscarPorTitulo();
					break;
				case "2":
					buscarPorIsbn();
					break;
				case "3":
					listarLivrosDisponiveis();
					break;
				case "4":
					listarLivrosEmprestados();
					break;
				case "0":
					voltarMenu = true;
					break;
				default:
					System.out.println(MENU_INVALIDO);
			}
		}
	}
	
	// ===== MÉTODOS DE REGISTRAR LIVROS =====
	
	private static void registrarLivroPorIsbn() {
		System.out.println("\n--- REGISTRAR LIVRO POR ISBN ---");
		System.out.print("Digite o ISBN do livro (ex: 978-8535929935): ");
		String isbn = scanner.nextLine().trim();
		
		if (isbn.isEmpty()) {
			System.out.println("❌ ISBN não pode ser vazio!");
			return;
		}
		
		if (biblioteca.buscarPorIsbn(isbn) != null) {
			System.out.println("❌ Livro com este ISBN já está cadastrado!");
			return;
		}
		
		try {
			APIBuscadorDelivro.LivroTemp livroTemp = APIBuscadorDelivro.buscarEConfirmarLivro(isbn);
			
			if (livroTemp != null) {
				Livro livro = new Livro(
					livroTemp.titulo,
					livroTemp.autor,
					livroTemp.isbn,
					livroTemp.ano,
					livroTemp.editora
				);
				biblioteca.adicionarLivro(livro);
			}
		} catch (Exception e) {
			System.out.println("❌ Erro ao processar ISBN: " + e.getMessage());
		}
	}
	
	private static void registrarLivroComWebcam() {
		System.out.println("\n--- REGISTRAR LIVRO COM WEBCAM ---");
		System.out.println("Aponte a câmera para o código de barras ou QR Code do livro...");
		
		try {
			String isbn = capture.captureAndDecode();
			
			if (isbn != null && !isbn.isEmpty()) {
				System.out.println("✓ ISBN detectado: " + isbn);
				
				if (biblioteca.buscarPorIsbn(isbn) != null) {
					System.out.println("❌ Livro com este ISBN já está cadastrado!");
					return;
				}
				
				APIBuscadorDelivro.LivroTemp livroTemp = APIBuscadorDelivro.buscarEConfirmarLivro(isbn);
				
				if (livroTemp != null) {
					Livro livro = new Livro(
						livroTemp.titulo,
						livroTemp.autor,
						livroTemp.isbn,
						livroTemp.ano,
						livroTemp.editora
					);
					biblioteca.adicionarLivro(livro);
				}
			} else {
				System.out.println("⚠️ Nenhum código detectado. Deseja registrar manualmente? (S/N)");
				String resposta = scanner.nextLine().trim().toUpperCase();
				
				if (resposta.equals("S")) {
					registrarLivroManual();
				}
			}
		} catch (Exception e) {
			System.out.println("⚠️ Erro ao capturar código: " + e.getMessage());
			System.out.println("Deseja registrar manualmente? (S/N)");
			String resposta = scanner.nextLine().trim().toUpperCase();
			
			if (resposta.equals("S")) {
				registrarLivroManual();
			}
		}
	}
	
	private static void registrarLivroManual() {
		System.out.println("\n--- REGISTRAR LIVRO MANUALMENTE ---");
		
		System.out.print("Título do livro: ");
		String titulo = scanner.nextLine().trim();
		
		System.out.print("Autor do livro: ");
		String autor = scanner.nextLine().trim();
		
		System.out.print("ISBN do livro: ");
		String isbn = scanner.nextLine().trim();
		
		System.out.print("Data de publicação (YYYY-MM-DD): ");
		String dataPublicacao = scanner.nextLine().trim();
		
		System.out.print("Editora do livro: ");
		String editora = scanner.nextLine().trim();
		
		if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
			System.out.println("❌ Título, autor e ISBN são obrigatórios!");
			return;
		}
		
		if (biblioteca.buscarPorIsbn(isbn) != null) {
			System.out.println("❌ Livro com este ISBN já está cadastrado!");
			return;
		}
		
		Livro livro = new Livro(titulo, autor, isbn, dataPublicacao, editora);
		biblioteca.adicionarLivro(livro);
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
		
		boolean encontrado = false;
		System.out.println("\n📚 Resultados da busca por título:");
		System.out.println(LINHA_FINA);
		
		for (Livro livro : biblioteca.getLivros()) {
			if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
				System.out.println("• " + livro.getTitulo() + " por " + livro.getAutor());
				System.out.println("  ISBN: " + livro.getIsbn() + " | Disponível: " + 
					(livro.isDisponibilidade() ? "Sim" : "Não"));
				encontrado = true;
			}
		}
		
		if (!encontrado) {
			System.out.println("❌ Nenhum livro encontrado com este título!");
		}
		System.out.println(LINHA_FINA);
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
			System.out.println("─".repeat(60));
			System.out.println("Título: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor());
			System.out.println("ISBN: " + livro.getIsbn());
			System.out.println("Data de Publicação: " + livro.getDataPublicacao());
			System.out.println("Editora: " + livro.getEditora());
			System.out.println("Disponibilidade: " + (livro.isDisponibilidade() ? "✓ Disponível" : "✗ Emprestado"));
			System.out.println("─".repeat(60));
		} else {
			System.out.println("❌ Livro não encontrado com este ISBN!");
		}
	}
	
	// ===== MÉTODOS DE GERENCIAR USUÁRIOS =====
	
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
		
		if (biblioteca.buscarUsuarioPorEmail(email) != null) {
			System.out.println("❌ Usuário com este email já existe!");
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
		
		System.out.println("\n" + LINHA_FINA);
		System.out.println("=== USUÁRIOS CADASTRADOS ===");
		System.out.println(LINHA_FINA);
		for (int i = 0; i < biblioteca.getUsuarios().size(); i++) {
			Usuario u = biblioteca.getUsuarios().get(i);
			System.out.println((i+1) + ". " + u.getNome() + " | Email: " + u.getEmail() + " | Telefone: " + u.getTelefone());
		}
		System.out.println(LINHA_FINA);
	}
	
	private static void buscarUsuarioPorEmail() {
		System.out.println("\n--- BUSCAR USUÁRIO POR EMAIL ---");
		System.out.print("Digite o email do usuário: ");
		String email = scanner.nextLine().trim();
		
		if (email.isEmpty()) {
			System.out.println("❌ Email não pode ser vazio!");
			return;
		}
		
		Usuario usuario = biblioteca.buscarUsuarioPorEmail(email);
		if (usuario != null) {
			System.out.println("\n✓ Usuário encontrado:");
			System.out.println(usuario);
		} else {
			System.out.println("❌ Usuário não encontrado!");
		}
	}
	
	// ===== MÉTODOS DE EMPRESTAR/DEVOLVER LIVROS =====
	
	private static void emprestarLivro() {
		System.out.println("\n--- EMPRESTAR LIVRO ---");
		
		if (biblioteca.getUsuarios().isEmpty() || biblioteca.getLivros().isEmpty()) {
			System.out.println("❌ Nenhum usuário ou livro cadastrado!");
			return;
		}
		
		System.out.print("Digite o email do usuário: ");
		String emailUsuario = scanner.nextLine().trim();
		
		Usuario usuario = biblioteca.buscarUsuarioPorEmail(emailUsuario);
		if (usuario == null) {
			System.out.println("❌ Usuário não encontrado!");
			return;
		}
		
		System.out.print("Digite o ISBN do livro: ");
		String isbn = scanner.nextLine().trim();
		
		Livro livro = biblioteca.buscarPorIsbn(isbn);
		if (livro == null) {
			System.out.println("❌ Livro não encontrado!");
			return;
		}
		
		if (!livro.isDisponibilidade()) {
			System.out.println("❌ Este livro não está disponível para empréstimo!");
			return;
		}
		
		usuario.emprestarLivro(livro);
	}
	
	private static void devolverLivro() {
		System.out.println("\n--- DEVOLVER LIVRO ---");
		
		if (biblioteca.getUsuarios().isEmpty()) {
			System.out.println("❌ Nenhum usuário cadastrado!");
			return;
		}
		
		System.out.print("Digite o email do usuário: ");
		String emailUsuario = scanner.nextLine().trim();
		
		Usuario usuario = biblioteca.buscarUsuarioPorEmail(emailUsuario);
		if (usuario == null) {
			System.out.println("❌ Usuário não encontrado!");
			return;
		}
		
		if (usuario.getLivrosEmprestados().isEmpty()) {
			System.out.println("❌ Este usuário não possui livros emprestados!");
			return;
		}
		
		System.out.println("\n📖 Livros emprestados por " + usuario.getNome() + ":");
		for (int i = 0; i < usuario.getLivrosEmprestados().size(); i++) {
			System.out.println((i+1) + ". " + usuario.getLivrosEmprestados().get(i).getTitulo() + 
				" (ISBN: " + usuario.getLivrosEmprestados().get(i).getIsbn() + ")");
		}
		
		System.out.print("\nDigite o ISBN do livro a devolver: ");
		String isbn = scanner.nextLine().trim();
		
		Livro livro = null;
		for (Livro l : usuario.getLivrosEmprestados()) {
			if (l.getIsbn().equalsIgnoreCase(isbn)) {
				livro = l;
				break;
			}
		}
		
		if (livro == null) {
			System.out.println("❌ Este livro não está emprestado para este usuário!");
			return;
		}
		
		usuario.devolverLivro(livro);
	}
	
	private static void verEmprestimosUsuario() {
		System.out.println("\n--- VER EMPRÉSTIMOS DO USUÁRIO ---");
		
		if (biblioteca.getUsuarios().isEmpty()) {
			System.out.println("❌ Nenhum usuário cadastrado!");
			return;
		}
		
		System.out.print("Digite o email do usuário: ");
		String emailUsuario = scanner.nextLine().trim();
		
		Usuario usuario = biblioteca.buscarUsuarioPorEmail(emailUsuario);
		if (usuario == null) {
			System.out.println("❌ Usuário não encontrado!");
			return;
		}
		
		usuario.listarEmprestimos();
	}
	
	// ===== MÉTODOS DE CONSULTAS =====
	
	private static void listarLivrosDisponiveis() {
		if (biblioteca.getLivros().isEmpty()) {
			System.out.println("\n❌ Nenhum livro cadastrado.");
			return;
		}
		
		System.out.println("\n" + LINHA_FINA);
		System.out.println("=== LIVROS DISPONÍVEIS ===");
		System.out.println(LINHA_FINA);
		
		int count = 0;
		for (Livro livro : biblioteca.getLivros()) {
			if (livro.isDisponibilidade()) {
				count++;
				System.out.printf("%d. %-30s | Autor: %-20s | ISBN: %s%n", 
					count, livro.getTitulo(), livro.getAutor(), livro.getIsbn());
			}
		}
		
		if (count == 0) {
			System.out.println("Nenhum livro disponível no momento.");
		}
		System.out.println(LINHA_FINA);
	}
	
	private static void listarLivrosEmprestados() {
		if (biblioteca.getLivros().isEmpty()) {
			System.out.println("\n❌ Nenhum livro cadastrado.");
			return;
		}
		
		System.out.println("\n" + LINHA_FINA);
		System.out.println("=== LIVROS EMPRESTADOS ===");
		System.out.println(LINHA_FINA);
		
		int count = 0;
		for (Livro livro : biblioteca.getLivros()) {
			if (!livro.isDisponibilidade()) {
				count++;
				System.out.printf("%d. %-30s | Autor: %-20s | ISBN: %s%n", 
					count, livro.getTitulo(), livro.getAutor(), livro.getIsbn());
			}
		}
		
		if (count == 0) {
			System.out.println("Nenhum livro emprestado no momento.");
		}
		System.out.println(LINHA_FINA);
	}
}
