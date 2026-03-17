
public class Usuario {
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String telefone;
	private String LivroEmprestado; // Para armazenar o título do livro emprestado, se houver

	public Usuario(String nome, String email, String senha, String cpf, String telefone) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.telefone = telefone;
		this.LivroEmprestado = null; // Inicialmente vazio
		
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLivroEmprestado() {
		return LivroEmprestado;
	}

	public void setLivroEmprestado(String livroEmprestado) {
		LivroEmprestado = livroEmprestado;
	}
	public boolean emprestarLivro(String tituloLivro) {
		if (this.LivroEmprestado == null) {
			this.LivroEmprestado = tituloLivro;
			return true; // Empréstimo bem-sucedido
		} else {
			return false; // O usuário já tem um livro emprestado
		}
	}
	
	public boolean devolverLivro() {
		if (this.LivroEmprestado != null) {
			this.LivroEmprestado = null;
			return true; // Devolução bem-sucedida
		} else {
			return false; // O usuário não tem um livro para devolver
		}
	}
}
