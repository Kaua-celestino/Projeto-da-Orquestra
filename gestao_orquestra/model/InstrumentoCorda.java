package gestao_orquestra.model;

//A classe InstrumentoCorda serve pra categorizar os instrumentos que usam corda (só isso mesmo)

public class InstrumentoCorda extends Instrumento{
    private int quantidadeCordas;

    //Criando construtor de InstrumentoCorda
    public InstrumentoCorda(String codigoTombamento, String nome, EstadoConservacao estado, int quantidadeCordas) {
        super(codigoTombamento, nome, Naipe.CORDAS, estado);
        this.quantidadeCordas = quantidadeCordas;
    }

    //Criando métodos get set pro atributo de InstrumentoCorda
    public int getQuantidadeCordas() {
        return quantidadeCordas;
    }
    public void setQuantidadeCordas(int quantidadeCordas) {
        this.quantidadeCordas = quantidadeCordas;
    }
}
