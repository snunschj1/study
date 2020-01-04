package com.practice.c_main2.b_surveillance_15683;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dr = {0, 1, 0, -1};
    private static final int[] dc = {1, 0, -1, 0};

    private static final int[][][] cctvDirs = {
            {{0}, {1}, {2}, {3}},
            {{0, 2}, {1, 3}},
            {{0, 1}, {1, 2}, {2, 3}, {3, 0}},
            {{0, 1, 2}, {1, 2, 3}, {2, 3, 0}, {3, 0, 1}},
            {{0, 1, 2, 3}}
    };

    private static final int EMPTY = 0;
    private static final int WALL = 6;
    private static final int CCTV1 = 1;
    private static final int CCTV2 = 2;
    private static final int CCTV3 = 3;
    private static final int CCTV4 = 4;
    private static final int CCTV5 = 5;
    private static final int SURV = 7;

    private static int n;
    private static int m;

    private static int[][] map;

    private static int numberOfEMPTY;
    private static int numberOfCCTV;
    private static Pair[] cctvs;

    private static int result = 64;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static void solve() {
        findNumberOfEmptyAndCCTV();
        recordCCTVInfos();
        findMinRemainedEMPTY(0, 0);
        System.out.println(result);
    }

    private static void findNumberOfEmptyAndCCTV() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int cur = map[i][j];
                if (cur == EMPTY) {
                    ++numberOfEMPTY;
                } else if (CCTV1 <= cur && cur <= CCTV5) {
                    ++numberOfCCTV;
                }
            }
        }
    }

    private static void recordCCTVInfos() {
        int index = 0;
        cctvs = new Pair[numberOfCCTV];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int cur = map[i][j];
                if (CCTV1 <= cur && cur <= CCTV5) {
                    cctvs[index++] = new Pair(cur, i, j);
                }
            }
        }
    }

    private static void findMinRemainedEMPTY(int index, int cntSurv) {
        if (index == numberOfCCTV) {
            int tmp = numberOfEMPTY - cntSurv;
            if (result > tmp) {
                result = tmp;
            }
            return;
        }

        else if (index < numberOfCCTV) {
            Pair p = cctvs[index];
            int cctv = p.cctv;
            int row = p.row;
            int col = p.col;

            int[][] dirsOfCertainCCTV = cctvDirs[cctv - 1];

            for (int i = 0; i < dirsOfCertainCCTV.length; i++) {
                int[] dirs = dirsOfCertainCCTV[i];

                ArrayList<Pair> pairs = new ArrayList<>();
                for (int j = 0; j < dirs.length; j++) {
                    moveForSurv(row, col, dirs[j], pairs);
                }

                for (int j = 0; j < pairs.size(); j++) {
                    int tmpRow = pairs.get(j).row;
                    int tmpCol = pairs.get(j).col;

                    map[tmpRow][tmpCol] = SURV;
                }

                findMinRemainedEMPTY(index+1, cntSurv + pairs.size());


                for (int j = 0; j < pairs.size(); j++) {
                    int tmpRow = pairs.get(j).row;
                    int tmpCol = pairs.get(j).col;

                    map[tmpRow][tmpCol] = EMPTY;
                }
            }
        }
    }

    private static void moveForSurv(int row, int col, int dir, ArrayList<Pair> pairs) {

        int nr = row + dr[dir];
        int nc = col + dc[dir];

        if (0 <= nr && nr < n && 0 <= nc && nc < m) {
            if (map[nr][nc] == WALL) {
                return;
            } else if (CCTV1 <= map[nr][nc] && map[nr][nc] <= CCTV5) {
                moveForSurv(nr, nc, dir, pairs);
            } else if (map[nr][nc] == SURV) {
                moveForSurv(nr, nc, dir, pairs);
            } else if (map[nr][nc] == EMPTY) {
                pairs.add(new Pair(nr, nc));
                moveForSurv(nr, nc, dir, pairs);
            }
        }
    }
}

class Pair {
    int cctv;
    int row;
    int col;

    Pair(int row, int col) {
        this.row = row;
        this.col = col;
    }

    Pair(int cctv, int row, int col) {
        this.cctv = cctv;
        this.row = row;
        this.col = col;
    }
}