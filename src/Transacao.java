import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private String descricao;
    private double valor;
    private LocalDate data;
    private String categoria;
    private boolean isReceita;

    // Construtor
    public Transacao(String descricao, double valor, LocalDate data, String categoria, boolean isReceita) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.isReceita = isReceita;
    }

    // Getters e Setters

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public String getCategoria() {
        return categoria;
    }
    public boolean isReceita() {
        return isReceita;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return descricao + ";" + valor + ";" + data.format(formatter) + ";" + categoria + ";" + isReceita;
    }

    // Método para criar uma Transação a partir de uma String
    public static Transacao fromString(String linha) {
        String[] partes = linha.split(";");
        String descricao = partes[0];
        double valor = Double.parseDouble(partes[1]);
        LocalDate data = LocalDate.parse(partes[2], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String categoria = partes[3];
        boolean isReceita = Boolean.parseBoolean(partes[4]);
        return new Transacao(descricao, valor, data, categoria, isReceita);
    }
}