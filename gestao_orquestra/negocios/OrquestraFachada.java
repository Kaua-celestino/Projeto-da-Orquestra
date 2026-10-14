package gestao_orquestra.negocios;

import gestao_orquestra.dados.*;
import gestao_orquestra.model.*;
import gestao_orquestra.exceptions.*;

public class OrquestraFachada {
    //A Fachada guarda APENAS as classes de negócios
    private CadastroConcerto cadastroConcerto;
    private CadastroEmprestimo cadastroEmprestimo;
    private CadastroInstrumento cadastroInstrumento;
    private CadastroMusico cadastroMusico;
    private CadastroUsuario cadastroUsuario;

    //O sistema nasce aq criando os repositórios e entregando pros cadastros
    public OrquestraFachada() {
        RepositorioConcerto repConcerto = new RepositorioConcerto(100);
        RepositorioEmprestimo repEmprestimo = new RepositorioEmprestimo(500);
        RepositorioInstrumento repInstrumento = new RepositorioInstrumento(300);
        RepositorioMusico repMusico = new RepositorioMusico(200);
        RepositorioUsuario repUsuario = new RepositorioUsuario(250);

        this.cadastroConcerto = new CadastroConcerto(repConcerto);
        this.cadastroEmprestimo = new CadastroEmprestimo(repEmprestimo);
        this.cadastroInstrumento = new CadastroInstrumento(repInstrumento);
        this.cadastroMusico = new CadastroMusico(repMusico);
        this.cadastroUsuario = new CadastroUsuario(repUsuario);
    }

    //===============================
    //DELEGAÇÕES DE USUÁRIO
    //===============================

    public void cadastrarUsuario(Usuario usuario) throws UsuarioJaCadastradoException, RepositorioCheioException {
        this.cadastroUsuario.cadastrar(usuario);
    }

    public Usuario fazerLogin(String login, String senha) throws UsuarioNaoEncontradoException, SenhaIncorretaException {
        return this.cadastroUsuario.fazerLogin(login, senha);
    }

    public void removerUsuario(String login) throws UsuarioNaoEncontradoException {
        this.cadastroUsuario.remover(login);
    }

    //===============================
    //DELEGRAÇÕES DE CONCERTO
    //===============================
    public void cadastrarConcerto(Concerto concerto) throws ConcertoJaCadastradoException, RepositorioCheioException {
        this.cadastroConcerto.cadastrar(concerto);
    }

    public void escalarMusicoNoConcerto(String nomeEspetaculo, Musico musico) throws ConcertoNaoEncontradoException {
        this.cadastroConcerto.escalarMusicoNoConcerto(nomeEspetaculo, musico);
    }

    public void removerMusicoDoConcerto(String nomeEspetaculo, String cpfMusico) throws ConcertoNaoEncontradoException, MusicoNaoEncontradoException {
        this.cadastroConcerto.removerMusicoDoConcerto(nomeEspetaculo, cpfMusico);
    }

    public void aprovarConcerto(String nomeEspetaculo) throws ConcertoNaoEncontradoException, ConcertoInviavelException {
        this.cadastroConcerto.aprovarConcerto(nomeEspetaculo);
    }

    //===============================
    //DELEGAÇÕES DE MÚSICO
    //===============================
    public void cadastrarMusico(Musico musico) throws MusicoJaCadastradoException, RepositorioCheioException {
        this.cadastroMusico.cadastrarMusico(musico);
    }

    public Musico buscarMusico(String cpfMusico) throws MusicoNaoEncontradoException {
        return this.cadastroMusico.buscar(cpfMusico);
    }

    public void removerMusico(String cpfMusico) throws MusicoNaoEncontradoException {
        this.cadastroMusico.remover(cpfMusico);
    }

    //===============================
    //DELEGAÇÕES DE INSTRUMENTO
    //===============================

    public void cadastrarInstrumento(Instrumento instrumento) throws InstrumentoJaCadastradoException, RepositorioCheioException {
        this.cadastroInstrumento.cadastrarInstrumento(instrumento);
    }

    public Instrumento buscarInstrumento(String codigoTombamento) throws InstrumentoNaoEncontradoException {
        return this.cadastroInstrumento.buscar(codigoTombamento);
    }

    //===============================
    //DELEGAÇÕES DE EMPRÉSTIMO
    //===============================

    public void realizarEmprestimo(Emprestimo emprestimo) throws LimiteEmprestimoExcedidoException, MusicoComPendenciaException, RepositorioCheioException {
        this.cadastroEmprestimo.registrarEmprestimo(emprestimo);
    }

    public Emprestimo buscarEmprestimo(String cpfMusico, String codigoTombamento) throws EmprestimoNaoEncontradoException{
        return this.cadastroEmprestimo.buscar(cpfMusico, codigoTombamento);
    }

    public void retornarEmprestimo(String cpfMusico, String codigoTombamento) throws EmprestimoNaoEncontradoException {
        this.cadastroEmprestimo.devolverEmprestimo(cpfMusico, codigoTombamento);
    }
}
