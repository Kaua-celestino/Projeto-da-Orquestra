package gestao_orquestra.model;

//A classe InstrumentoCorda serve pra categorizar os instrumentos que usam palhetas (lâminas finas que vibram com o sopro) ou não. (só isso mesmo)
public class InstrumentoSopro extends Instrumento{
    private String tipoPalheta;

    public InstrumentoSopro(String codigoTombamento, String nome, EstadoConservacao estado, String tipoPalheta) {
        super(codigoTombamento, nome, Naipe.SOPROS, estado);
        this.tipoPalheta = tipoPalheta;
    }

    //Criando métodos get set pro atributo de InstrumentoSopro
    public String getTipoPalheta() {
        return tipoPalheta;
    }
    public void setTipoPalheta(String tipoPalheta) {
        this.tipoPalheta = tipoPalheta;
    }
}

