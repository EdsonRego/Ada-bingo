import java.util.Arrays;
import java.util.Scanner;

public class AdaBingoGame {
    public static void main(String[] args) {

        System.out.println("|*=*=*=*=*=*=*=*=*=*=*=*=*=*=*|");
        System.out.println("|                             |");
        System.out.println("|    => SUPER BINGO 50+ <=    |");
        System.out.println("|                             |");
        System.out.println("|*=*=*=*=*=*=*=*=*=*=*=*=*=*=*|");

        Scanner sc = new Scanner(System.in);

        System.out.println("Escolha o modo de jogo:");
        System.out.print("Digite  [A]-Automático  [M]-Manual: ");
        String modoDoJogo = sc.nextLine();
        int[] bolas = new int[quantBolas];

        if (modoDoJogo.equalsIgnoreCase("A")) {
            modoAutomatico(sc, bolas);

        } else if (modoDoJogo.equalsIgnoreCase("M")) {
            sorteioManual();
        } else {
            System.out.println("Modo inválido");
        }

    }
    /** ################################### ÁREA DEFINICÃO DAS CONSTANTES ########################################### */
    final static int tamanhoCartela = 15;
    final static int quantBolas = 60;

    /** ################################### ÁREA DE CRIAÇÃO DOS MÉTODOS ESTÁTICOS ###################################
     *  ################################### MÉTODOS ESTATICOS GAME AUTOMÁTICO ####################################### */
    static void modoAutomatico(Scanner sc, int[] bolas) {
        int[] numeroLinhas;
        int[][] copiaCartoes;
        int numCartao;
        int[][] cartoes;
        System.out.print("Informe o número de jogadores: ");
        numCartao = sc.nextInt();

        while (numCartao <= 1) {
            System.out.println("Número inválido! Tem que ser maior ou igual a 2:");
            System.out.print("Informe o número de jogadores: ");
            numCartao = sc.nextInt();
        }

        cartoes = new int[numCartao][tamanhoCartela];
        copiaCartoes = new int[numCartao][tamanhoCartela];
        numeroLinhas = new int[numCartao];

        completarCartela(cartoes);

        copiarCartela(cartoes, copiaCartoes);

        for (int i = 0; i < cartoes.length; i++) {
            System.out.println("player-" + i);
            imprimirCartela(i, cartoes);
        }

        jogarBola(bolas, copiaCartoes, cartoes, numeroLinhas);
    }
    static void completarCartela(int[][] cartoes) {
        int num;
        boolean check;
        for (int i = 0; i < cartoes.length; i++) {
            for (int j = 0; j < cartoes[i].length; j++) {
                do {
                    num = numRandom();
                    check = checkNumero(cartoes, num, i);
                } while (check);
                cartoes[i][j] = num;
            }
            Arrays.sort(cartoes[i]);
        }
    }

    static void copiarCartela(int[][] cartoes, int[][] copiaCartoes) {
        for (int i = 0; i < cartoes.length; i++) {
            System.arraycopy(cartoes[i], 0, copiaCartoes[i], 0, cartoes[0].length);
        }
    }

    static void imprimirCartela(int i, int[][] arrayImprimir) {
        for (int j = 0; j < arrayImprimir[0].length; j++) {
            System.out.print(arrayImprimir[i][j] + " ");
            if ((j + 1) % 10 == 0 || (j + 1) % 10 == 5) {
                System.out.println();
            }
        }
        System.out.println();
    }

    static boolean checkNumero(int[][] cartao, int num, int nCartao) {
        boolean repetido = false;

        for (int i = 0; i < cartao[nCartao].length; i++) {
            if (num == cartao[nCartao][i]) {
                repetido = true;
                break;
            }
        }
        return repetido;
    }

    static void jogarBola(int[] arraybolas, int[][] arrayCopiaCartao, int[][] cartoes, int[] numeroLinhas) {
        boolean checkBola, bingo = false, linha;
        int num, contador = 0;

        do {
            for (int i = 0; i < arraybolas.length; i++) {

                do {
                    num = numRandom();
                    checkBola = verificarBola(arraybolas, num);
                } while (checkBola);
                arraybolas[i] = num;
                contador++;
                System.out.println();
                System.out.println("Bola " + num);
                System.out.println();
                restaNumeroCopiaArray(num, arrayCopiaCartao);
                for (int j = 0; j < cartoes.length; j++) {
                    linha = checkLinha(j, arrayCopiaCartao, cartoes, numeroLinhas);
                    if (linha) {
                        numeroLinhas[j] += 1;
                    }
                    if (numeroLinhas[j] > 2) {
                        bingo = checkBingo(j, arrayCopiaCartao, cartoes);
                        if (bingo) {
                            i = arraybolas.length - 1;
                            j = cartoes.length - 1;
                        }
                    }
                }
            }
        } while (!bingo);
        System.out.println();
        System.out.println("Total de " + contador + " bolas!");
        System.out.println();
        for (int i = 0; i < arraybolas.length; i++) {
            if (arraybolas[i] != 0) {
                System.out.printf("%3d", arraybolas[i]);
                if ((i + 1) % 10 == 0) {
                    System.out.println();
                }
            }
        }
    }

    static boolean verificarBola(int[] checkArray, int num) {
        boolean repetido = false;
        for (int j : checkArray) {
            if (num == j) {
                repetido = true;
                break;
            }
        }
        return repetido;
    }

    static void restaNumeroCopiaArray(int numeroBola, int[][] copiaCartao) {

        for (int i = 0; i < copiaCartao.length; i++) {
            for (int j = 0; j < copiaCartao[i].length; j++) {
                if (numeroBola == copiaCartao[i][j]) {
                    copiaCartao[i][j] = 0;
                }
            }
        }
    }

    static boolean checkBingo(int i, int[][] copiaCartao, int[][] cartoes) {
        boolean bingo = false;
        int controlador = 0;

        for (int j = 0; j < copiaCartao[i].length; j++) {
            controlador = controlador + copiaCartao[i][j];
        }

        if (controlador == 1500) {
            bingo = true;
            System.out.println();
            System.out.printf("Bingo para o cartão " + i + " com os números ");
            System.out.println();
            imprimirCartela(i, cartoes);
        }
        return bingo;
    }

    static boolean checkLinha(int i, int[][] copiaCartoes, int[][] cartoes, int[] numeroLinhas) {
        boolean checkLinha = false;
        int linha;

        linha = copiaCartoes[i][0] + copiaCartoes[i][1] + copiaCartoes[i][2] + copiaCartoes[i][3] + copiaCartoes[i][4];
        if (linha == 0) {
            checkLinha = true;
            copiaCartoes[i][0] = 100;
            copiaCartoes[i][1] = 100;
            copiaCartoes[i][2] = 100;
            copiaCartoes[i][3] = 100;
            copiaCartoes[i][4] = 100;

        }
        else {
            linha = copiaCartoes[i][5] + copiaCartoes[i][6] + copiaCartoes[i][7] + copiaCartoes[i][8] + copiaCartoes[i][9];
            if (linha == 0) {
                checkLinha = true;
                copiaCartoes[i][5] = 100;
                copiaCartoes[i][6] = 100;
                copiaCartoes[i][7] = 100;
                copiaCartoes[i][8] = 100;
                copiaCartoes[i][9] = 100;

            } else {
                linha = copiaCartoes[i][10] + copiaCartoes[i][11] + copiaCartoes[i][12] + copiaCartoes[i][13] + copiaCartoes[i][14];
                if (linha == 0) {
                    checkLinha = true;
                    copiaCartoes[i][10] = 100;
                    copiaCartoes[i][11] = 100;
                    copiaCartoes[i][12] = 100;
                    copiaCartoes[i][13] = 100;
                    copiaCartoes[i][14] = 100;

                }
            }
        }

        if (numeroLinhas[i] < 2) {
            if (checkLinha) {
                System.out.println();
                System.out.println("Existe linha no cartão: " + i);

                imprimirCartela(i, cartoes);

                System.out.println();
            }
        }
        return checkLinha;
    }
    /** ################################### MÉTODOS ESTATICOS USO GERAL ############################################ */
    static int numRandom() {
        return ((int) (1 + Math.random() * quantBolas));
    }

    static String entradaDeDados() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        return input;
    }

    static int leCartela(int[] input, int n) {
        int umNumero = 0;
        for (int i = 0; i < 1; i++) {
            umNumero = input[n];
        }
        return umNumero;
    }

    static boolean verificaNumeroRepetido(int carton[][], int num, int nCarton) {
        boolean repetido = false;
        for (int i = 0; i < carton[nCarton].length; i++) {
            if (num == carton[nCarton][i]) {
                repetido = true;
            }
        }
        return repetido;
    }

    /**
     * ################################### MÉTODOS ESTATICOS GAME MANUAL #############################################
     */

    static int[][] geradorDeCartelasManual() {

        String entradaCartelasManual = "1,2,3,4,5-6,7,8,9,1-2,3,4,5,6";
        String entradaAux1 = entradaCartelasManual.replace("-", ",");
        String entradaAux2 = entradaAux1.replace(",", " ");
        String[] cartelasAux = entradaAux2.split(" ");
        int[] cartela = new int[cartelasAux.length];
        int quantCartelas = cartela.length / tamanhoCartela;
        int[][] cartelas = new int[quantCartelas][tamanhoCartela];
        int cont = 0;
        for (int i = 0; i < cartelasAux.length; i++) {
            cartela[i] = Integer.parseInt(cartelasAux[i]);
        }
        for (int i = 0; i < quantCartelas; i++) {
            for (int j = 0; j < tamanhoCartela; j++) {
                cartelas[i][j] = leCartela(cartela, cont++);
                System.out.print(cartelas[i][j] + "\t");
            }
            System.out.println();
        }
        return cartelas;
    }

    static String entradaJogador() {
        String jogador = entradaDeDados();
        return jogador;
    }

    static void sorteioManual() {
        System.out.println("Modo manual indisponível no momento!");
        System.out.println("Deseja continuar?");
        System.out.print("Digite [S]: ");
        String continuar = entradaJogador();
        if(continuar.equalsIgnoreCase("S")){
            main(null);
        }else {
            System.out.println("Obrigado");
        }

    }
}