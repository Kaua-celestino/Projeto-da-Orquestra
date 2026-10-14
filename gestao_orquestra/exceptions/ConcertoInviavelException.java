package gestao_orquestra.exceptions;

public class ConcertoInviavelException extends Exception{
    public ConcertoInviavelException(String nomeEspetaculo, String naipeFaltando) {
        super("Não é possível aprovar o concerto '" + nomeEspetaculo + "': Faltam músicos no naipe de " + naipeFaltando + ".");
    }
}
