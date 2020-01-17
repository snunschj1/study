package com.practice.e_main4.a_twodemension_17140;

import java.io.*;
import java.util.*;

public class Main {

    private static int R, C, K;

    private static int[][] a = new int[100][100];

    private static int MAX_ROW = 3;
    private static int MAX_COL = 3;

    private static int result = 0;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = s2i(st.nextToken()) - 1;
        C = s2i(st.nextToken()) - 1;
        K = s2i(st.nextToken());

        for (int r = 0; r < MAX_ROW; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < MAX_COL; c++) {
                a[r][c] = s2i(st.nextToken());
            }
        }
    }

    private static void solve() {

        while (true) {

            if (a[R][C] == K) {
                break;
            }

            if (result > 100) {
                result = -1;
                break;
            }


            if (MAX_ROW >= MAX_COL) {
                operateR();
            } else /* if (MAX_ROW < MAX_COL) */ {
                operateC();
            }
            result++;
        }

        System.out.println(result);
    }

    private static void operateR() {

        int maxCol = 0;

        int[][][] rpCnt = new int[MAX_ROW][10][10];

        for (int r = 0; r < MAX_ROW; r++) {

            int cnt = 0;

            for (int c = 0; c < MAX_COL; c++) {
                int num = a[r][c];

                int tDigit = num / 10;
                int fDigit = num % 10;

                if (num == 0) {
                    continue;
                } else if (num == 100) {
                    tDigit = 0;
                    fDigit = 0;
                }

                if (rpCnt[r][tDigit][fDigit] == 0) {
                    cnt++;
                }

                rpCnt[r][tDigit][fDigit]++;

            }

            if (maxCol < 2 * cnt) {
                maxCol = 2 * cnt;

                if (maxCol > 100) {
                    maxCol = 100;
                }
            }
        }

        PriorityQueue<Pair> q;

        for (int i = 0; i < MAX_ROW; i++) {

            q = new PriorityQueue<>();

            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    if (rpCnt[i][j][k] != 0) {
                        int num = j * 10 + k;

                        if (j == 0 && k == 0) {
                            num = 100;
                        }

                        int cnt = rpCnt[i][j][k];

                        q.add(new Pair(num, cnt));
                    }

                }
            }

            for (int c = 0; c < maxCol; c += 2) {
                if (q.isEmpty()) {
                    a[i][c] = 0;
                    a[i][c + 1] = 0;
                } else {
                    Pair p = q.remove();

                    a[i][c] = p.num;
                    a[i][c + 1] = p.cnt;
                }
            }
        }

        MAX_COL = maxCol;
    }

    private static void operateC() {

        int maxRow = 0;

        int[][][] rpCnt = new int[MAX_COL][10][10];

        for (int c = 0; c < MAX_COL; c++) {

            int cnt = 0;

            for (int r = 0; r < MAX_ROW; r++) {
                int num = a[r][c];

                int tDigit = num / 10;
                int fDigit = num % 10;

                if (num == 0) {
                    continue;
                } else if (num == 100) {
                    tDigit = 0;
                    fDigit = 0;
                }

                if (rpCnt[c][tDigit][fDigit] == 0) {
                    cnt++;
                }

                rpCnt[c][tDigit][fDigit]++;

            }

            if (maxRow < 2 * cnt) {
                maxRow = 2 * cnt;

                if (maxRow > 100) {
                    maxRow = 100;
                }
            }
        }

        PriorityQueue<Pair> q;

        for (int i = 0; i < MAX_COL; i++) {

            q = new PriorityQueue<>();

            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    if (rpCnt[i][j][k] != 0) {

                        int num = j * 10 + k;

                        if (j == 0 && k == 0) {
                            num = 100;
                        }

                        int cnt = rpCnt[i][j][k];

                        q.add(new Pair(num, cnt));
                    }

                }
            }

            for (int r = 0; r < maxRow; r += 2) {
                if (q.isEmpty()) {
                    a[r][i] = 0;
                    a[r + 1][i] = 0;
                } else {
                    Pair p = q.remove();

                    a[r][i] = p.num;
                    a[r + 1][i] = p.cnt;
                }
            }
        }

        MAX_ROW = maxRow;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair implements Comparable<Pair> {
    int num;
    int cnt;

    Pair(int num, int cnt) {
        this.num = num;
        this.cnt = cnt;
    }

    @Override
    public int compareTo(Pair o) {
        if (this.cnt == o.cnt) {
            if (this.num > o.num) {
                return 1;
            } else if (this.num < o.num) {
                return -1;
            } else {
                return 0;
            }
        } else if (this.cnt > o.cnt) {
            return 1;
        } else {
            return -1;
        }
    }
}