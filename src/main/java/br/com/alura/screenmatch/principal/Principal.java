package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsultaChatGPT;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

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
    private ConsultaChatGPT consultaChatGPT = new ConsultaChatGPT();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=70b501e8";
    private final String SEASON = "&Season=";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    private List<Serie> series = new ArrayList<>();

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar série
                    2 - Buscar episódio
                    3 - Listar as séries buscadas
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    BuscarEpisodioPorSerie();
                    break;
                case 3:
                    ListarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para buscar");
        var nomeSerie = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void BuscarEpisodioPorSerie() {
        System.out.println("Lista de séries atualizadas");
        ListarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = scanner.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo()
                        .toLowerCase()
                        .contains(nomeSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()) {
            var serieEncontrada = serie.get();

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO +
                        serieEncontrada.getTitulo().replace(" ", "+") +
                        SEASON + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                                        .flatMap(d -> d.episodios().stream()
                                                .map(e -> new Episodio(d.numero(), e)))
                                        .toList();

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);

        } else {
            System.out.println("Série não encontrada!");
        }

    }

    private void ListarSeriesBuscadas() {
        //criar uma lista de series
        series = repositorio.findAll();
        //a partir da serie reservada alimentar criar uma nova serie na classe Serie
        //foi alterado para pegar os dados salvos no banco
//        series = dadosSeries.stream()
//                        .map(d -> new Serie(d))
//                                .collect(Collectors.toList());
        //ordenar ela a serie a partir da informação do genero e apresentar para o usuário
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }
}
