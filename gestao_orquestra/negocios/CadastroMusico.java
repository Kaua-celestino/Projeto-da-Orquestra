package gestao_orquestra.negocios;

import gestao_orquestra.dados.RepositorioMusico;
import gestao_orquestra.model.Musico;
import gestao_orquestra.exceptions.MusicoNaoEncontradoException;
import gestao_orquestra.exceptions.MusicoJaCadastradoException;
import gestao_orquestra.exceptions.RepositorioCheioException;

public class CadastroMusico {
    // O cadastro (Cérebro) precisa conhecer o repositório
    private RepositorioMusico repositorio;

    public CadastroMusico(RepositorioMusico repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrarMusico(Musico musico) throws MusicoJaCadastradoException, RepositorioCheioException {
        try {
            // Tenta procurar o musico pelo CPF
            repositorio.procurar(musico.getCpf());

            //Se não der erro na linha de cima, significa que o música JÁ EXISTE.
            //Então, barramos a operação lanaçando a nossa exceção
            throw new MusicoJaCadastradoException(musico.getCpf());

        } catch (MusicoNaoEncontradoException ex) {
            // Se der erro ao procurar ( ou seja, não achou), o caminho está livre para cadastrar.
            repositorio.inserir(musico);

        }

    }


    public Musico buscar(String cpf) throws MusicoNaoEncontradoException {
        return repositorio.procurar(cpf);
    }

    public void atualizar(Musico musico) throws MusicoNaoEncontradoException{
        repositorio.atualizar(musico);
    }

    public void remover(String cpf) throws MusicoNaoEncontradoException {
        repositorio.remover(cpf);
    }
}