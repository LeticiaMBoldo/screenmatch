package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Filme;
import br.com.alura.screenmatch.modelos.Serie;
import br.com.alura.screenmatch.modelos.Titulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class PrincipalComListas {
    public static void main(String[] args) {
        Filme meuFilme = new Filme(2001,"Ai - Inteligência Artificial");
        meuFilme.avalia(9);
        Filme outroFilme = new Filme(2016,"Sete minutos depois da meia noite");
        outroFilme.avalia(6);
        var filmeDoPaulo = new Filme(2003, "Dogville");
        filmeDoPaulo.avalia(10);
        Serie minhaSerie = new Serie(2026,"Enemies with Benefits");

        ArrayList<Titulo> lista = new ArrayList<>();
        /*O simbolo <> generics*/
        lista.add(meuFilme);
        lista.add(outroFilme);
        lista.add(filmeDoPaulo);
        lista.add(minhaSerie);

        /*Foreach = palavra reservada que cria um loop, parecido com for,
        só que mais simples*/
        for(Titulo item : lista) {
            /*Para cada item da da lista que tem haver com o titulo*/
            System.out.println(item.getNome());
            if (item instanceof Filme filme && filme.getClassificacao() > 2) {
//            Filme filme = (Filme) item; /*Fazer um cast do filme a partir do item*/
                System.out.println("Classificação " + filme.getClassificacao());
            }
        }
        /*Duas formas de usar o foreach*/
        //lista.forEach(nome -> System.out.println(nome));

        /*Method Reference*/
        //lista.forEach(System.out::println);

        ArrayList<String> buscaPorArtista = new ArrayList<>();
        buscaPorArtista.add("Adele");
        buscaPorArtista.add("Taylor Swift");
        buscaPorArtista.add("Rihanna");
        System.out.println(buscaPorArtista );

        /*metodo que ordena pela order alfabetica*/
        Collections.sort(buscaPorArtista);
        System.out.println("Depois da ordenação");
        System.out.println(buscaPorArtista);
        System.out.println("Lista de titulos ordenados");
        Collections.sort(lista);
        System.out.println(lista);

        /*Ordenar por ano*/
        System.out.println("Ordenado por ano de lançamento");
        lista.sort(Comparator.comparing(Titulo::getAnoDeLancamento)); /*:: -> trabalhar com lambdas*/
        System.out.println(lista);
    }
}
