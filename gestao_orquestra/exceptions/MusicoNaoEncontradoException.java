package gestao_orquestra.exceptions;

public class MusicoNaoEncontradoException extends Exception{
    public MusicoNaoEncontradoException(){
        super("A Operação falhou: Músico não encontrado no sistema.");
    }

    public MusicoNaoEncontradoException(String cpf){
        super("A Operação falhou: Músico com o CPF" + cpf + "não foi encontrado.");
    }
}
