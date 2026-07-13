package br.com.alura.screenmatch.stream;

import java.util.Arrays;
import java.util.List;

public class TestandoStream {

    public void testandoStream() {
        List<String> nomes = Arrays.asList("Jacques", "Iasmin", "Paulo", "Rodrigo", "Nico");
        nomes.stream()
                .sorted()
                .limit(3)
                .filter(n -> n.startsWith("N"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);

    }

}
