import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Bibilioteca biblioteca = new Bibilioteca();
		Scanner scanner = new Scanner(System.in);
		
		// Criando alguns usuários e livros para teste
		biblioteca.adicionarUsuario(new Usuario("Alice", "", "", "", ""));
		biblioteca.adicionarUsuario(new Usuario("Bob", "", "", "", ""));
		biblioteca.adicionarLivro(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "123456789", "1954", "Editora A"));
		biblioteca.adicionarLivro(new Livro("Harry Potter e a Pedra Filosofal", "J.K. Rowling", "987654321", "1997", "Editora B"));
		
		//menu de opções para o usuário
		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Alugar livro");
			System.out.println("2. Devolver livro");
			System.out.println("3. Procurar livro");
			System.out.println("4. Procurar usuário");
			System.out.println("5. Listar livros disponíveis");
			System.out.println("6. Listar usuários");
			System.out.println("7. Adicionar livro");
			System.out.println("8. Adicionar usuário");
			System.out.println("9. Listar livros emprestados");
			System.out.println("10.sair");
			System.out.print("Escolha uma opção: ");
			int opcao = scanner.nextInt();
			scanner.nextLine(); // Consumir a nova linha após o nextInt()
			
			// opçao para alugar um livro
			if (opcao == 1) {
				System.out.print("Digite o nome do usuario");
				String nomeUsuario = scanner.nextLine();
				Usuario usuario =  biblioteca.getUsuarios().stream()
						.filter(u -> u.getNome().equalsIgnoreCase(nomeUsuario))
						.findFirst()
						.orElse(null);
				if (usuario == null) {
					System.out.println("Usuário não encontrado: " + nomeUsuario);
					continue;
				}
				System.out.println("usuario encontrado: " + usuario.getNome() + " (" + usuario.getEmail() + ")");
				
				// Listar os livros disponíveis
				System.out.print("Digite o título do livro que deseja alugar: ");
				String tituloLivro = scanner.nextLine();
				Livro livro = biblioteca.getLivros().stream()
						.filter(l -> l.getTitulo().equalsIgnoreCase(tituloLivro) && l.isDisponibilidade())
						.findFirst()
						.orElse(null);
				System.out.println("Livro encontrado: " + livro.getTitulo() + " por " + livro.getAutor());		
				System.out.println("Livro alugado com sucesso: " + tituloLivro + " para o usuário: " + nomeUsuario);
				if (livro == null) {
					System.out.println("Livro não encontrado ou indisponível: " + tituloLivro);
					continue;
				}
				
			}
			// opçao para devolver um livro
			
			if (opcao == 2) {
				System.out.print("Digite o nome do usuario: ");
				String nomeUsuario = scanner.nextLine();
				Usuario usuario = biblioteca.getUsuarios().stream()
						.filter(u -> u.getNome().equalsIgnoreCase(nomeUsuario))
						.findFirst()
						.orElse(null);
				if (usuario == null) {
					System.out.println("Usuário não encontrado: " + nomeUsuario);
					continue;
				}
				System.out.println("usuario encontrado: " + usuario.getNome() + " (" + usuario.getEmail() + "(" + usuario.getLivroEmprestado() + ")");
				System.out.print("Digite o título do livro que deseja devolver: ");
				String tituloLivro = scanner.nextLine();
				Livro livro = biblioteca.getLivros().stream()
						.filter(l -> l.getTitulo().equalsIgnoreCase(tituloLivro) && !l.isDisponibilidade())
						.findFirst()
						.orElse(null);
				if (livro == null) {
					System.out.println("Livro não encontrado ou não emprestado: " + tituloLivro);
					continue;
				}
				System.out.println("Livro encontrado: " + livro.getTitulo() + " por " + livro.getAutor());
				System.out.println("Livro devolvido com sucesso: " + tituloLivro + " para o usuário: " + nomeUsuario);
				
			}
				
			// opçao para procurar um livro
			if (opcao == 3) {
				System.out.print("Digite o titulo do livro que deseja procurar: ");
				String titulolivro = scanner.nextLine();
				biblioteca.procurarLivro(titulolivro);
				System.out.println("Livro encontrado: " + titulolivro);
			}
			
			
			// opçao para procurar um usuário
			if (opcao == 4) {
				System.out.print("Digite o nome do usuario que deseja procurar: ");
				String nomeUsario  = scanner.nextLine();
				biblioteca.procurarUsuario(nomeUsario);
				System.out.println("Usuario encontrado: " + nomeUsario);		
			}
			
			// opçao para listar os livros disponíveis
			if (opcao == 5) {
				System.out.println("Livros disponíveis:");
				for (Livro livro : biblioteca.getLivros()) {
					if (livro.isDisponibilidade()) {
						System.out.println("- " + livro.getTitulo() + " por " + livro.getAutor());
					}
				}
			}
			
			if (opcao == 10) {
				break;
			}
		}
		scanner.close();
	}
}
