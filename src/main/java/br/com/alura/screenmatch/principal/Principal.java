package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    /**
     * O final no declaração da variavel significa que não vai ser mudado no futuro
     * Quando a varivael é final a declaração do nome é tudo em maiuscula
     *
     */
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=70b501e8";
    private final String SEASON = "&Season=";

    public void exibeMenu() {
        System.out.println("Digite o nome de uma série para busca: ");
        var nomeSerie = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO +
                    nomeSerie.replace(" ","+") +
                    API_KEY);
        /*Pegar dados da série*/
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList();
		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumo.obterDados(
                    ENDERECO +
                    nomeSerie.replace(" ","+") +
                    SEASON + i +
                    API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        /*Imprimir o titulo dos episodios*/
        /*for (int i = 0; i < dados.totalTemporadas(); i++) {
            List<DadosEpisodio> episodioTemporadas = temporadas.get(i).episodios();
            for(int j = 0; j <episodioTemporadas.size(); j++){
                System.out.println(episodioTemporadas.get(j).titulo());
            }
        }*/
        /*Transformar o for de cima, numa única linha de comando*/
        temporadas.forEach(t ->
                t.episodios().forEach(
                        e -> System.out.println(e.titulo()
                        )
                )
        );
    }
}
