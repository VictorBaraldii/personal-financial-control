import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Financeiro {
    private List<Transacao> transacoes;
    private final String caminhoArquivo = "transacoes.txt";

    // Construtor
    public Financeiro() {
        transacoes = new ArrayList<>();
        carregarTransacoes();
    }

    // Adiciona uma nova transação
    public void adicionarTransacao(Transacao t) {
        transacoes.add(t);
        salvarTransacoes();
    }

    // Calcula o saldo atual
    public double calcularSaldo() {
        double receitas = transacoes.stream()
                .filter(Transacao::isReceita)
                .mapToDouble(Transacao::getValor)
                .sum();

        double despesas = transacoes.stream()
                .filter(t -> !t.isReceita())
                .mapToDouble(Transacao::getValor)
                .sum();

        return receitas - despesas;
    }

    // Filtra transações por categoria
    public List<Transacao> filtrarPorCategoria(String categoria) {
        return transacoes.stream()
                .filter(t -> t.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    // Gera um relatório
    public void gerarRelatorio() {
        System.out.println("---- Relatório de Transações ----");
        for (Transacao t : transacoes) {
            System.out.println(t.getData() + " | " + (t.isReceita() ? "Receita" : "Despesa") + " | " + t.getCategoria() + " | " + t.getDescricao() + " | R$ " + t.getValor());
        }
        System.out.println("-------------------------------------");
        System.out.println("Saldo Atual: R$ " + calcularSaldo());
    }

    // Salva as transações no arquivo
    private void salvarTransacoes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Transacao t : transacoes){
                writer.write(t.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar transações: " + e.getMessage());
        }
    }

    // Carrega as transações do arquivo
    private void carregarTransacoes() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha =  reader.readLine()) != null) {
                Transacao t = Transacao.fromString(linha);
                transacoes.add(t);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar transações: " + e.getMessage());
        }
    }
}
