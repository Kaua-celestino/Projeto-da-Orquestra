package gestao_orquestra;

import java.time.LocalDate;
import java.util.Scanner;
import gestao_orquestra.negocios.OrquestraFachada;
import gestao_orquestra.model.*;
import gestao_orquestra.exceptions.*;

public class Programa {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicio a fachada uma única vez pra dar o ponta pé no programa.
        OrquestraFachada fachada = new OrquestraFachada();

        // 1. Carrega os dados do CSV para a memória
        fachada.carregarBackupGeral();

        // 2. Se a base de dados estiver vazia, carrega os dados fictícios
        if (fachada.listarMusicos().length == 0) {
            popularDadosIniciais(fachada);
        }

        try {
            fachada.gerarRelatorioAcervoPDF("Relatorio_Acervo.pdf");
            fachada.gerarRelatorioLutheriaPDF("Relatorio_Lutheria.pdf");
            fachada.gerarProgramaConcertoPDF("Sinfonia nº 5 de Beethoven", "Programa_Concerto.pdf");
            fachada.gerarTermoEmprestimoPDF("111.222.333-44", "INST-001", "Termo_Emprestimo.pdf");

            System.out.println("-> Todos os relatórios PDF foram gerados com sucesso!");
        } catch (Exception e) {
            System.out.println("-> Falha ao gerar PDFs: " + e.getMessage());
        }

        int opcao = -1;

        System.out.println("=======================================");
        System.out.println("   SISTEMA DE GESTÃO DE ORQUESTRA      ");
        System.out.println("=======================================");

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Submenu de Usuários (Login/Cadastro)");
            System.out.println("2. Submenu de Músicos");
            System.out.println("3. Submenu de Concertos");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome o "enter" que sobra no buffer do scanner

            switch (opcao) {
                case 1:
                    menuUsuarios(scanner, fachada);
                    break;

                case 2:
                    System.out.println("Menu de músicos em construção...");
                    break;

                case 3:
                    System.out.println("Menu de Concertos em construção...");
                    break;

                case 0:
                    System.out.println("A guardar os dados no CSV...");
                    try {
                        fachada.salvarBackupGeral();
                    } catch (Exception e) {
                        System.out.println("-> Erro ao guardar backup: " + e.getMessage());
                    }
                    System.out.println("Encerrando o sistema da Orquestra. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    //===================================
    //SUBMENUS
    //===================================

    private static void menuUsuarios(Scanner scanner, OrquestraFachada fachada) {
        System.out.println("\n--- MENU DE USUÁRIOS ---");
        System.out.println("1. Cadastrar novo Usuário");
        System.out.println("2. Fazer Login");
        System.out.print("Escolha: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            System.out.println("Digite o login: ");
            String login = scanner.nextLine();
            System.out.println("Digite a senha: ");
            String senha = scanner.nextLine();

            Usuario novoUsuario = new Usuario(login, senha, PerfilAcesso.MAESTRO); // Ajustado para não ser null

            try {
                fachada.cadastrarUsuario(novoUsuario);
                System.out.println("-> Usuário cadastrado com sucesso!");
            } catch (UsuarioJaCadastradoException | RepositorioCheioException e) {
                System.out.println("-> ERRO: " + e.getMessage());
            }
        } else if (op == 2) {
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            try {
                Usuario logado = fachada.fazerLogin(login, senha);
                System.out.println("-> Login efetuado com sucesso! Bem-vindo, " + logado.getLogin());
            } catch (Exception e) {
                System.out.println("-> ERRO DE AUTENTICAÇÃO: " + e.getMessage());
            }
        }
    }

    private static void popularDadosIniciais(OrquestraFachada fachada) {
        try {
            // 1. Criar usuário admin
            Usuario admin = new Usuario("admin", "1234", PerfilAcesso.MAESTRO);
            fachada.cadastrarUsuario(admin);

            // 2. Criar músicos
            Musico m1 = new Musico("111.222.333-44", "João Silva", "joao@email.com", Naipe.CORDAS, 8);
            Musico m2 = new Musico("555.666.777-88", "Maria Santos", "maria@email.com", Naipe.SOPROS, 9);
            fachada.cadastrarMusico(m1);
            fachada.cadastrarMusico(m2);

            // 3. Criar Instrumentos
            InstrumentoCorda violino = new InstrumentoCorda("INST-001", "Violino Eagle 4/4", EstadoConservacao.BOM, 4);
            InstrumentoSopro flauta = new InstrumentoSopro("INST-002", "Flauta Transversal Yamaha", EstadoConservacao.PRECISA_REPARO, "C");
            fachada.cadastrarInstrumento(violino);
            fachada.cadastrarInstrumento(flauta);

            // 4. Criar Concerto e escalar músico
            Concerto concerto = new Concerto("Sinfonia nº 5 de Beethoven", java.time.LocalDate.of(2026, 12, 20), "Teatro Santa Isabel", admin);
            fachada.cadastrarConcerto(concerto);
            fachada.escalarMusicoNoConcerto("Sinfonia nº 5 de Beethoven", m1);

            // 5. Criar empréstimo (Método corrigido)
            Emprestimo emp = new Emprestimo(m1, violino, LocalDate.now(), LocalDate.now().plusDays(7));
            fachada.cadastroEmprestimo(emp);

            System.out.println("-> Carga inicial de dados carregada em memória com sucesso!");
        } catch (Exception e) {
            System.out.println("-> Erro na carga inicial: " + e.getMessage());
        }
    }
}