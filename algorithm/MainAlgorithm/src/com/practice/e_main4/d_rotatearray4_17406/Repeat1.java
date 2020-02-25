package com.practice.e_main4.d_rotatearray4_17406;

import java.io.*;
import java.util.*;

public class Repeat1 {
    private static final int[] pow = {1, 2, 4, 8, 16, 32};

    private static int N, M, K;

    private static int[][] map;
    private static Plot[] op;

    private static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());
        K = s2i(st.nextToken());

        map = new int[N][M];
        op = new Plot[K];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++) {
                map[r][c] = s2i(st.nextToken());
            }
        }

        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());

            int r = s2i(st.nextToken()) - 1;
            int c = s2i(st.nextToken()) - 1;
            int s = s2i(st.nextToken());

            op[k] = new Plot(r-s, c-s, r+s, c+s);
        }
        br.close();

        solve();
    }

    private static void solve() {
        go(0, 0);
        System.out.println(ans);
    }

    private static void go(int cnt, int bit) {

        if (cnt >= K) {
            int minSum = calculateMin();
            if (ans > minSum) ans = minSum;
            return;
        }

        for (int i = 0; i < K; i++) {

            if ((bit & pow[i]) == 0) {      // 비트마스크로 해당 회전연산을 수행했었는지 본다.
                rotate(i);
                go(cnt + 1, bit + pow[i]);
                setBack(i);
            }
        }
    }

    private static void rotate(int idx) {
        Plot p = op[idx];

        int sr = p.sr;  // 시작 row
        int sc = p.sc;  // 시작 col
        int er = p.er;  // 끝 row
        int ec = p.ec;  // 끝 col

        while (sr < er && sc < ec) {

            int tmp = map[sr][sc];

            for (int r = sr+1; r <= er; r++)
                map[r-1][sc] = map[r][sc];
            for (int c = sc+1; c <= ec; c++)
                map[er][c-1] = map[er][c];
            for (int r = er-1; r >= sr; r--)
                map[r+1][ec] = map[r][ec];
            for (int c = ec-1; c >= sc+1; c--)
                map[sr][c+1] = map[sr][c];

            map[sr][sc+1] = tmp;

            sr += 1;
            sc += 1;
            er -= 1;
            ec -= 1;
        }
    }

    private static void setBack(int idx) {
        Plot p = op[idx];

        int sr = p.sr;  // 시작 row
        int sc = p.sc;  // 시작 col
        int er = p.er;  // 끝 row
        int ec = p.ec;  // 끝 col

        while (sr < er && sc < ec) {
            int tmp = map[sr][sc];

            for (int c = sc+1; c <= ec; c++)
                map[sr][c-1] = map[sr][c];
            for (int r = sr+1; r <= er; r++)
                map[r-1][ec] = map[r][ec];
            for (int c = ec-1; c >= sc; c--)
                map[er][c+1] = map[er][c];
            for (int r = er-1; r >= sr+1; r--)
                map[r+1][sc] = map[r][sc];

            map[sr+1][sc] = tmp;

            sr += 1;
            sc += 1;
            er -= 1;
            ec -= 1;
        }
    }

    private static int calculateMin() {
        int min = Integer.MAX_VALUE;

        for (int r = 0; r < N; r++) {
            int sum = 0;
            for (int c = 0; c < M; c++) {
                sum += map[r][c];
            }

            if (min > sum) min = sum;
        }

        return min;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Plot {
    int sr, sc, er, ec;

    Plot(int sr, int sc, int er, int ec) {
        this.sr = sr;
        this.sc = sc;
        this.er = er;
        this.ec = ec;
    }
}