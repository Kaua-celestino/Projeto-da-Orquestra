package gestao_orquestra.model;

//A classe Musico serve para registrar as pessoas que de fato vão tocar na orquestra com seus respectivos instrumentos.
public class Musico {
    private String cpf;
    private String nome;
    private String email;
    private Naipe naipePrincipal;
    private int nivelTecnico;

    public Musico(String cpf, String nome, String email, Naipe naipePrincipal, int nivelTecnico) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.naipePrincipal = naipePrincipal;
        this.nivelTecnico = nivelTecnico;
    }

    //Criando métodos get set para cada atribudo de Musico
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Naipe getNaipePrincipal() {
        return naipePrincipal;
    }
    public void setNaipePrincipal(Naipe naipePrincipal) {
        this.naipePrincipal = naipePrincipal;
    }

    public int getNivelTecnico() {
        return nivelTecnico;
    }
    public void setNivelTecnico(int nivelTecnico) {
        this.nivelTecnico = nivelTecnico;
    }
}