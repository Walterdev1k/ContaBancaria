import java.util.Scanner;

public class ContaBancaria {
    private double saldo;
    private double chequeEspecial;
    private double limiteChequeEspecial;
    private double chequeEspecialUsado;

    public ContaBancaria(double depositoInicial) {
        this.saldo = depositoInicial;
        definirLimiteChequeEspecial(depositoInicial);
        this.chequeEspecial = this.limiteChequeEspecial;
        this.chequeEspecialUsado = 0;
    }

    private void definirLimiteChequeEspecial(double depositoInicial) {
        if (depositoInicial <= 500.00) {
            this.limiteChequeEspecial = 50.00;
        } else {
            this.limiteChequeEspecial = depositoInicial * 0.5;
        }
    }

    public double consultarSaldo() {
        return saldo;
    }

    public double consultarChequeEspecial() {
        return chequeEspecial;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public boolean isUsandoChequeEspecial() {
        return saldo < 0;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de depósito deve ser positivo!");
            return;
        }

        if (isUsandoChequeEspecial()) {
            // Primeiro paga o cheque especial usado
            double divida = Math.abs(saldo);
            if (valor >= divida) {
                // Paga toda a dívida e aplica taxa de 20%
                double taxa = chequeEspecialUsado * 0.2;
                saldo = valor - divida - taxa;
                chequeEspecial = limiteChequeEspecial;
                chequeEspecialUsado = 0;
                System.out.println("Cheque especial quitado! Taxa de 20% aplicada: R$" + String.format("%.2f", taxa));
            } else {
                // Paga parte da dívida
                saldo += valor;
                chequeEspecialUsado -= valor;
                chequeEspecial = limiteChequeEspecial - chequeEspecialUsado;
                System.out.println("Parcela do cheque especial paga. Saldo ainda negativo.");
            }
        } else {
            saldo += valor;
        }
        System.out.println("Depósito de R$" + String.format("%.2f", valor) + " realizado com sucesso!");
    }

    public boolean sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de saque deve ser positivo!");
            return false;
        }

        double saldoDisponivel = saldo + chequeEspecial;

        if (valor > saldoDisponivel) {
            System.out.println("Saldo insuficiente! Saldo disponível: R$" + String.format("%.2f", saldoDisponivel));
            return false;
        }

        if (valor <= saldo) {
            saldo -= valor;
        } else {
            // Usa cheque especial
            double valorChequeEspecial = valor - saldo;
            saldo = -valorChequeEspecial;
            chequeEspecial -= valorChequeEspecial;
            chequeEspecialUsado += valorChequeEspecial;
            System.out.println("Cheque especial utilizado: R$" + String.format("%.2f", valorChequeEspecial));
        }

        System.out.println("Saque de R$" + String.format("%.2f", valor) + " realizado com sucesso!");
        return true;
    }

    public boolean pagarBoleto(double valor) {
        if (valor <= 0) {
            System.out.println("Valor do boleto deve ser positivo!");
            return false;
        }

        double saldoDisponivel = saldo + chequeEspecial;

        if (valor > saldoDisponivel) {
            System.out.println("Saldo insuficiente para pagar o boleto! Saldo disponível: R$" + String.format("%.2f", saldoDisponivel));
            return false;
        }

        if (valor <= saldo) {
            saldo -= valor;
        } else {
            // Usa cheque especial
            double valorChequeEspecial = valor - saldo;
            saldo = -valorChequeEspecial;
            chequeEspecial -= valorChequeEspecial;
            chequeEspecialUsado += valorChequeEspecial;
            System.out.println("Cheque especial utilizado para pagamento: R$" + String.format("%.2f", valorChequeEspecial));
        }

        System.out.println("Boleto de R$" + String.format("%.2f", valor) + " pago com sucesso!");
        return true;
    }

    public void exibirInformacoes() {
        System.out.println("\n=== INFORMAÇÕES DA CONTA ===");
        System.out.println("Saldo: R$" + String.format("%.2f", saldo));
        System.out.println("Cheque Especial Disponível: R$" + String.format("%.2f", chequeEspecial));
        System.out.println("Limite Total do Cheque Especial: R$" + String.format("%.2f", limiteChequeEspecial));
        System.out.println("Usando Cheque Especial: " + (isUsandoChequeEspecial() ? "SIM" : "NÃO"));
        if (isUsandoChequeEspecial()) {
            System.out.println("Valor utilizado do cheque especial: R$" + String.format("%.2f", chequeEspecialUsado));
        }
        System.out.println("Saldo Total Disponível: R$" + String.format("%.2f", (saldo + chequeEspecial)));
        System.out.println("============================\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== BEM-VINDO AO SISTEMA BANCÁRIO ===");
        System.out.print("Digite o valor do depósito inicial: R$");
        double depositoInicial = scanner.nextDouble();

        if (depositoInicial <= 0) {
            System.out.println("Valor de depósito inicial deve ser positivo!");
            return;
        }

        ContaBancaria conta = new ContaBancaria(depositoInicial);
        System.out.println("Conta criada com sucesso!");
        System.out.println("Limite de cheque especial definido: R$" + String.format("%.2f", conta.getLimiteChequeEspecial()));

        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Consultar Saldo");
            System.out.println("2 - Consultar Cheque Especial");
            System.out.println("3 - Depositar");
            System.out.println("4 - Sacar");
            System.out.println("5 - Pagar Boleto");
            System.out.println("6 - Verificar se está usando cheque especial");
            System.out.println("7 - Exibir todas as informações");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Saldo atual: R$" + String.format("%.2f", conta.consultarSaldo()));
                    break;

                case 2:
                    System.out.println("Cheque especial disponível: R$" + String.format("%.2f", conta.consultarChequeEspecial()));
                    System.out.println("Limite total: R$" + String.format("%.2f", conta.getLimiteChequeEspecial()));
                    break;

                case 3:
                    System.out.print("Digite o valor para depositar: R$");
                    double valorDeposito = scanner.nextDouble();
                    conta.depositar(valorDeposito);
                    break;

                case 4:
                    System.out.print("Digite o valor para sacar: R$");
                    double valorSaque = scanner.nextDouble();
                    conta.sacar(valorSaque);
                    break;

                case 5:
                    System.out.print("Digite o valor do boleto: R$");
                    double valorBoleto = scanner.nextDouble();
                    conta.pagarBoleto(valorBoleto);
                    break;

                case 6:
                    if (conta.isUsandoChequeEspecial()) {
                        System.out.println("SIM - A conta está usando cheque especial.");
                    } else {
                        System.out.println("NÃO - A conta não está usando cheque especial.");
                    }
                    break;

                case 7:
                    conta.exibirInformacoes();
                    break;

                case 0:
                    System.out.println("Obrigado por usar nosso sistema bancário!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}