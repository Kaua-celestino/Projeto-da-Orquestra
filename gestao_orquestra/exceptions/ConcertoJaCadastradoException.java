package gestao_orquestra.exceptions;

public class ConcertoJaCadastradoException extends Exception{
    public ConcertoJaCadastradoException(String nomeEspetaculo){
        super("Regra violada: Já existe um concerto cadastrado com o nome '" + nomeEspetaculo + "' no sistema.");

    }
}
