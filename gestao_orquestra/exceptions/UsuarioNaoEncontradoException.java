package gestao_orquestra.exceptions;

public class UsuarioNaoEncontradoException extends Exception {
    public UsuarioNaoEncontradoException(String login) {
        super("Não existe nenhum usuário cadastrado com o login '" + login + "'.");
    }
}