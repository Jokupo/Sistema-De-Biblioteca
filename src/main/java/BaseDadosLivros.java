import java.util.HashMap;
import java.util.Map;

/**
 * Classe que simula um banco de dados de livros com ISBN
 * Permite buscar informações de livros automaticamente pelo ISBN
 */
public class BaseDadosLivros {
	private static Map<String, LivroInfo> baseDados;
	
	static {
		baseDados = new HashMap<>();
		
		// Adicionando alguns livros de exemplo
		baseDados.put("978-0451524935", new LivroInfo(
			"1984",
			"George Orwell",
			"1949",
			"Companhia das Letras"
		));
		
		baseDados.put("978-0062316097", new LivroInfo(
			"O Hobbit",
			"J.R.R. Tolkien",
			"1937",
			"Intrínseca"
		));
		
		baseDados.put("978-8532511010", new LivroInfo(
			"Dom Casmurro",
			"Machado de Assis",
			"1899",
			"Companhia das Letras"
		));
		
		baseDados.put("978-8535930689", new LivroInfo(
			"O Cortiço",
			"Aluísio Azevedo",
			"1890",
			"Companhia das Letras"
		));
		
		baseDados.put("978-0451526342", new LivroInfo(
			"Orgulho e Preconceito",
			"Jane Austen",
			"1813",
			"Editora Rocco"
		));
		
		baseDados.put("978-8501045534", new LivroInfo(
			"Memórias Póstumas de Brás Cubas",
			"Machado de Assis",
			"1881",
			"Companhia das Letras"
		));
	}
	
	/**
	 * Busca um livro pela ISBN
	 * @param isbn Código ISBN do livro
	 * @return LivroInfo com os dados do livro, ou null se não encontrado
	 */
	public static LivroInfo buscarPorIsbn(String isbn) {
		if (isbn == null || isbn.isEmpty()) {
			return null;
		}
		return baseDados.get(isbn);
	}
	
	/**
	 * Verifica se um ISBN existe na base de dados
	 */
	public static boolean isbnExiste(String isbn) {
		return baseDados.containsKey(isbn);
	}
	
	/**
	 * Retorna a quantidade de livros na base de dados
	 */
	public static int getTotalLivros() {
		return baseDados.size();
	}
	
	/**
	 * Lista todos os ISBNs disponíveis
	 */
	public static void listarTodosIsbn() {
		System.out.println("\n=== ISBNs DISPONÍVEIS NO SISTEMA ===");
		int contador = 1;
		for (String isbn : baseDados.keySet()) {
			LivroInfo info = baseDados.get(isbn);
			System.out.println(contador + ". ISBN: " + isbn);
			System.out.println("   " + info.titulo + " - " + info.autor + " (" + info.dataPublicacao + ")");
			contador++;
		}
	}
	
	/**
	 * Classe interna para armazenar informações do livro
	 */
	public static class LivroInfo {
		public String titulo;
		public String autor;
		public String dataPublicacao;
		public String editora;
		
		public LivroInfo(String titulo, String autor, String dataPublicacao, String editora) {
			this.titulo = titulo;
			this.autor = autor;
			this.dataPublicacao = dataPublicacao;
			this.editora = editora;
		}
		
		@Override
		public String toString() {
			return titulo + " - " + autor + " (" + dataPublicacao + ") - " + editora;
		}
	}
}
