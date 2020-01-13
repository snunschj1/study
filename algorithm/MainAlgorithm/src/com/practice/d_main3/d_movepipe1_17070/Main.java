package com.practice.d_main3.d_movepipe1_17070;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dr = {0, 1, 1};
    private static final int[] dc = {1, 0, 1};
    private static final int[] dirs = {5, 6, 7};

    private static int N;
    private static int[][] map;

    private static int way = 0;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());
        map = new int[N][N];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = s2i(st.nextToken());
            }
        }
    }

    private static void solve() {
        go(0, 1, 5);
        System.out.println(way);
    }

    private static void go(int r, int c, int d) {

        if (r == N-1 && c == N-1) {
            if (map[r][c] != 1) {
                way += 1;
            }
            return;
        }

        int nr;
        int nc;

        if ((d & 1) == 1) {
            nr = r + dr[0];
            nc = c + dc[0];

            if (check(nr, nc, 1)) {
                go(nr, nc, dirs[0]);
            }
        }

        if ((d & 2) == 2) {
            nr = r + dr[1];
            nc = c + dc[1];

            if (check(nr, nc, 2)) {
                go(nr, nc, dirs[1]);
            }


        }

        if ((d & 4) == 4){
            nr = r + dr[2];
            nc = c + dc[2];

            if (check(nr, nc, 4)) {
                go(nr, nc, dirs[2]);
            }
        }
    }

    private static boolean check(int r, int c, int d) {
        if (0 <= r && r < N && 0 <= c && c < N) {

            // Todo : 대각선의 경우 체크해줘야 하는 것이 3개이다.
            if (d == 4) {
                if (map[r][c] == 0 && map[r-1][c] == 0 && map[r][c-1] == 0) {
                    return true;
                }
            } else {
                if (map[r][c] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
