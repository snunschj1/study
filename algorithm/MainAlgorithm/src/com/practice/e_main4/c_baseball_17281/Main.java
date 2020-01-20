package com.practice.e_main4.c_baseball_17281;

import java.util.*;
import java.io.*;

public class Main {

    private static final int ONE_RUN = 1;
    private static final int TWO_RUN = 2;
    private static final int THREE_RUN = 3;
    private static final int HOME_RUN = 4;
    private static final int OUT = 0;

    private static int N;

    private static int[][] innings;

    private static int answer = 0;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());

        innings = new int[N][9];

        for (int inning = 0; inning < N; inning++) {
            st = new StringTokenizer(br.readLine());
            for (int player = 0; player < 9; player++) {
                innings[inning][player] = s2i(st.nextToken());
            }
        }
    }

    private static void solve() {

        int[] orders = new int[9];
        for (int i = 0; i < 9; i++) {
            orders[i] = i;
        }

        int[] run = new int[3];

        do {

            /** 4번 타자 포함한 타자 순서 */
            int tmp = orders[3];
            orders[3] = 0;
            orders[0] = tmp;

            int score = 0;

            int order = 0;

            for (int inning = 0; inning < N; inning++) {

                int outs = 0;

                while (true) {

                    if (order == 9) {
                        order = 0;
                    }

                    int curPlayer = orders[order++];

                    int result = innings[inning][curPlayer];

                    if (result == OUT) {
                        outs += 1;

                        if (outs == 3) {
                            for (int i = 0; i < 3; i++) {
                                run[i] = 0;
                            }
                            break;
                        }

                    } else {
                        score += run(result, run);
                    }
                }
            }

            if (answer < score) {
                answer = score;
            }

            tmp = orders[0];
            orders[0] = orders[3];
            orders[3] = tmp;

        } while (next_permutation(orders));

        System.out.println(answer);
    }

    private static int run(int result, int[] run) {

        int score = 0;

        switch (result) {
            case ONE_RUN:
                score = moveRunner(ONE_RUN, run);
                run[ONE_RUN - 1] = 1;
                break;

            case TWO_RUN:
                score = moveRunner(TWO_RUN, run);
                run[TWO_RUN - 1] = 1;
                break;

            case THREE_RUN:
                score = moveRunner(THREE_RUN, run);
                run[THREE_RUN - 1] = 1;
                break;

            case HOME_RUN:
                score = moveRunner(HOME_RUN, run) + 1;
                break;

            default:
                break;
        }

        return score;
    }

    private static int moveRunner(int result, int[] run) {

        int score = 0;

        for (int i = 2; i >= 0; i--) {
            if (i + result >= 3) {
                score += run[i];
                run[i] = 0;
            } else {
                run[i + result] = run[i];
                run[i] = 0;
            }

        }

        return score;
    }

    private static boolean next_permutation(int[] a) {
        int i = a.length - 1;

        while (i > 1 && a[i] <= a[i-1]) {
            i -= 1;
        }

        if (i <= 1) {
            return false;
        }

        int j = a.length - 1;

        while (a[j] <= a[i-1]) {
            j -= 1;
        }

        int tmp = a[i-1];
        a[i-1] = a[j];
        a[j] = tmp;

        j = a.length - 1;

        while (i < j) {
            tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;

            i += 1;
            j -= 1;
        }

        return true;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
