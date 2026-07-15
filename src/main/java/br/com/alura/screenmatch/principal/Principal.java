package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
            List<DadosEpisodio> episo  dioTemporadas = temporadas.get(i).episodios();
            for(int j = 0; j <episodioTemporadas.size(); j++){
                System.out.println(episodioTemporadas.get(j).titulo());
            }
        }*/
        /*Transformar o for de cima, numa única linha de comando*/
        temporadas.forEach(t -> t.episodios().forEach(
                        e -> System.out.println(e.titulo()
                        )
                )
        );

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 10 Episodios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro(N/A)" + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação" + e))
                .limit(10)
                .peek(e -> System.out.println("Limitação de 10 no máximo" + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeando os campos e acrescentando um to upper case" + e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Informe uma referencia para buscar um episódio da serie " + nomeSerie);
        var trechoTitulo = scanner.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(trechoTitulo))
                .findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println("Episodio encontrado");
            System.out.println("temporada: " + episodioBuscado.get().getTemporada() +
                    "Episodio: " + episodioBuscado.get().getTitulo());
        } else {
            System.out.println("episodio não encontrado!");
        }

        System.out.println("a partir de que ano você deseja ver os episodios? ");
        var ano = scanner.nextInt();
        scanner.nextLine();

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataBusca = LocalDate.of(ano,1,1);
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null &&
                        e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                ", Episódio: " + e.getTitulo() +
                                ", Data de Lançamento: " + e.getDataLancamento().format(formatador)
                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)
                                        ));
        System.out.println(avaliacoesPorTemporada);

        /*Gerar estatisticas*/
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: " + est.getAverage());
        System.out.println("Pior episodio: " + est.getMin());
        System.out.println("Melhor episodio: " + est.getMax());
        System.out.println("Quantidade de episódios avaliados: " + est.getCount());
    }
}
