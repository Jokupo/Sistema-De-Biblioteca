import java.util.ArrayList;

public class Usuario {
	private String nome;
	private String email;
	private String telefone;
	private ArrayList<Livro> livrosEmprestados;
	
	public Usuario(String nome, String email, String telefone) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.livrosEmprestados = new ArrayList<>();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public ArrayList<Livro> getLivrosEmprestados() {
		return livrosEmprestados;
	}
	
	public void emprestarLivro(Livro livro) {
		if (livro == null) {
			System.out.println("Erro: Livro não pode ser nulo!");
			return;
		}
		if (!livro.isDisponibilidade()) {
			System.out.println("Erro: Este livro não está disponível!");
			return;
		}
		livrosEmprestados.add(livro);
		livro.setDisponibilidade(false);
		System.out.println("✓ Livro '" + livro.getTitulo() + "' emprestado para " + nome);
	}
	
	public void devolverLivro(Livro livro) {
		if (livro == null) {
			System.out.println("Erro: Livro não pode ser nulo!");
			return;
		}
		if (livrosEmprestados.remove(livro)) {
			livro.setDisponibilidade(true);
			System.out.println("✓ Livro '" + livro.getTitulo() + "' devolvido por " + nome);
		} else {
			System.out.println("Erro: Este usuário não possui este livro emprestado!");
		}
	}
	
	public void listarEmprestimos() {
		if (livrosEmprestados.isEmpty()) {
			System.out.println(nome + " não possui livros emprestados.");
			return;
		}
		System.out.println("\n=== EMPRÉSTIMOS DE " + nome.toUpperCase() + " ===");
		for (int i = 0; i < livrosEmprestados.size(); i++) {
			System.out.println((i+1) + ". " + livrosEmprestados.get(i).getTitulo());
		}
	}
	
	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", email=" + email + ", telefone=" + telefone
				+ ", livrosEmprestados=" + livrosEmprestados.size() + "]";
	}
}