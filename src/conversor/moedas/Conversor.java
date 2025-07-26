import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Conversor {
    private final ApiService apiService = new ApiService();
    private final List<String> historico = new ArrayList<>();

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        while (true) {
            try {
                System.out.println("\n== Conversor de Moedas ==");
                System.out.print("Moeda de origem (ex: USD): ");
                String origem = scanner.nextLine().toUpperCase();

                System.out.print("Moeda de destino (ex: BRL): ");
                String destino = scanner.nextLine().toUpperCase();

                System.out.print("Valor a converter: ");
                double valor = scanner.nextDouble();
                scanner.nextLine(); // limpar buffer

                double taxa = apiService.obterTaxa(origem, destino);
                double convertido = valor * taxa;

                LocalDateTime agora = LocalDateTime.now();
                String dataHora = formatter.format(agora);

                String registro = String.format("[%s] %.2f %s = %.2f %s", dataHora, valor, origem, convertido, destino);
                historico.add(registro);

                System.out.println("Resultado: " + registro);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                scanner.nextLine(); // evitar loop travado
            }

            System.out.print("\nDeseja ver o histórico de conversões? (s/n): ");
            String verHistorico = scanner.nextLine();
            if (verHistorico.equalsIgnoreCase("s")) {
                exibirHistorico();
            }

            System.out.print("\nDeseja realizar outra conversão? (s/n): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("s")) break;
        }

        System.out.println("Obrigado por usar o Conversor de Moedas!");
    }

    private void exibirHistorico() {
        System.out.println("\n== Histórico de Conversões ==");
        if (historico.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
        } else {
            for (String registro : historico) {
                System.out.println(registro);
            }
        }
    }
}