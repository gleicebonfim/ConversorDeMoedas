import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private static final String API_KEY = "b214842c6e4c47baf58c2196"; // minha apikey

    public double obterTaxa(String moedaOrigem, String moedaDestino) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + moedaOrigem;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        if (!json.get("result").getAsString().equals("success")) {
            throw new RuntimeException("Erro ao buscar taxas. Verifique a chave da API.");
        }

        JsonObject taxas = json.getAsJsonObject("conversion_rates");
        return taxas.get(moedaDestino).getAsDouble();
    }
}
