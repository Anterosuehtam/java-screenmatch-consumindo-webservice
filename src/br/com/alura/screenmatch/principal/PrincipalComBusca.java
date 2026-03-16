package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("Digite um filme para buscar as informações: ");
        var busca = scanner.nextLine();

        var endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=cfb6762e";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            var json = response.body();
            System.out.println("===============JSON do filme===============");
            System.out.println(json);

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();
            //Titulo meuTitulo = gson.fromJson(json, Titulo.class);
            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println("\n===============Título tipo OMDB===============");
            System.out.println(meuTituloOmdb);
            //try {
            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println("\n===============Título convertido=============== ");
            System.out.println(meuTitulo);
        } catch(NumberFormatException e) {
            System.out.println("\nOcorreu um erro durante a execução da aplicação: ");
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("\nAlgum erro de argumento na busca, verifique o endereço.");
        } catch (ErroDeConversaoDeAnoException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nO programa finalizou corretamente!");
    }
}
