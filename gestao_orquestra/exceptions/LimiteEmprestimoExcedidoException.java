package gestao_orquestra.exceptions;

public class LimiteEmprestimoExcedidoException extends Exception{
    public LimiteEmprestimoExcedidoException(String cpfMusico) {
        super("Regra violada: O músico de CPF " + cpfMusico + " já possui o limite máximo de instrumentos emprestados");
    }
}
