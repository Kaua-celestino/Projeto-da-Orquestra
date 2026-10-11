package gestao_orquestra.exceptions;

public class EmprestimoNaoEncontradoException extends Exception{
    public EmprestimoNaoEncontradoException (String cpfMusico, String codigoTombamento) {
        super("Não foi encontrado um empréstimo ativo do instrumento " + codigoTombamento + " para o músico de CPF" + cpfMusico + ".");
    }
}
