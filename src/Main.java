import dao.ClienteMapDAO;
import dao.IClienteDAO;
import domain.Cliente;

import javax.swing.*;

public class Main {
    private static IClienteDAO idClienteDAO;

    public static void main(String[] args) {
        idClienteDAO = new ClienteMapDAO();

        String opcao = JOptionPane.showInputDialog(null, "Digite 1 para CADASTRO, 2 para CONSULTAR, 3 para EXCLUIR, 4 para ALTERAR, 5 para SAIR", "CADASTRO DE CLIENTE", JOptionPane.INFORMATION_MESSAGE);

        while (!isOpcaoValida(opcao)) {
            if ("".equals(opcao)) {
                sair();
            }
            opcao = JOptionPane.showInputDialog(null, "OPÇÃO INVÁLIDA! Digite 1 para CADASTRO, 2 para CONSULTAR, 3 para EXCLUIR, 4 para ALTERAR, 5 para SAIR", "ERRO, TENTE NOVAMENTE!", JOptionPane.INFORMATION_MESSAGE);
        }

        while (isOpcaoValida(opcao)) {
            if (isOpcaoSair(opcao)) {
                sair();
            } else if (isCadastro(opcao)) {
                String dados = JOptionPane.showInputDialog(null, "Digite os dados do cliente separados por vírgula, conforme o exemplo: NOME, CPF, TELEFONE, ENDEREÇO, NÚMERO, CIDADE e ESTADO:", "CADASTRAR CLIENTE", JOptionPane.INFORMATION_MESSAGE);
                cadastrar(dados);
            } else if (isConsultar(opcao)) {
                String dados = JOptionPane.showInputDialog(null, "Digite o CPF para consultar os dados:", "CONSULTAR DADOS", JOptionPane.INFORMATION_MESSAGE);
                consultar(dados);
            } else if (isExcluir(opcao)) {
                String dados = JOptionPane.showInputDialog(null, "Digite o CPF do cliente que deseja excluir:", "EXCLUIR CLIENTE", JOptionPane.INFORMATION_MESSAGE);
                excluir(dados);
            } else if (isAlterar(opcao)) {
                String dados = JOptionPane.showInputDialog(null, "Digite o CPF do cliente que deseja alterar:", "ALTERAR CLIENTE", JOptionPane.INFORMATION_MESSAGE);
                alterar(dados);
            }
            opcao = JOptionPane.showInputDialog(null, "Digite 1 para CADASTRO, 2 para CONSULTAR, 3 para EXCLUIR, 4 para ALTERAR, 5 para SAIR", "CADASTRO DE CLIENTE", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void excluir(String dados) {
        Long cpf = Long.parseLong(dados);
        Cliente cliente = idClienteDAO.consultar(cpf);

        if (cliente != null) {
            idClienteDAO.excluir(cpf);
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado!", "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isExcluir(String opcao) {
        return "3".equals(opcao);
    }

    private static void alterar(String dados) {
        Long cpf = Long.parseLong(dados);
        Cliente cliente = idClienteDAO.consultar(cpf);

        if (cliente != null) {
            String novosDados = JOptionPane.showInputDialog(null, "Digite os novos dados do cliente separados por vírgula, conforme o exemplo: NOME, CPF, TELEFONE, ENDEREÇO, NÚMERO, CIDADE e ESTADO:", "ALTERAR CLIENTE", JOptionPane.INFORMATION_MESSAGE);
            String[] dadosSeparados = novosDados.split(",");

            if (dadosSeparados.length < 7) {
                JOptionPane.showMessageDialog(null, "Formato incorreto! Digite os dados como: NOME, CPF, TELEFONE, ENDEREÇO, NÚMERO, CIDADE, ESTADO", "Erro de Alteração", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cliente.setNome(dadosSeparados[0]);
            cliente.setCpf(Long.parseLong(dadosSeparados[1]));
            cliente.setTel(Long.valueOf(dadosSeparados[2]));
            cliente.setEnd(dadosSeparados[3]);
            cliente.setNumero(Integer.valueOf(dadosSeparados[4]));
            cliente.setCidade(dadosSeparados[5]);
            cliente.setEstado(dadosSeparados[6]);

            idClienteDAO.alterar(cliente);
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado!", "Erro de Alteração", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isAlterar(String opcao) {
        return "4".equals(opcao);
    }

    private static void consultar(String dados) {
        Cliente cliente = idClienteDAO.consultar(Long.parseLong(dados));
        if (cliente != null) {
            JOptionPane.showMessageDialog(null, "CLIENTE ENCONTRADO: " + cliente.toString(), "CLIENTE ENCONTRADO", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "ERRO, CLIENTE NÃO ENCONTRADO!", "ERRO, CLIENTE NÃO EXISTE", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static boolean isConsultar(String opcao) {
        return "2".equals(opcao);
    }

    private static void cadastrar(String dados) {
        String[] dadosSeparados = dados.split(",");

        if (dadosSeparados.length < 7) {
            JOptionPane.showMessageDialog(null, "Formato incorreto! Digite os dados como: NOME, CPF, TELEFONE, ENDEREÇO, NÚMERO, CIDADE, ESTADO", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(dadosSeparados[0], dadosSeparados[1], dadosSeparados[2], dadosSeparados[3], dadosSeparados[4], dadosSeparados[5], dadosSeparados[6]);
        Boolean isCadastro = idClienteDAO.cadastrar(cliente);

        if (isCadastro) {
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Cliente já cadastrado");
        }
    }

    private static boolean isCadastro(String opcao) {
        return "1".equals(opcao);
    }

    private static boolean isOpcaoSair(String opcao) {
        return "5".equals(opcao);
    }

    private static void sair() {
        JOptionPane.showMessageDialog(null, "ATÉ LOGO", "BYE", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static boolean isOpcaoValida(String opcao) {
        return "1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao) || "4".equals(opcao) || "5".equals(opcao);
    }
}
