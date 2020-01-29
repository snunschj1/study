package com.practice.c_main2.c_ladder_15684;

import java.io.*;
import java.util.*;

public class Daesang {

    private static int N, M, H;

    private static boolean[][] check;

    private static int min = 4;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());
        H = s2i(st.nextToken());

        check = new boolean[H][N];

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());

            int r = s2i(st.nextToken()) - 1;
            int c = s2i(st.nextToken()) - 1;

            check[r][c + 1] = true;
        }

        br.close();
    }

    private static void solve() {
        if (isDisabled()) {
            System.out.println("-1");
            return;
        }

        addLadders(0, 0, 0);

        if (min == 4) min = -1;
        System.out.println(min);
    }

    private static boolean isDisabled() {
        int ladderCnt, columnCnt = 0;

        for (int c = 1; c < N; c++) {
            ladderCnt = 0;
            for (int r = 0; r < H; r++) {
                if (check[r][c]) {
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

    private static void addLadders(int r, int c, int cnt) {
        if (cnt >= min) return;

        if (go()) {
            min = cnt;
            return;
        }

        if (cnt == 3) return;

        for (int i = r; i < H; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (!check[i][j] && !check[i][j+1]) {
                    check[i][j+1] = true;
                    addLadders(i, j, cnt + 1);
                    check[i][j+1] = false;
                }
            }
        }
    }

    private static boolean go() {
        for (int c = 0; c < N; c++) {
            int finish = c;

            for (int r = 0; r < H; r++) {
                if (finish + 1 < N && check[r][finish + 1]) finish++;
                else if (finish >= 0 && check[r][finish]) finish--;
            }

            if (c != finish) return false;
        }

        return true;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
