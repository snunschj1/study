package com.practice.g_main6.d_protectfilm_2112;

import java.util.*;
import java.io.*;

public class Main {

    private static final int NONE = -1;
    private static final int A = 0;
    private static final int B = 1;

    private static int T, D, W, K;
    private static int[][] film;
    private static int[][] tFilm;
    private static int[][] visit;
    private static int[] combination;
    private static int answer;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = s2i(br.readLine());

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());

            D = s2i(st.nextToken());
            W = s2i(st.nextToken());
            K = s2i(st.nextToken());

            film = new int[D][W];

            visit = new int[D+1][W];
            for (int i = 0; i < D + 1; i++) {
                Arrays.fill(visit[i], -1);
            }

            combination = new int[D];
            Arrays.fill(combination, -1);

            for (int r = 0; r < D; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < W; c++) {
                    film[r][c] = s2i(st.nextToken());
                }
            }
            st = null;

            answer = Integer.MAX_VALUE;
            solve(t);
        }

        br.close();
    }

    private static void solve(int test) {
        if (K == 1 || isOk(film)) {
            System.out.printf("#%d 0\n", test);
        } else {
            makeCombination(0, 0, combination);
            System.out.printf("#%d %d\n", test, answer);
        }
    }

    private static void makeCombination(int index, int cnt, int[] c) {

        if (index >= D) {

            if (answer < cnt) {
                return;
            }

            tFilm = copyMap(film);
            adaptChemical(tFilm, c);
            if (isOk(tFilm)) {
                if (answer > cnt) {
                    answer = cnt;
                }
            }

            return;
        }

        if (answer < cnt) {
            return;
        }

        c[index] = -1;
        makeCombination(index + 1, cnt, c);
        c[index] = 1;
        makeCombination(index + 1, cnt + 1, c);
        c[index] = 0;
        makeCombination(index + 1, cnt + 1, c);


    }

    private static void adaptChemical(int[][] film, int[] c) {
        for (int i = 0; i < D; i++) {
            if (c[i] == NONE) {
                continue;
            } else if (c[i] == A) {
                Arrays.fill(film[i], A);
            } else if (c[i] == B) {
                Arrays.fill(film[i], B);
            }
        }
    }

    private static boolean isOk(int[][] film) {

        int status;
        int cnt;

        for (int c = 0; c < W; c++) {

            status = film[0][c];
            cnt = 1;

            for (int r = 1; r < D; r++) {

                if (film[r][c] == status) {
                    cnt += 1;
                } else {
                    status = film[r][c];
                    cnt = 1;
                }

                if (cnt == K) break;

                if (r == D - 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int[][] copyMap(int[][] a) {
        int[][] tmp = new int[a.length][a[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                tmp[i][j] = a[i][j];
            }
        }

        return tmp;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}