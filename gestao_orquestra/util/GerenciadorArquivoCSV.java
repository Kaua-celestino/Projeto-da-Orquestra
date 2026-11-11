package gestao_orquestra.util;

import gestao_orquestra.model.*;
import gestao_orquestra.negocios.OrquestraFachada;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;

public class GerenciadorArquivoCSV {

    // =========================================================================
    // 1. USUÁRIOS
    // Formato: login;senha;perfilAcesso
    // =========================================================================
    public static void salvarUsuarios(Usuario[] usuarios, String caminho) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));
        for (Usuario u : usuarios) {
            if (u != null) {
                String linha = u.getLogin() + ";" + u.getSenha() + ";" + u.getPerfil().name();
                writer.write(linha);
                writer.newLine();
            }
        }
        writer.close();
    }

    public static void carregarUsuarios(OrquestraFachada fachada, String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] d = linha.split(";");
                    Usuario u = new Usuario(d[0], d[1], PerfilAcesso.valueOf(d[2]));
                    fachada.cadastrarUsuario(u);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Nenhum backup de Usuários encontrado.");
        }
    }

    // =========================================================================
    // 2. MÚSICOS
    // Formato: cpf;nome;email;naipe;nivelTecnico
    // =========================================================================
    public static void salvarMusicos(Musico[] musicos, String caminho) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));
        for (Musico m : musicos) {
            if (m != null) {
                String linha = m.getCpf() + ";" + m.getNome() + ";" + m.getEmail() + ";" +
                        m.getNaipePrincipal().name() + ";" + m.getNivelTecnico();
                writer.write(linha);
                writer.newLine();
            }
        }
        writer.close();
    }

    public static void carregarMusicos(OrquestraFachada fachada, String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] d = linha.split(";");
                    Musico m = new Musico(d[0], d[1], d[2], Naipe.valueOf(d[3]), Integer.parseInt(d[4]));
                    fachada.cadastrarMusico(m);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Nenhum backup de Músicos encontrado.");
        }
    }

    // =========================================================================
    // 3. INSTRUMENTOS
    // Formato Corda: CORDA;codigo;modelo;estado;qtdCordas
    // Formato Sopro: SOPRO;codigo;modelo;estado;afinacao
    // =========================================================================
    public static void salvarInstrumentos(Instrumento[] instrumentos, String caminho) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));
        for (Instrumento inst : instrumentos) {
            if (inst != null) {
                String linha = "";
                if (inst instanceof InstrumentoCorda) {
                    InstrumentoCorda c = (InstrumentoCorda) inst;
                    linha = "CORDA;" + c.getCodigoTombamento() + ";" + c.getNome() + ";" +
                            c.getEstado().name() + ";" + c.getQuantidadeCordas();
                } else if (inst instanceof InstrumentoSopro) {
                    InstrumentoSopro s = (InstrumentoSopro) inst;
                    linha = "SOPRO;" + s.getCodigoTombamento() + ";" + s.getNome() + ";" +
                            s.getEstado().name() + ";" + s.getTipoPalheta();
                }
                writer.write(linha);
                writer.newLine();
            }
        }
        writer.close();
    }

    public static void carregarInstrumentos(OrquestraFachada fachada, String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] d = linha.split(";");
                    String tipo = d[0];
                    EstadoConservacao estado = EstadoConservacao.valueOf(d[3]);

                    if (tipo.equals("CORDA")) {
                        InstrumentoCorda corda = new InstrumentoCorda(d[1], d[2], estado, Integer.parseInt(d[4]));
                        fachada.cadastrarInstrumento(corda);
                    } else if (tipo.equals("SOPRO")) {
                        InstrumentoSopro sopro = new InstrumentoSopro(d[1], d[2], estado, d[4]);
                        fachada.cadastrarInstrumento(sopro);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("-> Nenhum backup de Instrumentos encontrado.");
        }
    }

    // =========================================================================
    // 4. CONCERTOS
    // Formato: nomeEspetaculo;data;local;loginMaestro
    // =========================================================================
    public static void salvarConcertos(Concerto[] concertos, String caminho) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));
        for (Concerto c : concertos) {
            if (c != null) {
                String loginMaestro = (c.getMaestro() != null) ? c.getMaestro().getLogin() : "SEM_MAESTRO";
                String linha = c.getNomeEspetaculo() + ";" + c.getData().toString() + ";" +
                        c.getLocal() + ";" + loginMaestro;
                writer.write(linha);
                writer.newLine();
            }
        }
        writer.close();
    }

    public static void carregarConcertos(OrquestraFachada fachada, String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] d = linha.split(";");
                    Usuario maestro = null;
                    try {
                        maestro = fachada.buscarUsuario(d[3]);
                    } catch (Exception ignored) {}

                    Concerto concerto = new Concerto(d[0], LocalDate.parse(d[1]), d[2], maestro);
                    fachada.cadastrarConcerto(concerto);
                }
            }
        } catch (Exception e) {
            System.out.println("-> Nenhum backup de Concertos encontrado.");
        }
    }

    // =========================================================================
    // 5. EMPRÉSTIMOS
    // Formato: cpfMusico;codigoTombamento;dataRetirada;dataDevolucaoPrevista;dataDevolucaoFeita
    // =========================================================================
    public static void salvarEmprestimos(Emprestimo[] emprestimos, String caminho) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));
        for (Emprestimo e : emprestimos) {
            if (e != null) {
                String devFeita = (e.getDataDevolucaoFeita() != null) ? e.getDataDevolucaoFeita().toString() : "NULL";
                String linha = e.getMusico().getCpf() + ";" +
                        e.getInstrumento().getCodigoTombamento() + ";" +
                        e.getDataRetirada().toString() + ";" +
                        e.getDataDevolucaoPrevista().toString() + ";" +
                        devFeita;
                writer.write(linha);
                writer.newLine();
            }
        }
        writer.close();
    }

    public static void carregarEmprestimos(OrquestraFachada fachada, String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] d = linha.split(";");
                    Musico m = fachada.buscarMusico(d[0]);
                    Instrumento inst = fachada.buscarInstrumento(d[1]);

                    if (m != null && inst != null) {
                        Emprestimo emp = new Emprestimo(m, inst, LocalDate.parse(d[2]), LocalDate.parse(d[3]));
                        if (!d[4].equals("NULL")) {
                            emp.setDataDevolucaoFeita(LocalDate.parse(d[4]));
                        }
                        fachada.cadastroEmprestimo(emp);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("-> Nenhum backup de Empréstimos encontrado.");
        }
    }
}