package gestao_orquestra.exceptions;

public class UsuarioNaoEncontradoException extends Exception{
    public UsuarioNaoEncontradoException(String login) {
        super("Regra violada: Já existe um usuário cadastrado com o login '" + login + "." );
    }
}
