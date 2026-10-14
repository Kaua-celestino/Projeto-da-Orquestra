package gestao_orquestra.dados;

import gestao_orquestra.exceptions.RepositorioCheioException;
import gestao_orquestra.model.Concerto;
import gestao_orquestra.exceptions.ConcertoJaCadastradoException;
import gestao_orquestra.exceptions.ConcertoNaoEncontradoException;

public class RepositorioConcerto {
    private Concerto[] concertos;
    private int indice;

    public RepositorioConcerto(int tamanhoMaximo) {
        this.concertos = new Concerto[tamanhoMaximo];
        this.indice = 0;
    }

    public void inserir(Concerto concerto) throws RepositorioCheioException{
        if (this.indice < this.concertos.length) {
            this.concertos[this.indice] = concerto;
            this.indice++;
        } else {
            throw new RepositorioCheioException();
        }
    }

    public Concerto procurar(String nomeEspetaculo) throws ConcertoNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.concertos[i].getNomeEspetaculo().equalsIgnoreCase(nomeEspetaculo)) {
                return this.concertos[i];
            }
        }
        throw new ConcertoNaoEncontradoException(nomeEspetaculo);
    }

    public void atualizar(Concerto concertoAtualizado) throws ConcertoNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.concertos[i].getNomeEspetaculo().equalsIgnoreCase(concertoAtualizado.getNomeEspetaculo())) {
                this.concertos[i] = concertoAtualizado;
                return;
            }
        }
        throw new ConcertoNaoEncontradoException(concertoAtualizado.getNomeEspetaculo());
    }

    public void remover(String nomeEspetaculo) throws ConcertoNaoEncontradoException{
        for (int i = 0; i < this.indice; i++) {
            if (this.concertos[i].getNomeEspetaculo().equalsIgnoreCase(nomeEspetaculo)) {
                for (int j = i; j < this.indice - 1; j++) {
                    this.concertos[i] = this.concertos[j + 1];
                }
                this.concertos[this.indice - 1] = null;
                this.indice--;
                return;
            }
        } throw new ConcertoNaoEncontradoException(nomeEspetaculo);
    }

    public Concerto[] getTodosConcertos() {
        return this.concertos;
    }

    public int getIndice() {
        return this.indice;
    }
}
