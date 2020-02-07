package com.practice.f_main5.f_cubing_5373;

import java.util.*;
import java.io.*;

/**
 * 틀린 풀이
 */

public class Main {

    private static int T;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = s2i(br.readLine());

        int n;

        while (T-- > 0) {
            Cube.setDefaultCube();

            n = s2i(br.readLine());

            String[] tmp = br.readLine().split(" ");

            for (int i = 0; i < n; i++) {
                cubing(tmp[i].charAt(0), tmp[i].charAt(1));
            }
            print();
        }
    }

    private static void cubing(char side, char dir) {
        Cube.cubing(side, dir);
    }

    private static void print() {
        Cube.printCube();
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Cube {

    private static final int ROW = 0;
    private static final int COL = 1;

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int FRONT = 2;
    private static final int BACK = 3;
    private static final int LEFT = 4;
    private static final int RIGHT = 5;

    // orders 배열의 행 : UPDM = U+ or D-
    private static final int UPDM = 0;
    private static final int UMDP = 1;
    private static final int LPRM = 2;
    private static final int LMRP = 3;
    private static final int FPBM = 4;
    private static final int FMBP = 5;

    // 0 : 윗면 1 : 아래면 2 : 앞면 3 : 뒷면 4 : 왼쪽면 5 : 오른쪽면
    static char[][][] cube = new char[6][3][3];

    // 큐브 돌릴 때, 면이 돌아가는 순서
    static int[][] orders = {
            {FRONT, LEFT, BACK, RIGHT, FRONT},
            {FRONT, RIGHT, BACK, LEFT, FRONT},
            {UP, FRONT, DOWN, BACK, UP},
            {UP, BACK, DOWN, FRONT, UP},
            {UP, RIGHT, DOWN, LEFT, UP},
            {UP, LEFT, DOWN, RIGHT, UP}};

    static int[][] ways = {
            {0, 0, 1, 2, 2, 1},
            {0, 0, 2, 1, 1, 2},
            {1, 2, 0, 0, 1, 2},
            {2, 1, 0, 0, 2, 1},
            {2, 1, 1, 2, 0, 0},
            {1, 2, 2, 1, 0, 0}};


    static void setDefaultCube() {
        for (int s = 0; s < 6; s++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (s == 0) cube[s][i][j] = 'w';
                    else if (s == 1) cube[s][i][j] = 'y';
                    else if (s == 2) cube[s][i][j] = 'r';
                    else if (s == 3) cube[s][i][j] = 'o';
                    else if (s == 4) cube[s][i][j] = 'g';
                    else if (s == 5) cube[s][i][j] = 'b';
                }
            }
        }
    }

    static void cubing(char side, char dir) {

        rotateCubeSide(side, dir);

        if ((side == 'U' && dir == '+') || (side == 'D' && dir == '-')) {
            goUD(side, UPDM);

        } else if ((side == 'U' && dir == '-') || (side == 'D' && dir == '+')) {
            goUD(side, UMDP);

        } else if ((side == 'L' && dir == '+') || (side == 'R' && dir == '-')) {
            goLR(side, LPRM);

        } else if ((side == 'L' && dir == '-') || (side == 'R' && dir == '+')) {
            goLR(side, LMRP);

        } else if ((side == 'F' && dir == '+') || (side == 'B' && dir == '-')) {
            goFB(side, FPBM);

        } else if ((side == 'F' && dir == '-') || (side == 'B' && dir == '+')) {
            goFB(side, FMBP);
        }
    }

    private static void goUD(char side, int updm) {
        char[] insertChar;
        insertChar = cube[FRONT][side == 'U' ? 0 : 2];

        for (int i = 0; i < 4; i++) {
            insertChar = move(insertChar, orders[updm][i], orders[updm][i + 1], side == 'U' ? 0 : 2, ROW);
        }
    }

    private static void goLR(char side, int lmrp) {
        int[] tmp = orders[lmrp];

        char[] insertChar = new char[3];
        for (int i = 0; i < 3; i++) {
            insertChar[i] = cube[UP][i][side == 'L' ? 0 : 2];
        }

        for (int i = 0; i < 4; i++) {
            insertChar = move(insertChar, tmp[i], tmp[i + 1], side == 'L' ? 0 : 2, COL);
        }
    }

    private static void goFB(char side, int fpbm) {
        int[] tmp = orders[fpbm];

        char[] insertChar;
        int index;

        if (fpbm == FPBM) {
            insertChar = cube[UP][2];
            index = 0;
        } else {
            insertChar = cube[UP][0];
            index = 2;
        }

        for (int i = 0; i < 4; i++) {
            insertChar = move(insertChar, tmp[i], tmp[i + 1], index, (i + 1) % 2 == 1 ? COL : ROW);

            index = (index + 2) % 4;
        }
    }

    static char[] move(char[] insertChar, int insert, int target, int tIndex, int tArrDir) {

        char[] returnArr = new char[3];

        if (tArrDir == ROW) {

            for (int i = 0; i < 3; i++) {
                returnArr[i] = cube[target][tIndex][i];
            }

            if (ways[insert][target] == 1) {
                for (int i = 0; i < 3; i++) {
                    cube[target][tIndex][i] = insertChar[i];
                }
            } else if (ways[insert][target] == 2) {

                for (int i = 0; i < 3; i++) {
                    cube[target][tIndex][i] = insertChar[2 - i];
                }
            }

        } else /* tArrDir == COL */ {

            for (int i = 0; i < 3; i++) {
                returnArr[i] = cube[target][i][tIndex];
            }

            if (ways[insert][target] == 1) {

                for (int i = 0; i < 3; i++) {
                    cube[target][i][tIndex] = insertChar[i];
                }

            } else if (ways[insert][target] == 2) {

                for (int i = 0; i < 3; i++) {
                    cube[target][i][tIndex] = insertChar[2 - i];
                }
            }
        }

        return returnArr;
    }

    static void rotateCubeSide(char side, char dir) {

        int target = 0;

        if (side == 'U') target = 0;
        else if (side == 'D') target = 1;
        else if (side == 'F') target = 2;
        else if (side == 'B') target = 3;
        else if (side == 'L') target = 4;
        else if (side == 'R') target = 5;

        char[][] tmp = new char[3][3];

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                tmp[r][c] = cube[target][r][c];
            }
        }

        if (dir == '+') {
            for (int r = 0; r < 3; r++) {
                for (int i = 0; i < 3; i++) {


                    cube[target][i][-r + 2] = tmp[r][i];
                }
            }

        } else if (dir == '-') {
            for (int r = 0; r < 3; r++) {
                for (int i = 0; i < 3; i++) {
                    cube[target][-i + 2][r] = tmp[r][i];
                }
            }
        }
    }

    static void printCube() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                System.out.print(cube[UP][r][c]);
            }
            System.out.print("\n");
        }
    }
}