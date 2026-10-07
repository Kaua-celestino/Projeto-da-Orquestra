package gestao_orquestra.exceptions;

public class MusicoJaCadastradoException extends Exception{
    public MusicoJaCadastradoException(String cpf) {
        super("A operação falhou: Já existe um músico cadastrado com o CPF " + cpf + ".");
    }
}
