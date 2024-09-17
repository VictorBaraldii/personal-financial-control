import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Financeiro financeiro = new Financeiro();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    adicionarTransacao();
                    break;
                case 2:
                    financeiro.gerarRelatorio();
                    break;
                case 3:
                    filtrarPorCategoria();
                    break;
                case 0:
                    System.out.println("Saindo do aplicativo. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n==== Controle Financeiro Pessoal ====");
        System.out.println("1. Adicionar Transação");
        System.out.println("2. Gerar Relatório");
        System.out.println("3. Filtrar Transações por Categoria");
        System.out.println("0. Sair");
        System.out.println("Escolha um opção: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void adicionarTransacao() {
        try {
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Valor: R$ ");
            double valor = Double.parseDouble(scanner.nextLine());

            System.out.print("Data (dd/MM/yyyy): ");
            LocalDate data = LocalDate.parse(scanner.nextLine(), formatter);

            System.out.print("Categoria: ");
            String categoria = scanner.nextLine();

            System.out.print("É uma receita? (s/n): ");
            String resposta = scanner.nextLine();
            boolean isReceita = resposta.equalsIgnoreCase("s");

            Transacao t = new Transacao(descricao, valor, data, categoria, isReceita);
            financeiro.adicionarTransacao(t);

            System.out.println("Transação adicionada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao adicionar transação: " + e.getMessage());
        }
    }

    private static void filtrarPorCategoria() {
        System.out.print("Digite a categoria para filtrar: ");
        String categoria = scanner.nextLine();
        List<Transacao> filtradas = financeiro.filtrarPorCategoria(categoria);

        if (filtradas.isEmpty()) {
            System.out.println("Nenhuma transação encontrada para a categoria: " + categoria);
        } else {
            System.out.println("----- Transações na Categoria: " + categoria + "-----");
            for (Transacao t : filtradas) {
                System.out.println(t.getData().format(formatter) + " | " + (t.isReceita() ? "Receita" : "Despesa") + " | R% " + t.getValor() + " | " + t.getDescricao());
            }
        }
    }
}
