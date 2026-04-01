import java.util.ArrayList;

/**
 * Classe que gerencia a biblioteca
 * Mantém listas de usuários e livros
 */
public class Biblioteca {
	private ArrayList<Usuario> usuarios;
	private ArrayList<Livro> livros;
	
	public Biblioteca() {
		usuarios = new ArrayList<>();
		livros = new ArrayList<>();
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public ArrayList<Livro> getLivros() {
		return livros;
	}

	public void setLivros(ArrayList<Livro> livros) {
		this.livros = livros;
	}
	
	/**
	 * Adiciona um novo usuário à biblioteca com validações
	 */
	public void adicionarUsuario(Usuario usuario) {
		if (usuario == null) {
			System.out.println("❌ Erro: Usuário não pode ser nulo!");
			return;
		}
		usuarios.add(usuario);
		System.out.println("✓ Usuário adicionado: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
	}
	
	/**
	 * Adiciona um novo livro à biblioteca com validações
	 */
	public void adicionarLivro(Livro livro) {
		if (livro == null || livro.getIsbn().isEmpty()) {
			System.out.println("❌ Erro: Livro ou ISBN não pode ser vazio!");
			return;
		}
		if (buscarPorIsbn(livro.getIsbn()) != null) {
			System.out.println("❌ Erro: Já existe um livro com este ISBN!");
			return;
		}
		livros.add(livro);
		System.out.println("✓ Livro adicionado: " + livro.getTitulo() + " por " + livro.getAutor());
	}
	
	/**
	 * Busca um livro pelo ISBN (retorna o objeto)
	 */
	public Livro buscarPorIsbn(String isbn) {
		if (isbn == null || isbn.isEmpty()) {
			return null;
		}
		for (Livro livro : livros) {
			if (livro.getIsbn().equalsIgnoreCase(isbn)) {
				return livro;
			}
		}
		return null;
	}
	
	/**
	 * Busca um livro pelo título e imprime resultado
	 */
	public void procurarLivro(String titulo) {
		for (Livro livro : livros) {
			if (livro.getTitulo().equalsIgnoreCase(titulo)) {
				System.out.println("✓ Livro encontrado: " + livro.getTitulo() + " por " + livro.getAutor());
				return;
			}
		}
		System.out.println("❌ Livro não encontrado: " + titulo);
	}
	
	/**
	 * Busca um usuário pelo email e retorna o objeto
	 */
	public Usuario buscarUsuarioPorEmail(String email) {
		if (email == null || email.isEmpty()) {
			return null;
		}
		for (Usuario usuario : usuarios) {
			if (usuario.getEmail().equalsIgnoreCase(email)) {
				return usuario;
			}
		}
		return null;
	}
	
	/**
	 * Busca um usuário pelo email e imprime resultado
	 */
	public void procurarUsuario(String email) {
		Usuario usuario = buscarUsuarioPorEmail(email);
		if (usuario != null) {
			System.out.println("✓ Usuário encontrado: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
		} else {
			System.out.println("❌ Usuário não encontrado: " + email);
		}
	}
	
	/**
	 * Lista todos os livros cadastrados com formatação
	 */
	public void listarLivros() {
		if (livros.isEmpty()) {
			System.out.println("\n❌ Nenhum livro cadastrado.");
			return;
		}
		System.out.println("\n" + "=".repeat(80));
		System.out.println("=== LIVROS CADASTRADOS ===");
		System.out.println("Total de livros: " + livros.size());
		System.out.println("─".repeat(80));
		for (int i = 0; i < livros.size(); i++) {
			Livro livro = livros.get(i);
			String status = livro.isDisponibilidade() ? "Disponível" : "Emprestado";
			System.out.printf("%d. %-30s | Autor: %-20s | ISBN: %-13s | %s%n", 
				(i+1), livro.getTitulo(), livro.getAutor(), livro.getIsbn(), status);
		}
		System.out.println("─".repeat(80));
	}
}
