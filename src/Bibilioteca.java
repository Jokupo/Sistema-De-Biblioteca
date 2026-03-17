
import java.util.ArrayList;

public class Bibilioteca {
	private ArrayList<Usuario> usuarios;
	private ArrayList<Livro> livros;
	
	public Bibilioteca() {
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
		usuarios.add(usuario);
		System.out.println("Usuário adicionado: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
	}
	public void adicionarLivro(Livro livro) {
		livros.add(livro);
		System.out.println("Livro adicionado: " + livro.getTitulo() + " por " + livro.getAutor());
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
}
