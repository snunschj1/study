package com.practice.c_main2.c_ladder_15684;

/**
 * 출처 : https://www.acmicpc.net/source/14774333
 */

import java.util.*;
import java.io.*;

public class Main2 {

    private static final boolean EXIST = true;
    private static final boolean NON_EXIST = false;

    // row = 가로선 col = 세로선
    private static int row, col;
    private static int numberOfInitLadder;

    private static boolean[][] map;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        col = s2i(st.nextToken());
        numberOfInitLadder = s2i(st.nextToken());
        row = s2i(st.nextToken());

        map = new boolean[row + 2][col + 1];

        for (int i = 0; i < numberOfInitLadder; i++) {
            st = new StringTokenizer(br.readLine());
            int lr = s2i(st.nextToken());
            int lc = s2i(st.nextToken());
            map[lr][lc] = EXIST;
        }
    }

    private static void solve() {
        if (isDisabled()) {
            System.out.println("-1");
            return;
        }

        for (int usableLadderNum = 0; usableLadderNum <= 3; usableLadderNum++) {
            addLadder(1, 1, 0, usableLadderNum);
        }

        System.out.println("-1");
    }

    private static boolean isDisabled() {
        int ladderCnt, columnCnt = 0;

        for (int c = 1; c < col + 1; c++) {
            ladderCnt = 0;
            for (int r = 1; r < row + 2; r++) {
                if (map[r][c]) {
                    ladderCnt++;
                }
            }

            if (ladderCnt % 2 == 1) {
                columnCnt++;
            }
        }

        if (columnCnt > 3) {
            return true;
        } else {
            return false;
        }
    }

    private static void addLadder(int r, int c, int usedLadderNum, int usableLadderNum) {
        if (usedLadderNum == usableLadderNum) {
            if (check()) {
                System.out.println(usableLadderNum);
                System.exit(0);
            }
            return;
        }

        int j = c;
        for (int i = r; i < row + 1; i++) {
            while (j <= col - 1) {
                if (map[i][j + 1]) {
                    j += 3;
                } else if (map[i][j]) {
                    j += 2;
                } else if (map[i][j - 1]) {
                    j += 1;
                } else {
                    map[i][j] = EXIST;
                    addLadder(i, j + 2, usedLadderNum + 1, usableLadderNum);
                    map[i][j] = NON_EXIST;
                    j++;
                }
            }
            j = 1;
        }
    }


    private static boolean check() {

        int r;
        int tmpC;

        for (int c = 1; c < col; c++) {
            r = 0;
            tmpC = c;

            while (r < row + 2) {
                if (map[r][tmpC - 1]) {
                    tmpC--;
                } else if (map[r][tmpC]) {
                    tmpC++;
                }

                r++;
            }

            if (tmpC != c) {
                return false;
            }
        }
        return true;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
