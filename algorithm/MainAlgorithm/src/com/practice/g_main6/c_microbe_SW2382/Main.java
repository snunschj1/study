package com.practice.g_main6.c_microbe_SW2382;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    private static int T;
    private static int N, M, K;

    private static int[][] map;
    private static int[][] tMap;
    private static Microbe[] microbes;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = s2i(br.readLine());

        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());

            N = s2i(st.nextToken());
            M = s2i(st.nextToken());
            K = s2i(st.nextToken());

            map = new int[N][N];
            microbes = new Microbe[K + 1];

            for (int k = 1; k <= K; k++) {
                st = new StringTokenizer(br.readLine());

                int row = s2i(st.nextToken());
                int col = s2i(st.nextToken());
                int num = s2i(st.nextToken());
                int dir = s2i(st.nextToken()) - 1;

                microbes[k] = new Microbe(row, col, num, dir);
                map[row][col] = k;
            }

            st = null;

            solve(t);
        }

        br.close();
    }

    private static void solve(int testCase) {
        for (int m = 0; m < M; m++) {
            tMap = new int[N][N];

            for (int k = 1; k <= K; k++) {

                if (microbes[k] == null) {
                    continue;
                }

                Microbe micro = microbes[k];

                int r = micro.row;
                int c = micro.col;
                int d = micro.dir;
                int n = micro.num;

                int nr = r + dr[d];
                int nc = c + dc[d];
                int nd = d;
                int nn = n;

                // Todo : 빨간 셀에 도착했는지
                if (isEdge(nr, nc)) {
                    nn /= 2;

                    if (nn == 0) {
                        // Todo : 군집 제거
                        microbes[k] = null;
                        continue;

                    } else {
                        if (nd % 2 == 0) {
                            nd += 1;
                        } else {
                            nd -= 1;
                        }
                    }
                }

                // Todo : 다른 군집이 있는지
                if (tMap[nr][nc] == 0) {
                    changeStatus(microbes, k, nr, nc, nn, nd);
                    tMap[nr][nc] = k;
                } else {
                    Microbe exist = microbes[tMap[nr][nc]];

                    if (nn > exist.num) {
                        nn += exist.num;
                        microbes[tMap[nr][nc]] = null;
                        changeStatus(microbes, k, nr, nc, nn, nd);
                        tMap[nr][nc] = k;

                    } else {
                        nn += exist.num;
                        nd = exist.dir;
                        microbes[k] = null;
                        changeStatus(microbes, tMap[nr][nc], nr, nc, nn, nd);
                    }
                }
            }
            map = copyMap(tMap, N);
        }

        System.out.printf("#%d %d\n", testCase, countRemainMicrobes());
    }

    private static void changeStatus(Microbe[] microbes, int k, int r, int c, int n, int d) {
        microbes[k].row = r;
        microbes[k].col = c;
        microbes[k].num = n;
        microbes[k].dir = d;
    }

    private static int countRemainMicrobes() {
        int sum = 0;

        for (int k = 1; k <= K; k++) {
            if (microbes[k] != null) {
                sum += microbes[k].num;
            }
        }

        return sum;
    }

    private static int[][] copyMap(int[][] m, int size) {
        int[][] tmp = new int[size][size];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                tmp[r][c] = m[r][c];
            }
        }

        return tmp;
    }

    private static boolean isEdge(int row, int col) {
        if (row == 0 || row == N - 1) {
            return true;
        } else if (col == 0 || col == N - 1) {
            return true;
        }
        return false;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Microbe {
    int row, col, num, dir;

    Microbe (int row, int col, int num, int dir) {
        this.row = row;
        this.col = col;
        this.num = num;
        this.dir = dir;
    }
}
