package gestao_orquestra;

import java.util.Scanner;
import gestao_orquestra.negocios.OrquestraFachada;
import gestao_orquestra.model.*;
import gestao_orquestra.exceptions.*;

public class Programa {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Inicio a fachada uma única vez pra dar o ponta pé no programa.
        OrquestraFachada fachada = new OrquestraFachada();

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
            scanner.nextLine(); //Consome o "enter" que sobra no buffer do scanner

            switch (opcao) {
                case 1:
                    menuUsuarios(scanner, fachada);
                    break;

                case 2:
                    System.out.println("Menu de músicos em construção...");
                    // menuMusicos(scanner, fachada);
                    break;

                case 3:
                    System.out.println("Menu de Concertos em construção...");
                    // menuConcertos(scanner, fachada);
                    break;

                case 0:
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

            //Aqui falta pedir o perfil de acesso (maestro ou arquivista)
            //O null tá como exemplo, depois ajusto
            Usuario novoUsuario = new Usuario(login, senha, null);

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
            } catch (UsuarioNaoEncontradoException | SenhaIncorretaException e) {
                System.out.println("-> ERRO DE AUTENTICAÇÃO: " + e.getMessage());
            }
        }
    }
}