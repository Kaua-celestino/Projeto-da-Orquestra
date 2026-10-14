package gestao_orquestra.dados;

import gestao_orquestra.model.Usuario;
import gestao_orquestra.exceptions.UsuarioNaoEncontradoException;
import gestao_orquestra.exceptions.RepositorioCheioException;

public class RepositorioUsuario {
    private Usuario[] usuarios;
    private int indice;

    public RepositorioUsuario(int tamanhoMaximo) {
        this.usuarios = new Usuario[tamanhoMaximo];
        this.indice = 0;
    }

    public void inserir(Usuario usuario) throws RepositorioCheioException {
        if (this.indice < this.usuarios.length) {
            this.usuarios[this.indice] = usuario;
            this.indice++;
        } else {
            throw new RepositorioCheioException();
        }
    }

    public Usuario procurar(String login) throws UsuarioNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.usuarios[i].getLogin().equals(login)) {
                return this.usuarios[i];
            }
        } throw new UsuarioNaoEncontradoException(login);
    }

    public void atualizar(Usuario usuarioAtualizado) throws UsuarioNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if(this.usuarios[i].getLogin().equals(usuarioAtualizado.getLogin())) {
                this.usuarios[i] = usuarioAtualizado;
                return;
            }
        } throw new UsuarioNaoEncontradoException(usuarioAtualizado.getLogin());
    }

    public void remover (String login) throws UsuarioNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.usuarios[i].getLogin().equals(login)) {
                for (int j = i; j < this.indice - 1; j++ ) {
                    this.usuarios[j] = this.usuarios[j + 1];
                }
                this.usuarios[this.indice - 1] = null;
                this.indice--;
                return;
            }
        } throw new UsuarioNaoEncontradoException(login);
    }
}
