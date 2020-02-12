package com.practice.g_main6.g_breakblock_SW5656;

import java.io.*;
import java.util.*;

public class Main2 {

    private static final int[] dr = {1, 0, -1, 0};
    private static final int[] dc = {0, -1, 0, 1};

    private static int T;
    private static int N, H, W;

    private static int[][] map;

    private static int min;

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
            W = s2i(st.nextToken());
            H = s2i(st.nextToken());

            map = new int[H][W];
            for (int r = 0; r < H; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < W; c++) {
                    map[r][c] = s2i(st.nextToken());
                }
            }

            solve(t);
        }

        br.close();
    }

    private static void solve(int test) {
        min = Integer.MAX_VALUE;
        ping(map, 0);
        System.out.printf("#%d %d\n", test, min);
    }

    private static void ping(int[][] map, int cnt) {

        numOfRemains(map);

        if (cnt == N) {
            return;
        }

        int[][] nMap = new int[H][W];

        for (int c = 0; c < W; c++) {   // Todo : 어떤 열에 구슬을 쏠지 선택

            for (int r = 0; r < H; r++) {   // Todo : 전체 맵을 값 복사
                nMap[r] = Arrays.copyOf(map[r], map[r].length);
            }

            for (int r = 0; r < H; r++) {

                if (nMap[r][c] != 0) {      // Todo : 선택한 열의 가장 최상단 블록에 구슬을 쏜다
                    Boom(nMap, r, c, nMap[r][c] - 1);
                    BlockClear(nMap);
                    ping(nMap, cnt + 1);
                    break;
                }
            }
        }
    }

    private static void Boom(int[][] map, int r, int c, int range) {
        map[r][c] = 0;

        if (map[r][c] == 1) return;

        for (int d = 0; d < 4; d++) {
            BoomLine(map, r, c, range, dr[d], dc[d]);
        }
    }

    private static void BoomLine(int[][] map, int r, int c, int range, int dr, int dc) {
        for (int i = 0; i < range; i++) {
            r += dr;
            c += dc;

            if (0 <= r && r < H && 0 <= c && c < W) {

                if (map[r][c] != 0) {
                    Boom(map, r, c, map[r][c] - 1);
                }
            }
        }
    }

    private static void BlockClear(int[][] nMap) {
        for (int c = 0; c < W; c++) {

            int idx = H - 1;

            for (int r = H - 1; r >= 0; r--) {      // Todo : 남아 있는 블록을 밑으로 내린다.
                if (nMap[r][c] != 0) {
                    nMap[idx--][c] = nMap[r][c];
                }
            }

            for (int r = 0; r <= idx; r++) {        // Todo : 다 내린 후, 나머지 칸은 0으로 채운다.
                nMap[r][c] = 0;
            }
        }
    }

    private static void numOfRemains(int[][] map) {
        int cnt = 0;
        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                if (map[r][c] != 0) cnt++;
            }
        }
        min = Math.min(min, cnt);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
