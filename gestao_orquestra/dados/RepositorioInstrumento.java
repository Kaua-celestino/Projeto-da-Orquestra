package gestao_orquestra.dados;

import gestao_orquestra.model.Instrumento;
import gestao_orquestra.exceptions.InstrumentoNaoEncontradoException;
import gestao_orquestra.exceptions.RepositorioCheioException;


public class RepositorioInstrumento {
    private Instrumento[] instrumentos;
    private int indice;

    public RepositorioInstrumento(int tamanhoMaximo) {
        this.instrumentos = new Instrumento[tamanhoMaximo];
        this.indice = 0;
    }

    public void inserir(Instrumento instrumento) throws RepositorioCheioException {
        if (this.indice < this.instrumentos.length) {
            this.instrumentos[this.indice] = instrumento;
            this.indice++;
        } else {
            throw new RepositorioCheioException();
        }
    }

    public Instrumento procurar(String codigoTombamento) throws InstrumentoNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.instrumentos[i].getCodigoTombamento().equals(codigoTombamento)) {
                return this.instrumentos[i];
            }
        }
        throw new InstrumentoNaoEncontradoException(codigoTombamento);
    }

    public void atualizar(Instrumento instrumentoAtualizado) throws InstrumentoNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.instrumentos[i].getCodigoTombamento().equals(instrumentoAtualizado.getCodigoTombamento())) {
                this.instrumentos[i] = instrumentoAtualizado;
                return;
            }
        }
        throw new InstrumentoNaoEncontradoException(instrumentoAtualizado.getCodigoTombamento());
    }

    public void remover(String codigoTombamento) throws InstrumentoNaoEncontradoException {
        for (int i = 0; i < this.indice; i++) {
            if (this.instrumentos[i].getCodigoTombamento().equals(codigoTombamento)) {
                for (int j = i; j < this.indice - 1; j++) {
                    this.instrumentos[j] = this.instrumentos[j + 1];
                }
                this.instrumentos[this.indice - 1] = null;
                this.indice--;
                return;
            }
        }
        throw new InstrumentoNaoEncontradoException(codigoTombamento);
    }
}