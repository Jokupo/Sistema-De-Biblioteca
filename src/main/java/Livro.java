
public class Livro {

	private String titulo;
	private String autor;
	private String isbn;
	private String dataPublicacao;
	private String editora;
	private boolean disponibilidade;
	
	public Livro(String titulo, String autor, String isbn, String dataPublicacao, String editora) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.dataPublicacao = dataPublicacao;
		this.editora = editora;
		this.disponibilidade = true; //Por padrão, o livro está disponível
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public boolean isDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(boolean disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	
	public String toString() {
		return "Livro [titulo=" + titulo + ", autor=" + autor + ", isbn=" + isbn + ", dataPublicacao="
				+ dataPublicacao + ", editora=" + editora + ", disponibilidade=" + disponibilidade + "]";
	}
}
