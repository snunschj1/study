package com.practice.d_main3.c_gerrymandering2_17779;

/**
    출처 : https://www.acmicpc.net/source/16122187
 */
import java.util.*;
import java.io.*;

public class Main2 {

    private static int[][] map;

    private static int N;

    private static int answer = 40000;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = s2i(br.readLine());

        map = new int[N+1][N+1];
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = s2i(st.nextToken());
            }
        }

    }

    private static void solve() {
        for (int i = 1; i <= N - 2; i++) {
            for (int j = 1; j <= N - 1; j++) {
                for (int d1 = 1; d1 <= j-1; d1++) {
                    for (int d2 = 1; d2 <= N-j; d2++) {
                        start(i, j, d1, d2);
                    }
                }
            }
        }
        System.out.println(answer);
    }

    private static void start(int row, int col, int d1, int d2) {
        if (row + d1 + d2 > N) {
            return;
        }

        int[] gu = new int[6];

        for (int r = 1; r <= N; r++) {

            int lineCol1 = -1;
            int lineCol2 = -1;

            // Todo : 경계선이 존재할 row 범위
            if (row <= r && r <= row + d1 + d2) {
                lineCol1 = col;
                lineCol2 = col;

                if (r <= row + d1) {
                    lineCol1 -= r - row;
                } else {
                    lineCol1 -= d1;
                    lineCol1 += r - (row + d1);
                }

                if (r <= row + d2) {
                    lineCol2 += r - row;
                } else {
                    lineCol2 += d2;
                    lineCol2 -= r - (row + d2);
                }
            }

            for (int c = 1; c <= N; c++) {

                // Todo : 선거구 5부터 조건문을 실행한다.
                if (lineCol1 <= c && c <= lineCol2) {
                    gu[5] += map[r][c];
                } else if (r < row + d1 && c <= col) {
                    gu[1] += map[r][c];
                } else if (r <= row + d2 && col < c) {
                    gu[2] += map[r][c];
                } else if (row + d1 <= r && c < col - d1 + d2) {
                    gu[3] += map[r][c];
                } else /* if (row + d2 < r && col - d1 + d2 <= c) */ {
                    gu[4] += map[r][c];
                }
            }
        }

        int max = 0;
        int min = 40000;
        for (int i = 1; i < 6; i++) {
            max = Math.max(max, gu[i]);
            min = Math.min(min, gu[i]);
        }

        if (max - min < answer) {
            answer = max - min;
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
