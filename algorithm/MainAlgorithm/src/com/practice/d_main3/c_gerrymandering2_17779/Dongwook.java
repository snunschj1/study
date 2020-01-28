package com.practice.d_main3.c_gerrymandering2_17779;

import java.util.*;
import java.io.*;

public class Dongwook {

    private static final int WALL = -1;
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    private static int N;
    private static int[][] map;

    private static int totalPop;
    private static int[] pops = new int[5];

    private static int[] nextDirs = {RIGHT, DOWN, LEFT, UP};
    private static int[] dr = {-1, 0, 1, 0};
    private static int[] dc = {0, 1, 0, -1};

    // 선거구 2 -> 선거구 4 -> 선거구 3 -> 선거구 1 순으로 본다.
    private static int[] borderDr = {1, 1, -1, -1};
    private static int[] borderDc = {1, -1, -1, 1};

    private static int[] borderLength = new int[4];

    private static int answer = -1;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());

        map = new int[N+2][N+2];

        Arrays.fill(map[0], WALL);
        Arrays.fill(map[N+1], WALL);

        for (int r = 1; r <= N; r++) {
            map[r][0] = map[r][N+1] = WALL;
            st = new StringTokenizer(br.readLine());
            for (int c = 1; c <= N; c++) {
                map[r][c] = s2i(st.nextToken());
                totalPop += map[r][c];
            }
        }

        br.close();
    }

    private static void solve() {
        for (int r = 1; r <= N; r++) {
            for (int c = 1; c <= N; c++) {
                bf(r, c, UP);
            }
        }

        System.out.println(answer);
    }

    private static void bf(int r, int c, int dir) {

        if (dir == 4) {
            // Todo :  선거구 5의 인구수를 구한다.
            pops[4] = totalPop;

            for (int d = 0; d < 4; d++) {
                pops[4] -= pops[d];
            }

            int max = pops[4], min = pops[4];

            for (int d = 0; d < 4; d++) {
                if (max < pops[d]) max = pops[d];
                if (min > pops[d]) min = pops[d];
            }

            int diff = max - min;

            if (answer == -1 || answer > diff) answer = diff;
            return;
        }

        int length = 0;
        int partialPop = 0;

        int nr = r, nc = c;

        while (true) {
            // Todo : 길이가 1 씩 늘어난다.
            nr += borderDr[dir];
            nc += borderDc[dir];

            if (map[nr][nc] == WALL) break;

            borderLength[dir] = ++length;

            // Todo : 경계선 한 칸 그릴 때마다 특정 방향에 있는 모든 값 더하기 (ex. 선거구 2의 경우 UP 방향)
            int tmpR = nr, tmpC = nc;

            while (true) {
                tmpR += dr[dir];
                tmpC += dc[dir];

                if (map[tmpR][tmpC] == WALL) break;

                partialPop += map[tmpR][tmpC];
            }

            switch (dir) {
                case UP: case RIGHT:
                    sumRemainPartialPop(nr, nc, dir, partialPop);
                    break;

                case DOWN: case LEFT:
                    if (borderLength[dir] == borderLength[dir - 2]) {
                        sumRemainPartialPop(nr, nc, dir, partialPop);
                    }
                    break;
            }
        }
    }

    private static void sumRemainPartialPop(int r, int c, int dir, int partialPop) {
        pops[dir] = partialPop;

        int nr = r, nc = c;

        while (true) {

            // Todo : 주어진 좌표에서 dir의 다음 방향으로 한 칸 씩 가면서
            nr += dr[nextDirs[dir]];
            nc += dc[nextDirs[dir]];

            if (map[nr][nc] == WALL) break;

            int tmpR = nr;
            int tmpC = nc;

            while (true) {
                // Todo : 한 칸 씩 간 좌표 포함해서 dir 방향으로 갔을 때의 모든 값을 더한다.
                pops[dir] += map[tmpR][tmpC];

                tmpR += dr[dir];
                tmpC += dc[dir];

                if (map[tmpR][tmpC] == WALL) break;

            }
        }

        // Todo : sumRemainPartialPop 의 인자로 들어온 r, c 가 경계선 끝이다.
        bf(r, c, dir + 1);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
