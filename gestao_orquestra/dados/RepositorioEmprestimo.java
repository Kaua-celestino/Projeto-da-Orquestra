package gestao_orquestra.dados;

import gestao_orquestra.model.Emprestimo;
import gestao_orquestra.exceptions.EmprestimoNaoEncontradoException;
import gestao_orquestra.exceptions.RepositorioCheioException;


//criação do repositório de Empréstimo
//Tem um atributo array do tipo Emprestimo
//E um atributo int para o indice
public class RepositorioEmprestimo {
    private Emprestimo[] emprestimos;
    private int indice;

    //nota mental: não preciso colocar o Emprestimo[] como parametro, já que só preciso saber o tamanho do array
    //e o indice sempre começa em 0, ent já deixa pré-determinado assim.
    public RepositorioEmprestimo(int tamanhoMaximo) {
        this.emprestimos = new Emprestimo[tamanhoMaximo];
        this.indice = 0;
    }

    public void inserir(Emprestimo emprestimo) throws RepositorioCheioException{
        if (this.indice < this.emprestimos.length) {
            this.emprestimos[this.indice] = emprestimo;
            this.indice++;

        } else {
            throw new RepositorioCheioException();
        }
    }

    public Emprestimo procurar(String cpfMusico, String codigoTombamento) throws EmprestimoNaoEncontradoException{
        for (int i = 0; i < this.indice; i++) {
            Emprestimo emp = this.emprestimos[i];
            if (emp.getMusico().getCpf().equals(cpfMusico) &&
                emp.getInstrumento().getCodigoTombamento().equals(codigoTombamento) &&
                emp.getDataDevolucaoFeita() == null) {
                return emp;
            }
        }
        throw new EmprestimoNaoEncontradoException(cpfMusico, codigoTombamento);
    }

    public void Atualizar(Emprestimo emprestimoAtualizado) throws EmprestimoNaoEncontradoException {
            for(int i = 0; i < this.indice; i++) {
                Emprestimo emp = this.emprestimos[i];
                if (emp.getMusico().getCpf().equals(emprestimoAtualizado.getMusico().getCpf()) &&
                    emp.getInstrumento().getCodigoTombamento().equals(emprestimoAtualizado.getInstrumento().getCodigoTombamento())) {
                    this.emprestimos[i] = emprestimoAtualizado;
                }
            }
            throw new EmprestimoNaoEncontradoException(emprestimoAtualizado.getMusico().getCpf(), emprestimoAtualizado.getInstrumento().getCodigoTombamento());
    }

    //Métodos auxiliares para a camada de serviço poder varrer o array.
    public Emprestimo[] getTodosEmprestimos(){
        return this.emprestimos;
    }

    public int getIndice() {
        return this.indice;
    }
}
