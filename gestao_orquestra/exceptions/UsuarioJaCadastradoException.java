package gestao_orquestra.exceptions;

public class UsuarioJaCadastradoException extends Exception{
    public UsuarioJaCadastradoException(String login) {
        super("Erro: O usuário com login '" + login + "' não foi encontrad.");
    }
}
