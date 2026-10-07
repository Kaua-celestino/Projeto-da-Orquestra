package gestao_orquestra.dados;

import gestao_orquestra.model.Musico;
import gestao_orquestra.exceptions.MusicoNaoEncontradoException;
import gestao_orquestra.exceptions.RepositorioCheioException;


public class RepositorioMusico {

    private Musico[] musicos;
    private int indice;

    public RepositorioMusico(int tamanhoMaximo) {
        this.musicos = new Musico[tamanhoMaximo];
        this.indice = 0;
    }

    public void inserir(Musico musico) throws RepositorioCheioException {
        if (this.indice < this.musicos.length) {
            this.musicos[this.indice] = musico;
            this.indice++;
        } else {
            throw new RepositorioCheioException();
        }

    }

    public Musico procurar(String cpf) throws MusicoNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.musicos[i].getCpf().equals(cpf)) {
                return this.musicos[i];
            }
        }
        throw new MusicoNaoEncontradoException(cpf);
    }

    public void atualizar(Musico musicoAtualizado) throws MusicoNaoEncontradoException{
        for (int i = 0; i < this.indice; i++) {
            if (this.musicos[i].getCpf().equals(musicoAtualizado.getCpf())){
                this.musicos[i] = musicoAtualizado;
                return;
            }
        }
        throw new MusicoNaoEncontradoException(musicoAtualizado.getCpf());
    }

    public void remover(String cpf) throws MusicoNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.musicos[i].getCpf().equals(cpf)){
                //Puxa todos os elementos uma posição para trás e apaga o músico sobrepondo-o
                for (int j = i; j < this.indice - 1; j++) {
                    this.musicos[j] = this.musicos[j+1];
                }
                //Limpa a última posição e reduz o tamanho lógico
                this.musicos[this.indice - 1] = null;
                this.indice--;
                return;
            }
        }
        throw new MusicoNaoEncontradoException(cpf);
    }

}


