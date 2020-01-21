package com.practice.e_main4.f_fishking_17143;

import java.util.*;
import java.io.*;

public class Main {

    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, 1, -1};

    private static int R, C, M;

    private static Shark[][] map;

    private static int answer = 0;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = s2i(st.nextToken());
        C = s2i(st.nextToken());
        M = s2i(st.nextToken());

        map = new Shark[R][C];

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());

            int r = s2i(st.nextToken()) - 1;
            int c = s2i(st.nextToken()) - 1;
            int s = s2i(st.nextToken());
            int d = s2i(st.nextToken()) - 1;
            int z = s2i(st.nextToken());

            Shark shark = new Shark(s, d, z);

            map[r][c] = shark;
        }
    }

    private static void solve() {
        for (int i = 0; i < C; i++) {
            catchShark(i);
            System.out.println("====" + (i+1) + "초");
            moveShark();
        }

        System.out.println(answer);
    }

    private static void catchShark(int col) {
        for (int r = 0; r < R; r++) {
            if (map[r][col] != null) {
                answer += map[r][col].z;
                map[r][col] = null;
            }
        }
    }

    private static void moveShark() {
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (map[r][c] != null) {
                    int s = map[r][c].s;
                    int d = map[r][c].d;
                    int z = map[r][c].z;
                    map[r][c] = null;
                    System.out.println("start");
                    go(r, c, s, d, z, s);
                    System.out.println();
                }
            }
        }
    }

    private static void go(int r, int c, int s, int d, int z, int cnt) {
        System.out.printf("r = %d , c = %d, s = %d, d = %d, z = %d\n", r, c, s, d, z);

        if (cnt == 0) {
            Shark other = map[r][c];
            if (other != null) {
                if (z < other.z) {
                    return;
                }
            }

            map[r][c] = new Shark(s, d, z);
            return;
        }

        int nr = r + dr[d];
        int nc = c + dc[d];
        int nd = d;

        if (0 > nr || R <= nr || 0 > nc || C <= nc) {
            if (d % 2 == 0) nd += 1;
            else /* if (d % 2 == 1) */ nd -= 1;

            nr = r + dr[nd];
            nc = c + dc[nd];
        }

        go(nr, nc, s, nd, z, cnt - 1);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Shark {
    int s, d, z;

    Shark(int s, int d, int z) {
        this.s = s;
        this.d = d;
        this.z = z;
    }
}