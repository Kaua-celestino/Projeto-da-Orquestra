package gestao_orquestra.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Concerto {
    private String nomeEspetaculo;
    private LocalDate data;
    private String local;
    private Usuario maestro;
    private List<Musico> musicosEscalados;

    public Concerto(String nomeEspetaculo, LocalDate data, String local, Usuario maestro, List<Musico> musicosEscalados) {
        this.nomeEspetaculo = nomeEspetaculo;
        this.data = data;
        this.local = local;
        this.maestro = maestro;
        this.musicosEscalados = new ArrayList<>();
    }

    public String getNomeEspetaculo() {
        return nomeEspetaculo;
    }
    public void setNomeEspetaculo(String nomeEspetaculo) {
        this.nomeEspetaculo = nomeEspetaculo;
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }

    public Usuario getMaestro() {
        return maestro;
    }
    public void setMaestro(Usuario maestro) {
        this.maestro = maestro;
    }

    public List<Musico> getMusicosEscalados() {
        return musicosEscalados;
    }
    public void setMusicosEscalados(List<Musico> musicosEscalados) {
        this.musicosEscalados = musicosEscalados;
    }

//métodos de manipulação da lista de músicos
    public void adicionarMusico(Musico musico){
        this.musicosEscalados.add(musico);
    }

    public void removerMusico(Musico musico) {
        this.musicosEscalados.remove(musico);
    }
}



