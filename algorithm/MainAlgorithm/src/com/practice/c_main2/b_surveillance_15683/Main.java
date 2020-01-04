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

            /**
             * 해당 cctv의 가능한 감시 방법들이 dirsArrOfCertainCCTV 에 들어있다.
             *
             * ex. CCTV2의 경우
             * {{0, 1}, {1, 2}, {2, 3}, {3, 0}}
             */
            int[][] dirsArrOfCertainCCTV = cctvDirs[cctv - 1];

            // Todo : 해당 cctv의 가능한 감시 방법들을 하나씩 해본다.
            // ex. CCTV2의 경우
            // {0, 1} -> {1, 2} -> {2, 3} -> {3, 0}
            for (int i = 0; i < dirsArrOfCertainCCTV.length; i++) {
                int[] dirs = dirsArrOfCertainCCTV[i];

                // 해당 CCTV에 의해 EMPTY -> SURV 로 바뀔 좌표들을 pairs 리스트에 저장한다.
                ArrayList<Pair> survedBlocks = new ArrayList<>();
                for (int j = 0; j < dirs.length; j++) {
                    moveForSurv(row, col, dirs[j], survedBlocks);
                }

                // EMPTY > SURV로 장소 정보를 바꾼다.
                for (int j = 0; j < survedBlocks.size(); j++) {
                    int tmpRow = survedBlocks.get(j).row;
                    int tmpCol = survedBlocks.get(j).col;

                    map[tmpRow][tmpCol] = SURV;
                }

                // Todo : 이번 cctv에서 EMPTY > SURV로 바꾼 좌표 개수 정보를 담고, 다음 cctv로 넘어간다.
                findMinRemainedEMPTY(index+1, cntSurv + survedBlocks.size());

                // 장소 정보를 다시 SURV -> EMPTY로 바꾼다.
                for (int j = 0; j < survedBlocks.size(); j++) {
                    int tmpRow = survedBlocks.get(j).row;
                    int tmpCol = survedBlocks.get(j).col;

                    map[tmpRow][tmpCol] = EMPTY;
                }
            }
        }
    }

    private static void moveForSurv(int row, int col, int dir, ArrayList<Pair> survedBlocks) {
        // Todo : 해당 CCTV가 한 번에 EMPTY > SURV 로 바꾸는 좌표가 무엇인지 찾는 로직
        int nr = row + dr[dir];
        int nc = col + dc[dir];

        if (0 <= nr && nr < n && 0 <= nc && nc < m) {
            if (map[nr][nc] == WALL) {
                return;
            } else if (CCTV1 <= map[nr][nc] && map[nr][nc] <= CCTV5) {
                moveForSurv(nr, nc, dir, survedBlocks);
            } else if (map[nr][nc] == SURV) {
                moveForSurv(nr, nc, dir, survedBlocks);
            } else if (map[nr][nc] == EMPTY) {
                survedBlocks.add(new Pair(nr, nc));
                moveForSurv(nr, nc, dir, survedBlocks);
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