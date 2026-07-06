import br.com.alura.screenmatch.calculos.CalculadoraDeTempo;
import br.com.alura.screenmatch.calculos.FiltroRecomendacao;
import br.com.alura.screenmatch.modelos.Episodio;
import br.com.alura.screenmatch.modelos.Filme;
import br.com.alura.screenmatch.modelos.Serie;

public class Principal {
    public static void main(String[] args) {
        Filme meuFilme = new Filme();
        meuFilme.setNome("Ai - Inteligência Artificial");
        meuFilme.setAnoDeLancamento(2001);
        meuFilme.setDuracaoEmMinutos(146);

        meuFilme.exibeFichaTecnica();
        meuFilme.avalia(8);
        meuFilme.avalia(5);
        meuFilme.avalia(10);
        System.out.println(meuFilme.getTotalDeAvaliacoes() );
        System.out.println(meuFilme.obtemMedia());

        Serie minhaSerie = new Serie();
        minhaSerie.setNome("Enemies with Benefits");
        minhaSerie.setAnoDeLancamento(2026);
        minhaSerie.setAtiva(true);
        minhaSerie.setEpisodiosPorTemporada(10);
        minhaSerie.setMinutosPorEpisodio(50);
        minhaSerie.setTemporadas(1);
        System.out.println("Duranção para maratonar " + minhaSerie.getNome() + " é de " +
                minhaSerie.getDuracaoEmMinutos());

        Filme outroFilme = new Filme();
        outroFilme.setNome("Sete minutos depois da meia noite");
        outroFilme.setAnoDeLancamento(2016);
        outroFilme.setDuracaoEmMinutos(108);

        CalculadoraDeTempo calculadora = new CalculadoraDeTempo();
        calculadora.inclui(meuFilme);
        calculadora.inclui(outroFilme);
        calculadora.inclui(minhaSerie);
        System.out.println(calculadora.getTempoTotal());

        FiltroRecomendacao filtro = new FiltroRecomendacao();
        filtro.filtra(meuFilme);

        Episodio episodio = new Episodio();
        episodio.setNome("Episodio 1");
        episodio.setNumero(1);
        episodio.setSerie(minhaSerie);
        episodio.setTotalVisualizacoes(300);
        filtro.filtra(episodio);


    }
}
