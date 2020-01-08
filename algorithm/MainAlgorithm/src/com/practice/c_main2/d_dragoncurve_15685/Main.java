package com.practice.c_main2.d_dragoncurve_15685;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dirs = {1, 2, 4, 8};
    private static final int[] dr = {0, -1, 0, 1};
    private static final int[] dc = {1, 0, -1, 0};

    private static boolean[][] map = new boolean[101][101];

    private static int N;

    private static Pair[] start;
    private static ArrayList<Integer>[] curves;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());

        start = new Pair[N];
        curves = new ArrayList[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int c = s2i(st.nextToken());
            int r = s2i(st.nextToken());
            int d = s2i(st.nextToken());
            int g = s2i(st.nextToken());

            start[i] = new Pair(r, c, dirs[d], g);

            curves[i] = new ArrayList<>();
            curves[i].add(dirs[d]);
        }
    }

    private static void solve() {
        for (int i = 0; i < N; i++) {
            Pair p = start[i];
            decideCurves(i);


            map[p.r][p.c] = true;
            drawCurves(p.r, p.c, 0, curves[i]);
        }

        int sum = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (map[i][j]) {
                    if (map[i][j+1] && map[i+1][j] && map[i+1][j+1]) {
                        sum += 1;
                    }
                }
            }
        }
        System.out.println(sum);
    }

    private static void decideCurves(int index) {
        int generation = start[index].g;

        for (int g = 0; g < generation; g++) {
            int size = curves[index].size();
            while(size-- >= 1) {
                int nc = turnDirection(curves[index].get(size));
                curves[index].add(nc);
            }
        }
    }

    private static void drawCurves(int row, int col, int index, ArrayList<Integer> curve) {
        if (index >= curve.size()) {
            return;
        }

        int dir = curve.get(index);

        Pair p = nextPoint(row, col, dir);
        map[p.r][p.c] = true;
        drawCurves(p.r, p.c, index+1, curve);
    }

    private static int turnDirection(int n) {
        int nd = n << 1;
        if (nd > 8) {
            nd = 1;
        }
        return nd;
    }

    private static Pair nextPoint(int row, int col, int dir) {
        int nr = row;
        int nc = col;

        if ((dir & 1) == 1) {
            nr += dr[0];
            nc += dc[0];
        } else if ((dir & 2) == 2) {
            nr += dr[1];
            nc += dc[1];
        } else if ((dir & 4) == 4) {
            nr += dr[2];
            nc += dc[2];
        } else if ((dir & 8) == 8) {
            nr += dr[3];
            nc += dc[3];
        }
        return new Pair(nr, nc);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair {
    int r, c, d, g;

    Pair(int r, int c) {
        this.r = r;
        this.c = c;
    }

    Pair(int r, int c, int d, int g) {
        this(r, c);
        this.d = d;
        this.g = g;
    }
}