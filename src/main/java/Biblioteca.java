
import java.util.ArrayList;

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
	
	public void adicionarUsuario(Usuario usuario) {
		if (usuario == null) {
			System.out.println("Erro: Usuário não pode ser nulo!");
			return;
		}
		usuarios.add(usuario);
		System.out.println("✓ Usuário adicionado: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
	}
	
	public void adicionarLivro(Livro livro) {
		if (livro == null || livro.getIsbn().isEmpty()) {
			System.out.println("Erro: Livro ou ISBN não pode ser vazio!");
			return;
		}
		if (buscarPorIsbn(livro.getIsbn()) != null) {
			System.out.println("Erro: Já existe um livro com este ISBN!");
			return;
		}
		livros.add(livro);
		System.out.println("✓ Livro adicionado: " + livro.getTitulo() + " por " + livro.getAutor());
	}
	
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
	
	public void procurarLivro(String titulo) {
		for (Livro livro : livros) {
			if (livro.getTitulo().equalsIgnoreCase(titulo)) {
				System.out.println("Livro encontrado: " + livro.getTitulo() + " por " + livro.getAutor());
				return;
			}
		}
		System.out.println("Livro não encontrado: " + titulo);
	}
	
	public void procurarUsuario(String email) {
		for (Usuario usuario : usuarios) {
			if (usuario.getEmail().equalsIgnoreCase(email)) {
				System.out.println("Usuário encontrado: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
				return;
			}
		}
		System.out.println("Usuário não encontrado: " + email);
	}
	
	public void listarLivros() {
		if (livros.isEmpty()) {
			System.out.println("\n❌ Nenhum livro cadastrado.");
			return;
		}
		System.out.println("\n=== LIVROS CADASTRADOS ===");
		System.out.println("Total de livros: " + livros.size());
		System.out.println("─".repeat(80));
		for (int i = 0; i < livros.size(); i++) {
			Livro livro = livros.get(i);
			System.out.printf("%d. %-30s | Autor: %-20s | ISBN: %s%n", 
				(i+1), livro.getTitulo(), livro.getAutor(), livro.getIsbn());
		}
		System.out.println("─".repeat(80));
	}
}
