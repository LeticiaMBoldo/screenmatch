package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        var busca = "";
        List<Titulo> titulo = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (!busca.equalsIgnoreCase("sair")) {
            System.out.println("Digite um filme para busca: ");
            busca = leitura.nextLine();

            if(busca.equalsIgnoreCase("sair")) {
                break;
            }

            String chaveApi = "70b501e8";
            String endereco = "http://www.omdbapi.com/?i=tt3896198&apikey=" + chaveApi + "&t="
                    + busca.replace(" ", "+");
            System.out.println(endereco);

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
                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(meuTituloOmdb);

                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println("Titulo já convertido!");
                System.out.println(meuTitulo);

                /*Gravar arquivo*/
//                escrita.write(meuTitulo.toString());
//                escrita.close(); /*Lembrar de finalizar o arquivo ou ele vai ficar indisponivel e vai tomar memoria*/

                titulo.add(meuTitulo);

            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento na busca," +
                        " verifique o endereço!");

            } catch (ErroDeConversaoDeAnoException e) {
                System.out.println(e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Aconteceu algo, não sei o que é");
            }
        }
        System.out.println(titulo);

        FileWriter escrita = new FileWriter("filmes.json");
        escrita.write(gson.toJson(titulo));
        escrita.close();
        System.out.println("Programa finalizou corretamente!");

    }
}
