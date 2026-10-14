package gestao_orquestra.exceptions;

public class MusicoComPendenciaException extends Exception{
    public MusicoComPendenciaException(String cpfMusico){
        super("Regra violada: O músico de CPF " + cpfMusico + " possui devoluções pendentes.");
    }
}
