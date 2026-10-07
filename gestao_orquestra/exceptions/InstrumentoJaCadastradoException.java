package gestao_orquestra.exceptions;

public class InstrumentoJaCadastradoException extends Exception{
    public InstrumentoJaCadastradoException(String codigo) {
        super("Regra violada: Já existe um instrumento cadastrado no acervo com o código " + codigo + ".");
    }
}
