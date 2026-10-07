package gestao_orquestra.EXCEPTIONS;

public class RepositorioCheioException extends Exception{
    public RepositorioCheioException() {
        super("Erro: O repositório atingiu a capacidade máxima.");
    }
}
