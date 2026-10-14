package gestao_orquestra.negocios;

import gestao_orquestra.dados.RepositorioEmprestimo;
import gestao_orquestra.model.Emprestimo;
import gestao_orquestra.exceptions.*;

import javax.naming.LimitExceededException;
import java.time.LocalDate;

public class CadastroEmprestimo {
    private RepositorioEmprestimo repositorio;

    public CadastroEmprestimo(RepositorioEmprestimo repositorio){
        this.repositorio = repositorio;
    }

    public void registrarEmprestimo(Emprestimo emprestimo) throws LimiteEmprestimoExcedidoException, MusicoComPendenciaException, RepositorioCheioException {

        String cpfMusico = emprestimo.getMusico().getCpf();

        //Verifica se o músico tem pendências
        if (verificarPendenciaAtraso(cpfMusico)) {
            throw new MusicoComPendenciaException(cpfMusico);
        }

        //Verifica se o músico já es´ta segurnado 2 instrumentos
        if (contarInstrumentoEmprestados(cpfMusico) >= 2) {
            throw new LimiteEmprestimoExcedidoException(cpfMusico);
        }

        //Se chegar aqui, significa que passou nas verificações e está pronto para inserir no repositório
        repositorio.inserir(emprestimo);
    }



//métodos auxiliares da classe Cadastro Emprestimo

    private boolean verificarPendenciaAtraso(String cpfMusico){
        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < repositorio.getIndice(); i++) {
            Emprestimo emp = repositorio.getTodosEmprestimos()[i];

            //Procura empréstimos ativos (não devolvidos) do músico
            if (emp.getMusico().getCpf().equals(cpfMusico) && emp.getDataDevolucaoFeita() == null) {
                //SE a data prevista de entrega for antes de hoje, o músico está atrasado
                if (emp.getDataDevolucaoPrevista().isBefore(hoje)){
                    return true;
                }
            }
        } return false;
    }

    private int contarInstrumentoEmprestados(String cpfMusico) {
        int contador = 0;
        for (int i = 0; i < repositorio.getIndice(); i++) {
            Emprestimo emp = repositorio.getTodosEmprestimos()[i];

            if (emp.getMusico().getCpf().equals(cpfMusico) && emp.getDataDevolucaoFeita() == null) {
                contador++;
            }
        } return contador;
    }

    public Emprestimo buscar(String cpfMusico, String codigoTombamento) throws EmprestimoNaoEncontradoException{
        return repositorio.procurar(cpfMusico, codigoTombamento);
    }

    public void devolverEmprestimo(String cpfMusico, String codigoTombamento) throws EmprestimoNaoEncontradoException {
        //Tenta achar o empréstimo ativo, se não achar, o DAO joga o exception
        Emprestimo emprestimo = repositorio.procurar(cpfMusico, codigoTombamento);

        //Se achou, "desativa" preenchendo o dia que se devolveu o instrumento (hoje/agora)
        emprestimo.setDataDevolucaoFeita(LocalDate.now());

        //Manda o repositório salvar a alteração
        repositorio.atualizar(emprestimo);
    }

}
