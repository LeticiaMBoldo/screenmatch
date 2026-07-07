package br.com.alura.screenmatch.modelos;

public class Serie extends Titulo{
    private int temporadas;
    private int episodiosPorTemporada;
    private boolean ativa;
    private int minutosPorEpisodio;

    /*Construtor*/
    public Serie(int anoDeLancamento, String nome) {
        super(anoDeLancamento, nome);
    }

    /*Getter*/
    public int getTemporadas() {
        return temporadas;
    }

    public int getEpisodiosPorTemporada() {
        return episodiosPorTemporada;
    }

    public int getMinutosPorEpisodio() {
        return minutosPorEpisodio;
    }

    public boolean getAtiva() {
        return ativa;
    }

    /*Setter*/
    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    public void setEpisodiosPorTemporada(int episodiosPorTemporada) {
        this.episodiosPorTemporada = episodiosPorTemporada;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void setMinutosPorEpisodio(int minutosPorEpisodio) {
        this.minutosPorEpisodio = minutosPorEpisodio;
    }

    /*Metodos*/

    @Override /*Sobreescreve o metodo*/
    public int getDuracaoEmMinutos() {
        return temporadas * episodiosPorTemporada * minutosPorEpisodio;
        /*return super.getDuracaoEmMinutos(); -> conhecido como super classe, herda devolve as caracteristicas da classe mãe*/
    }

    @Override
    public String toString() {
        return "Serie: "+ this.getNome() + " (" + this.getAnoDeLancamento() + ")";
    }
}
