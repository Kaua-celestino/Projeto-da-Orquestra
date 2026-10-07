package gestao_orquestra.exceptions;

public class InstrumentoNaoEncontradoException extends Exception{
    public InstrumentoNaoEncontradoException(){
        super("A operação falhou: Instrumento não encontrado no acervo.");
    }

    public InstrumentoNaoEncontradoException(String codigo) {
        super("A operação falhou: Instrumento com código " + codigo + "não foi encontrado.");
    }
}
