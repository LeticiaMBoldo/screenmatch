package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PrincipalComEncoding {
    public static void main(String[] args) {
        try {
            Scanner leitura = new Scanner(System.in);

            System.out.println("Digite um filme para busca: ");
            var busca = leitura.nextLine();
            String buscaUrlAjustada = URLEncoder.encode(busca, StandardCharsets.UTF_8.toString());

            String chaveApi = "70b501e8";
            String endereco = "http://www.omdbapi.com/?i=tt3896198&apikey=" + chaveApi
                    + "&t=" + buscaUrlAjustada;

            try {
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());


                String json = response.body();
                System.out.println(json);
                /*Usou o Maven para baixar o gson*/
                /*Fazer um gson builder*/
                //Gson gson = new Gson();
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                        .create();
                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(meuTituloOmdb);

                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println("Titulo já convertido!");
                System.out.println(meuTitulo);
            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento na busca," +
                        " verifique o endereço!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                System.out.println("Aconteceu algo, não sei o que é");
            }
            finally {
                System.out.println("Programa finalizou corretamente!");
            }
        }catch (UnsupportedEncodingException e) {
            System.out.println("URL precisa ser corrigida: ");
            e.printStackTrace();
        }
    }
}
