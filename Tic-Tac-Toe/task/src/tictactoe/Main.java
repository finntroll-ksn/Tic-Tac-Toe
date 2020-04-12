package tictactoe;

import java.util.*;

class FieldState {
    char[][] state;
    int xNumber;
    int oNumber;

    FieldState(char[][] s, int x, int o)
    {
        state = s;
        xNumber = x;
        oNumber = o;
    }
}

class CheckResult {
    int winCombinations;
    boolean xWin;

    CheckResult(int win, boolean x)
    {
        winCombinations = win;
        xWin = x;
    }
}

public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int s = 3;
        boolean end = false;
        char[] signs = {'X', 'O'};
        int i = 0;
        String input = "         ";

        FieldState field = getFieldState(input, s);

        while (!end) {
            field = nextTurn(field, s, signs[i % 2]);
            printField(field.state, s);
            CheckResult result = checkResult(field.state, s);
            end = outputResult(field.oNumber, field.xNumber, result.winCombinations, result.xWin);
            i++;
        }
    }

    private static FieldState nextTurn(FieldState field, int size, char sign) {
        System.out.print("Enter the coordinates: ");

        int x;
        int y;

        String input = scanner.nextLine();

        input = input.replaceAll(" ", "");

        if (input.length() > 2) {
            System.out.println("You should enter numbers!");

            return nextTurn(field, size, sign);
        } else {
            x = Character.getNumericValue(input.charAt(0)) - 1;
            y = 3 - Character.getNumericValue(input.charAt(1));

            if (x > 2 || y > 2 || x < 0 || y < 0) {
                System.out.println("Coordinates should be from 1 to 3!");

                return nextTurn(field, size, sign);
            }

            if (field.state[y][x] == 'X' || field.state[y][x] == 'O') {
                System.out.println("This cell is occupied! Choose another one!");

                return nextTurn(field, size, sign);
            } else {
                field.state[y][x] = sign;

                if (sign == 'X') {
                    field.xNumber++;
                } else {
                    field.oNumber++;
                }
            }
        }

        return field;
    }

    private static FieldState getFieldState(String fieldInput, int size) {
        int xcount = 0;
        int ocount = 0;
        char[][] state = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                state[i][j] = fieldInput.charAt(i*size+j);

                if (state[i][j] == 'O') {
                    ocount++;
                } else if (state[i][j] == 'X') {
                    xcount++;
                }
            }
        }

        return new FieldState(state, xcount, ocount);
    }

    private static void printField(char[][] state, int size) {
        String border = "---------";

        System.out.println(border);

        for (int i = 0; i < size; i++) {
            System.out.print("| ");

            for (int j = 0; j < size; j++) {
                System.out.print("" + state[i][j] + " ");
            }

            System.out.println("|");
        }

        System.out.println(border);
    }

    private static boolean outputResult(int ocount, int xcount, int win, boolean xwin) {

        if (Math.abs(ocount - xcount) > 1) {
            System.out.println("Impossible");
        } else {
            if (win > 1) {
                System.out.println("Impossible");
            } else if(win == 0) {
                if (ocount + xcount == 9) {
                    System.out.println("Draw");
                } else {
                    return false;
                }
            } else {
                if (xwin) {
                    System.out.println("X wins");
                } else {
                    System.out.println("O wins");
                }
            }
        }

        return true;
    }

    private static CheckResult checkResult(char[][] state, int size) {
        int win = 0;
        boolean xwin = false;

        for (int i = 0; i < size; i++) {
            if (state[i][0] == state[i][1] && state[i][0] == state[i][2] && state[i][0] != ' ') {
                win++;
                if (state[i][0] == 'X') {
                    xwin = true;
                }
            }

            if (state[0][i] == state[1][i] && state[0][i] == state[2][i] && state[0][i] != ' ') {
                win++;
                if (state[0][i] == 'X') {
                    xwin = true;
                }
            }
        }

        if (state[0][0] == state[1][1] && state[1][1] == state[2][2] && state[1][1] != ' ') {
            win++;
            if (state[0][0] == 'X') {
                xwin = true;
            }
        }

        if (state[0][2] == state[1][1] && state[1][1] == state[2][0] && state[1][1] != ' ') {
            win++;
            if (state[1][1] == 'X') {
                xwin = true;
            }
        }

        return new CheckResult(win, xwin);
    }
}