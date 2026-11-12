package gestao_orquestra.negocios;

import gestao_orquestra.dados.*;
import gestao_orquestra.model.*;
import gestao_orquestra.exceptions.*;
import gestao_orquestra.util.GerenciadorArquivoCSV;

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

    public Usuario buscarUsuario(String login) throws UsuarioNaoEncontradoException {
        return this.cadastroUsuario.buscar(login);
    }

    public Usuario[] listarUsuarios() {
        return this.cadastroUsuario.listarTodos();
    }

    //===============================
    //DELEGRAÇÕES DE CONCERTO
    //===============================
    public void cadastrarConcerto(Concerto concerto) throws ConcertoJaCadastradoException, RepositorioCheioException {
        this.cadastroConcerto.cadastrar(concerto);
    }

    public void removerConcerto(String nomeEspetaculo) throws ConcertoNaoEncontradoException {
        this.cadastroConcerto.remover(nomeEspetaculo);
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

    public Concerto[] listarConcertos() {
        return this.cadastroConcerto.listarTodos();
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

    public Musico[] listarMusicos() {
        return this.cadastroMusico.listarTodos();
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

    public void removerInstrumento(String codigoTombamento) throws InstrumentoNaoEncontradoException {
        this.cadastroInstrumento.remover(codigoTombamento);
    }

    public Instrumento[] listarInstrumentos() {
        return this.cadastroInstrumento.listarTodos();
    }

    //===============================
    //DELEGAÇÕES DE EMPRÉSTIMO
    //===============================

    public void cadastroEmprestimo(Emprestimo emprestimo) throws LimiteEmprestimoExcedidoException, MusicoComPendenciaException, RepositorioCheioException {
        this.cadastroEmprestimo.cadastroEmprestimo(emprestimo);
    }

    public Emprestimo buscarEmprestimo(String cpfMusico, String codigoTombamento) throws EmprestimoNaoEncontradoException {
        return this.cadastroEmprestimo.buscar(cpfMusico, codigoTombamento);
    }

    public void retornarEmprestimo(String cpfMusico, String codigoTombamento) throws EmprestimoNaoEncontradoException {
        this.cadastroEmprestimo.devolverEmprestimo(cpfMusico, codigoTombamento);
    }

    public double calcularMultaEmprestimo(String cpfMusico, String codigoTombamento, double taxaDiaria) throws Exception {
        Emprestimo emprestimo = this.cadastroEmprestimo.buscar(cpfMusico, codigoTombamento);
        return emprestimo.calcularMultaEmprestimo(taxaDiaria);
    }

    public Emprestimo[] listarEmprestimos() {
        return this.cadastroEmprestimo.listarTodos();
    }


    //================================
    //DELEGAÇÕES DE PDF
    //================================
    public void gerarRelatorioAcervoPDF(String caminhoArquivo) throws Exception {
        Instrumento[] lista = this.cadastroInstrumento.listarTodos();
        GeradorPDF.gerarRelatorioAcervo(lista, lista.length, caminhoArquivo);
    }

    public void gerarProgramaConcertoPDF(String nomeEspetaculo, String caminhoArquivo) throws Exception {
        Concerto concerto = this.cadastroConcerto.buscar(nomeEspetaculo);
        GeradorPDF.gerarProgramaConcerto(concerto, caminhoArquivo);
    }

    public void gerarTermoEmprestimoPDF(String cpfMusico, String codigoTombamento, String caminhoArquivo) throws Exception {
        Emprestimo emprestimo = this.cadastroEmprestimo.buscar(cpfMusico, codigoTombamento);
        GeradorPDF.gerarTermoEmprestimo(emprestimo, caminhoArquivo);
    }

    public void gerarRelatorioLutheriaPDF(String caminhoArquivo) throws Exception {
        Instrumento[] listaManutencao = this.cadastroInstrumento.listarEmManutencao();
        GeradorPDF.gerarRelatorioLutheria(listaManutencao, caminhoArquivo);
    }

    //================================
    //DELEGAÇÕES DE PERSISTÊNCIA E BACKUP
    //================================

    public void salvarBackupGeral() throws Exception {
        GerenciadorArquivoCSV.salvarUsuarios(this.cadastroUsuario.listarTodos(), "usuarios.csv");
        GerenciadorArquivoCSV.salvarMusicos(this.cadastroMusico.listarTodos(), "musicos.csv");
        GerenciadorArquivoCSV.salvarInstrumentos(this.cadastroInstrumento.listarTodos(), "instrumentos.csv");
        GerenciadorArquivoCSV.salvarConcertos(this.cadastroConcerto.listarTodos(), "concertos.csv");
        GerenciadorArquivoCSV.salvarEmprestimos(this.cadastroEmprestimo.listarTodos(), "emprestimos.csv");
        System.out.println("-> Backup CSV salvo com sucesso!");
    }

    public void carregarBackupGeral() {
        GerenciadorArquivoCSV.carregarUsuarios(this, "usuarios.csv");
        GerenciadorArquivoCSV.carregarMusicos(this, "musicos.csv");
        GerenciadorArquivoCSV.carregarInstrumentos(this, "instrumentos.csv");
        GerenciadorArquivoCSV.carregarConcertos(this, "concertos.csv");
        GerenciadorArquivoCSV.carregarEmprestimos(this, "emprestimos.csv");
        System.out.println("-> Backup CSV restaurado em memória com sucesso!");
    }
}