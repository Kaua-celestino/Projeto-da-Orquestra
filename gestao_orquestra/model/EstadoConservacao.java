package gestao_orquestra.model;

//A classe EstadoConservacao separa os estados de cuidado de cada instrumento em:
//Novo: Extremamente apto para tocar
//Bom: Bom para tocar
//Precisa_reparo: Necessita de reparos para tocar
//Inutilizavel: Impossibilitado de tocar
public enum EstadoConservacao {
    NOVO,
    BOM,
    PRECISA_REPARO,
    INUTILIZAVEL
}
