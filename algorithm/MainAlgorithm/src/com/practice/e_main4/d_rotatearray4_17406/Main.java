package com.practice.e_main4.d_rotatearray4_17406;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dr = {0, 1, 0, -1};
    private static final int[] dc = {1, 0, -1, 0};

    private static int N, M, K;

    private static int startRow, startCol, finRow, finCol;

    private static int[][] a;

    private static ArrayDeque<Pair> operates = new ArrayDeque<>();


    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());
        K = s2i(st.nextToken());

        a = new int[N][M];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());

            for (int c = 0; c < M; c++) {
                a[r][c] = s2i(st.nextToken());
            }
        }

        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());

            int r = s2i(st.nextToken()) - 1;
            int c = s2i(st.nextToken()) - 1;
            int s = s2i(st.nextToken());

            operates.add(new Pair(r, c, s));
        }
    }

    private static void solve() {

        while (!operates.isEmpty()) {
            Pair p = operates.pop();

            findRowAndCol(p);

            while (startRow != finRow) {
                dfs(startRow, startCol, 0, 0);

                startRow += 1;
                startCol += 1;
                finRow -= 1;
                finCol -= 1;
            }
        }

        System.out.println(calculateMin());
    }

    private static void findRowAndCol(Pair p) {
        startRow = p.r - p.s;
        startCol = p.c - p.s;
        finRow = p.r + p.s;
        finCol = p.c + p.s;
    }

    private static void dfs(int row, int col, int dir, int preValue) {
        if (row == startRow && col == startCol && dir == 3) {
            a[row][col] = preValue;
            return;
        }

        /** 다음 dfs 인자 */
        int nr = row + dr[dir];
        int nc = col + dc[dir];
        int curValue = a[row][col];

        if (row == startRow && col == startCol && preValue == 0) {
            dfs(nr, nc, dir, curValue);
        } else {

            /** 이전 값을 배열의 현재 원소에 넣어준다. */
            a[row][col] = preValue;

            if (nr < startRow || nr > finRow || nc < startCol || nc > finCol) {
                /** 범위를 넘어가는 경우 방향을 바꿔준다. */
                nr = row + dr[dir + 1];
                nc = col + dc[dir + 1];

                dfs(nr, nc, dir + 1, curValue);
            } else {
                /** 다음 dfs  */
                dfs(nr, nc, dir, curValue);
            }
        }
    }

    private static int calculateMin() {
        int min = Integer.MAX_VALUE;

        for (int r = 0; r < N; r++) {
            int sum = 0;
            for (int c = 0; c < M; c++) {
                sum += a[r][c];
            }

            if (min > sum) min = sum;
        }

        return min;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair {
    int r, c, s;

    Pair(int r, int c, int s) {
        this.r = r;
        this.c = c;
        this.s = s;
    }
}