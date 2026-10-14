package gestao_orquestra.exceptions;

public class SenhaIncorretaException extends Exception{
    public SenhaIncorretaException() {
        super("Falha na autenticação: senha incorreta.");
    }
}
