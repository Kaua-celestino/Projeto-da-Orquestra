package gestao_orquestra.negocios;

import gestao_orquestra.dados.RepositorioConcerto;
import gestao_orquestra.exceptions.*;
import gestao_orquestra.model.Concerto;
import gestao_orquestra.model.Musico;
import gestao_orquestra.model.Naipe;


public class CadastroConcerto {

    private RepositorioConcerto repositorio;

    public CadastroConcerto(RepositorioConcerto repositorio){
        this.repositorio = repositorio;
    }

    public void cadastrar(Concerto concerto) throws ConcertoJaCadastradoException, RepositorioCheioException {
        try {
            //Tenta achar o concerto no repositório
            repositorio.procurar(concerto.getNomeEspetaculo());

            //Se a linha de cima n deu certo, é porque o concerto já EXISTE
            throw new ConcertoJaCadastradoException(concerto.getNomeEspetaculo());

        } catch (ConcertoNaoEncontradoException e){
            //Se o procurar disparar o erro de não encontrado, sabemos que o nome está livre para ser cadastrado.
            repositorio.inserir(concerto);
        }
    }

    public Concerto buscar(String nomeEspetaculo) throws ConcertoNaoEncontradoException {
        return repositorio.procurar(nomeEspetaculo);
    }

    public void remover(String nomeEspetaculo) throws ConcertoNaoEncontradoException {
        repositorio.remover(nomeEspetaculo);
    }

    // REGRA DE NEGÓCIOS: escalar músicos

    public void escalarMusicoNoConcerto(String nomeEspetaculo, Musico musico) throws ConcertoNaoEncontradoException {
        Concerto concerto = repositorio.procurar(nomeEspetaculo);
        concerto.adicionarMusico(musico);
        repositorio.atualizar(concerto);
    }

    public void removerMusicoDoConcerto(String nomeEspetaculo, String cpfMusico) throws ConcertoNaoEncontradoException, MusicoNaoEncontradoException {
        Concerto concerto = repositorio.procurar(nomeEspetaculo);

        concerto.removerMusico(cpfMusico);

        repositorio.atualizar(concerto);
    }

    public void aprovarConcerto(String nomeEspetaculo) throws ConcertoNaoEncontradoException, ConcertoInviavelException {
        //Busca o concerto
        Concerto concerto = repositorio.procurar(nomeEspetaculo);

        //Variáveis para a contagem dos intrumentos nos naipes
        int contCordas = 0;
        int contSopros = 0;
        int contPercussao = 0;

        //Varre as cadeiras do palco
        for (int i = 0; i < concerto.getIndiceMusicos(); i++) {
            Musico musico = concerto.getMusicosEscalados()[i];

            //Compara diretamente com o enum.
            if (musico.getNaipePrincipal() == Naipe.CORDAS) {
                contCordas++;
            } else if (musico.getNaipePrincipal() == Naipe.SOPROS) {
                contSopros++;
            } else if (musico.getNaipePrincipal() == Naipe.PERCUSSAO) {
                contPercussao++;
            }
        }

        if (contCordas < 1) {
            throw new ConcertoInviavelException(nomeEspetaculo, "Cordas");

        } else if (contSopros < 1) {
            throw new ConcertoInviavelException(nomeEspetaculo, "Sopros");
        } else if (contPercussao < 1) {
            throw new ConcertoInviavelException(nomeEspetaculo, "Percussão");
        }
    }
}

