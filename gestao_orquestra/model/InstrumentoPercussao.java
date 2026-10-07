package gestao_orquestra.model;

//A classe InstrumentoCorda serve pra categorizar os instrumentos que usam baquetas ou não (só isso mesmo)
public class InstrumentoPercussao extends Instrumento{
    private boolean usaBaquetas;

    //Criando construtor de InstrumentoPercussao
    public InstrumentoPercussao(String codigoTombamento, String nome, EstadoConservacao estado, boolean usaBaquetas){
        super(codigoTombamento, nome, Naipe.PERCUSSAO, estado);
        this.usaBaquetas = usaBaquetas;
    }


    //Criando métodos get set pro atributo de InstrumentoPercussao
    public boolean isUsaBaquetas() {
        return usaBaquetas;
    }
    public void setUsaBaquetas(boolean usaBaquetas) {
        this.usaBaquetas = usaBaquetas;
    }
}
