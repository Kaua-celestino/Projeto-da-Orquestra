package gestao_orquestra.model;

//Qualquer pessoa que use o programa terá que ter um cadastro como usuário
//Sendo separado entre Maestro ou Arquivista
public class Usuario {
    private String login;
    private String senha;
    private PerfilAcesso perfil;

    //Criando construtor de usuário
    public Usuario(String login, String senha, PerfilAcesso perfil) {
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    //Criando métodos get set para cada atribudo Usuario
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilAcesso getPerfil() {
        return perfil;
    }
    public void setPerfil(PerfilAcesso perfil) {
        this.perfil = perfil;
    }
}
