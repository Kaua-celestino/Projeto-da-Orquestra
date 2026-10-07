package gestao_orquestra.negocios;

import gestao_orquestra.dados.RepositorioInstrumento;
import gestao_orquestra.model.Instrumento;
import gestao_orquestra.exceptions.InstrumentoJaCadastradoException;
import gestao_orquestra.exceptions.InstrumentoNaoEncontradoException;
import gestao_orquestra.exceptions.RepositorioCheioException;

public class CadastroInstrumento {
    private RepositorioInstrumento repositorio;

    public CadastroInstrumento(RepositorioInstrumento instrumento) {
        this.repositorio = repositorio;
    }

    public void cadastrarInstrumento(Instrumento instrumento) throws InstrumentoJaCadastradoException, RepositorioCheioException {
        try {
            repositorio.procurar(instrumento.getCodigoTombamento());

            throw new InstrumentoJaCadastradoException(instrumento.getCodigoTombamento());
        } catch (InstrumentoNaoEncontradoException ex) {
            repositorio.inserir(instrumento);
        }
    }


    public Instrumento buscar(String codigoTombamento) throws InstrumentoNaoEncontradoException {
        return repositorio.procurar(codigoTombamento);
    }

    public void atualizar(Instrumento instrumento) throws InstrumentoNaoEncontradoException {
        repositorio.atualizar(instrumento);
    }

    public void remover(String codigoTombamento) throws InstrumentoNaoEncontradoException {
        repositorio.remover(codigoTombamento);
    }
}
