
public class Livro {

	private String titulo;
	private String autor;
	private String isbn;
	private String DataPublicacao;
	private String Editora;
	private boolean Disponibilidade;
	
	public Livro(String titulo, String autor, String isbn, String DataPublicacao, String Editora) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.DataPublicacao = DataPublicacao;
		this.Editora = Editora;
		this.Disponibilidade = true; //Por padrão, o livro está disponível
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
		return DataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		DataPublicacao = dataPublicacao;
	}

	public String getEditora() {
		return Editora;
	}

	public void setEditora(String editora) {
		Editora = editora;
	}

	public boolean isDisponibilidade() {
		return Disponibilidade;
	}

	public void setDisponibilidade(boolean disponibilidade) {
		Disponibilidade = disponibilidade;
	}
	
	public String toString() {
		return "Livro [titulo=" + titulo + ", autor=" + autor + ", isbn=" + isbn + ", DataPublicacao="
				+ DataPublicacao + ", Editora=" + Editora + ", Disponibilidade=" + Disponibilidade + "]";
	}
}
