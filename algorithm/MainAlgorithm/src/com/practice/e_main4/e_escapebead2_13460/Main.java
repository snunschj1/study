package com.practice.e_main4.e_escapebead2_13460;

import java.io.*;
import java.util.*;

public class Main {

    private static final int[] dr = {-1, 0, 1, 0};
    private static final int[] dc = {0, 1, 0, -1};

    private static final int EMPTY = 3;
    private static final int WALL = -1;
    private static final int RED = 0;
    private static final int BLUE = 1;
    private static final int HOLE = 2;

    private static int N, M;
    private static int[][] map;

    private static Bead[] def = new Bead[3];

    private static int answer = 11;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());

        map = new int[N][M];

        String[] s = null;
        for (int r = 0; r < N; r++) {
            s = br.readLine().split("");
            for (int c = 0; c < M; c++) {
                int tmp;

                if (s[c].equals(".")) {
                    tmp = EMPTY;
                } else if (s[c].equals("#")) {
                    tmp = WALL;
                } else if (s[c].equals("R")) {
                    tmp = RED;
                    def[RED] = new Bead(r, c);
                } else if (s[c].equals("B")) {
                    tmp = BLUE;
                    def[BLUE] = new Bead(r, c);
                } else /* if (s[c].equals("O") */ {
                    tmp = HOLE;
                    def[HOLE] = new Bead(r, c);
                }

                map[r][c] = tmp;
            }
        }
    }

    private static void solve() {
        Bead red = def[RED];
        Bead blue = def[BLUE];

        go(-1, red, blue, 0);

        if (answer == 11) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }
    }

    private static void go(int dir, Bead red, Bead blue, int cnt) {
        if (cnt >= 11) {
            return;
        }

        if (blue.r == def[HOLE].r && blue.c == def[HOLE].c) {
            return;
        }

        if (red.r == def[HOLE].r && red.c == def[HOLE].c) {

            if (answer > cnt) {
                answer = cnt;
            }

            return;
        }


        for (int k = 0; k < 4; k++) {
            if (dir != -1 && (dir + 2) % 4 == k) {
                continue;
            }

            int diffRow = red.r - blue.r;
            int diffCol = red.c - blue.c;

            Bead nr;
            Bead nb;

            if (k % 2 == 0) {
                /* 짝수 */
                if (k + diffRow == 1) {
                    nb = goBead(k, blue, BLUE);
                    nr = goBead(k, red, RED);
                } else {
                    nr = goBead(k, red, RED);
                    nb = goBead(k, blue, BLUE);
                }
            } else {
                /* 홀수 */
                if (k + diffCol == 2) {
                    nr = goBead(k, red, RED);
                    nb = goBead(k, blue, BLUE);
                } else {
                    nb = goBead(k, blue, BLUE);
                    nr = goBead(k, red, RED);
                }
            }

            if (nr.r == red.r && nr.c == red.c) {
                backToPrev(blue, nb, BLUE);
                continue;
            }

            go(k, nr, nb, cnt + 1);

            backToPrev(red, nr, RED);
            backToPrev(blue, nb, BLUE);

        }
    }

    private static void backToPrev(Bead prev, Bead next, int type) {
        if (map[next.r][next.c] == type) {
            map[next.r][next.c] = EMPTY;
            map[prev.r][prev.c] = type;
        } else if (map[next.r][next.c] == HOLE) {
            map[prev.r][prev.c] = RED;
        }
    }

    private static Bead goBead(int k, Bead bead, int type) {
        int nr = bead.r;
        int nc = bead.c;

        while (true) {
            nr += dr[k];
            nc += dc[k];

            if (map[nr][nc] == WALL || map[nr][nc] == RED || map[nr][nc] == BLUE) {

                if (!(bead.r == nr - dr[k] && bead.c == nc - dc[k])) {
                    map[nr - dr[k]][nc - dc[k]] = type;
                    map[bead.r][bead.c] = EMPTY;
                }

                return new Bead(nr - dr[k], nc - dc[k]);
            } else if (map[nr][nc] == HOLE) {

                map[bead.r][bead.c] = EMPTY;
                return new Bead(nr, nc);
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Bead {
    int r, c;

    Bead(int r, int c) {
        this.r = r;
        this.c = c;
    }
}