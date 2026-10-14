package gestao_orquestra.exceptions;

public class ConcertoNaoEncontradoException extends Exception{
    public ConcertoNaoEncontradoException(String nomeEspetaculo) {
        super("Operação falhou: O concerto '" + nomeEspetaculo + "não foi encontrado no sistema.");
    }
}
