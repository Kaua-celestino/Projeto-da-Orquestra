package gestao_orquestra.negocios;

import gestao_orquestra.dados.RepositorioUsuario;
import gestao_orquestra.model.Usuario;
import gestao_orquestra.exceptions.UsuarioJaCadastradoException;
import gestao_orquestra.exceptions.UsuarioNaoEncontradoException;
import gestao_orquestra.exceptions.RepositorioCheioException;
import gestao_orquestra.exceptions.SenhaIncorretaException;

public class CadastroUsuario {

    private RepositorioUsuario repositorio;

    public CadastroUsuario(RepositorioUsuario repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrar(Usuario usuario) throws UsuarioJaCadastradoException, RepositorioCheioException {
        try {
            repositorio.procurar(usuario.getLogin());
            throw new UsuarioJaCadastradoException(usuario.getLogin());

        } catch (UsuarioNaoEncontradoException e) {
            repositorio.inserir(usuario);
        }
    }

    public Usuario buscar(String login) throws UsuarioNaoEncontradoException {
        return repositorio.procurar(login);
    }

    public void remover(String login) throws UsuarioNaoEncontradoException {
        repositorio.remover(login);
    }

    public Usuario fazerLogin(String login, String senha) throws UsuarioNaoEncontradoException, SenhaIncorretaException {
        Usuario usuario = repositorio.procurar(login);

        if (usuario.getSenha().equals(senha)) {
            return usuario;
        } else {
            throw new SenhaIncorretaException();
        }
    }
}
