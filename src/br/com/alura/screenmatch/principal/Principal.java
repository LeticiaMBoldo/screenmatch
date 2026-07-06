package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.calculos.CalculadoraDeTempo;
import br.com.alura.screenmatch.calculos.FiltroRecomendacao;
import br.com.alura.screenmatch.modelos.Episodio;
import br.com.alura.screenmatch.modelos.Filme;
import br.com.alura.screenmatch.modelos.Serie;

import java.util.ArrayList;

public class Principal {
    public static void main(String[] args) {
        Filme meuFilme = new Filme(2001,"Ai - Inteligência Artificial");
        meuFilme.setDuracaoEmMinutos(146);

        meuFilme.exibeFichaTecnica();
        meuFilme.avalia(8);
        meuFilme.avalia(5);
        meuFilme.avalia(10);
        System.out.println(meuFilme.getTotalDeAvaliacoes() );
        System.out.println(meuFilme.obtemMedia());

        Serie minhaSerie = new Serie(2026,"Enemies with Benefits");
        minhaSerie.setAtiva(true);
        minhaSerie.setEpisodiosPorTemporada(10);
        minhaSerie.setMinutosPorEpisodio(50);
        minhaSerie.setTemporadas(1);
        System.out.println("Duranção para maratonar " + minhaSerie.getNome() + " é de " +
                minhaSerie.getDuracaoEmMinutos());

        Filme outroFilme = new Filme(2016,"Sete minutos depois da meia noite");
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

        var filmeDoMazzaropi = new Filme(1957,"Mazzaropi em Jéca Tatú");
        filmeDoMazzaropi.setDuracaoEmMinutos(140);
        filmeDoMazzaropi.avalia(5);

        ArrayList<Filme> listaDeFilmes = new ArrayList<>();
        listaDeFilmes.add(filmeDoMazzaropi);
        listaDeFilmes.add(meuFilme);
        listaDeFilmes.add(outroFilme);
        System.out.println("Tamanho da lista " + listaDeFilmes.size());
        System.out.println("Primeiro filme " + listaDeFilmes.get(0).getNome());

    }
}
