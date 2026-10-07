package gestao_orquestra.model;

//A classe Instrumento dará base para t0do e qualquer instrumento dentro da orquestra.

//O código de tombamento seria o "número de série" do instrumento, pois pode haver 2 pessoas
//que tocam o mesmo tipo de instrumento. Serve para diferenciá-los.

//O atributo emLutheria é usado para saber se o instrumento está em conserto/reparo ou não
public abstract class Instrumento {
    private String codigoTombamento;
    private String nome;
    private Naipe familia;
    private EstadoConservacao estado;
    private boolean emLutheria;

    //Criando construtor de instrumento
    public Instrumento(String codigoTombamento, String nome, Naipe familia, EstadoConservacao estado) {
        this.codigoTombamento = codigoTombamento;
        this.nome = nome;
        this.familia = familia;
        this.estado = estado;
        this.emLutheria = false;
    }

    //Criando métodos get set para cada atribudo
    public String getCodigoTombamento() {
        return codigoTombamento;
    }
    public void setCodigoTombamento(String codigoTombamento) {
        this.codigoTombamento = codigoTombamento;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Naipe getFamilia() {
        return familia;
    }
    public void setFamilia(Naipe familia) {
        this.familia = familia;
    }

    public EstadoConservacao getEstado() {
        return estado;
    }
    public void setEstado(EstadoConservacao estado) {
        this.estado = estado;
    }

    public boolean isEmLutheria() {
        return emLutheria;
    }
    public void setEmLutheria(boolean emLutheria) {
        this.emLutheria = emLutheria;
    }
}
