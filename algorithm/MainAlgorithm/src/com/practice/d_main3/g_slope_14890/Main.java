package com.practice.d_main3.g_slope_14890;

import java.util.*;
import java.io.*;

public class Main {

    private static final int DOWN = 1;
    private static final int NOT_DOWN = 0;

    private static int N;
    private static int L;

    private static int[][] map;

    private static int result = 0;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        L = s2i(st.nextToken());

        map = new int[N][N];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = s2i(st.nextToken());
            }
        }

        br.close();
    }

    private static void solve() {
        for (int r = 0; r < N; r++) {
            goHorizontal(r, 0, 1, NOT_DOWN);
        }

        for (int c = 0; c < N; c++) {
            goVertical(0, c, 1, NOT_DOWN);
        }

        System.out.println(result);
    }

    private static void goHorizontal(int row, int col, int cnt, int dir) {
//        System.out.printf("goHorizontal : row = %d, col = %d, cnt = %d, dir = %d\n", row, col, cnt, dir);
        if (col == N-1) {
            if (dir != DOWN) {
//                System.out.println("Horizontal : row = " + row);
                result += 1;
                return;
            } else {
                if (L == 1) {
//                    System.out.println("Horizontal : row = " + row);
                    result += 1;
                    return;
                } else {
                    return;
                }
            }
        }

        int next = map[row][col+1];

        if (dir == DOWN) {
            for (int i = 1; i < L; i++) {
                if (col + i >= N) {
                    return;
                }

                int tmp = map[row][col + i];
                if (tmp != map[row][col]) {
                    return;
                }
            }
            goHorizontal(row, col + L - 1, 0, NOT_DOWN);
        } else {
            if (next == map[row][col]+1) {
                if (cnt < L) {
                    return;
                } else {
                    goHorizontal(row, col+1, 1, NOT_DOWN);
                }
            } else if (next == map[row][col]) {
                goHorizontal(row, col+1, cnt+1, NOT_DOWN);
            } else if (next == map[row][col] -1) {
                goHorizontal(row, col+1, 1, DOWN);
            } else {
                return;
            }
        }


    }

    private static void goVertical(int row, int col, int cnt, int dir) {
//        System.out.printf("goVertical : row = %d, col = %d, cnt = %d, dir = %d\n", row, col, cnt, dir);
        if (row == N-1) {
            if (dir != DOWN) {
//                System.out.println("Vertical : col = " + col);
                result += 1;
                return;
            } else {
                if (L == 1) {
//                    System.out.println("Vertical : col = " + col);
                    result += 1;
                    return;
                } else {
                    return;
                }
            }
        }

        int next = map[row+1][col];

        if (dir == DOWN) {
            for (int i = 1; i < L; i++) {
                if (row + i >= N) {
                    return;
                }
                int tmp = map[row + i][col];
                if (tmp != map[row][col]) {
                    return;
                }
            }
            goVertical(row + L - 1, col, 0, NOT_DOWN);
        } else {
            if (next == map[row][col]+1) {
                if (cnt < L) {
                    return;
                } else {
                    goVertical(row+1, col, 1, NOT_DOWN);
                }
            } else if (next == map[row][col]) {
                goVertical(row+1, col, cnt+1, NOT_DOWN);
            } else if (next == map[row][col] -1) {
                goVertical(row+1, col, 1, DOWN);
            } else {
                return;
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
