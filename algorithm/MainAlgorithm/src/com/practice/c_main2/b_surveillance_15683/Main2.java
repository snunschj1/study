package com.practice.c_main2.b_surveillance_15683;

/**
 * 출처 : https://www.acmicpc.net/source/16453586
 */

/**
 * 0001 (1) : 북
 * 0010 (2) : 서
 * 0100 (4) : 남
 * 1000 (8) : 동
 */

import java.util.*;
import java.io.*;

public class Main2 {

    private static final int EMPTY = 0;
    private static final int WALL = 6;
    private static final int CCTV1 = 1;
    private static final int CCTV2 = 2;
    private static final int CCTV3 = 3;
    private static final int CCTV4 = 4;
    private static final int CCTV5 = 5;
    private static final int SURV = -1;

    private static final int SURV_ON_ACTION = -1;
    private static final int SURV_OFF_ACTION = 1;

    private static int n, m;
    private static int[][] map;

    private static CCTV[] cctvs = new CCTV[8];
    private static int[][] cctv5s = new int[8][2];

    private static int numberOfCCTVFrom1To4;
    private static int numberOfCCTV5;
    private static int numberOfEMPTY;

    private static int min = 64;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = s2i(st.nextToken());
        m = s2i(st.nextToken());

        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = s2i(st.nextToken());
            }
        }
    }

    private static void solve() {
        recordMapInfo();
        monitorCCTV5();

        if (numberOfCCTVFrom1To4 > 0) {
            monitorRemainedCCTVs(0);
        } else {
            min = numberOfEMPTY;
        }

        System.out.println(min);
    }

    private static void recordMapInfo() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int info = map[i][j];

                if (info == EMPTY) {
                    ++numberOfEMPTY;
                } else if (CCTV1 <= info && info <= CCTV4) {
                    cctvs[numberOfCCTVFrom1To4++] = new CCTV(i, j, info);
                } else if (info == CCTV5) {
                    cctv5s[numberOfCCTV5][0] = i;
                    cctv5s[numberOfCCTV5++][1] = j;
                }
            }
        }
    }

    private static void monitorCCTV5() {
        for (int i = 0; i < numberOfCCTV5; i++) {
            monitor(cctv5s[i][0], cctv5s[i][1], 15, -1);
        }
    }

    private static void monitorRemainedCCTVs(int index) {
        for (int i = 0; i < cctvs[index].loop; i++) {
            monitor(cctvs[index].row, cctvs[index].col, cctvs[index].dir[i], SURV_ON_ACTION);

            if (index == numberOfCCTVFrom1To4 - 1) {
                if (numberOfEMPTY == 0) {
                    System.out.println(0);
                    System.exit(0);
                }

                if (min > numberOfEMPTY) {
                    min = numberOfEMPTY;
                }
            }

            if (index < numberOfCCTVFrom1To4 - 1) {
                monitorRemainedCCTVs(index + 1);
            }

            monitor(cctvs[index].row, cctvs[index].col, cctvs[index].dir[i], SURV_OFF_ACTION);
        }
    }

    private static void monitor(int row, int col, int dir, int action) {
        if ((dir & 1) == 1 && row > 0) {
            for (int nr = row - 1; nr >= 0; nr--) {
                if (map[nr][col] == WALL) break;
                if (map[nr][col] >= CCTV1) continue;
                map[nr][col] += action;
                if (map[nr][col] == SURV && action == SURV_ON_ACTION) numberOfEMPTY--;
                if (map[nr][col] == EMPTY && action == SURV_OFF_ACTION) numberOfEMPTY++;
            }
        }

        if ((dir & 4) == 4 && row < n - 1) {
            for (int nr = row + 1; nr < n; nr++) {
                if (map[nr][col] == WALL) break;
                if (map[nr][col] >= CCTV1) continue;
                map[nr][col] += action;
                if (map[nr][col] == SURV && action == SURV_ON_ACTION) numberOfEMPTY--;
                if (map[nr][col] == EMPTY && action == SURV_OFF_ACTION) numberOfEMPTY++;
            }
        }

        if ((dir & 2) == 2 && col > 0) {
            for (int nc = col - 1; nc >= 0; nc--) {
                if (map[row][nc] == WALL) break;
                if (map[row][nc] >= CCTV1) continue;
                map[row][nc] += action;
                if (map[row][nc] == SURV && action == SURV_ON_ACTION) numberOfEMPTY--;
                if (map[row][nc] == EMPTY && action == SURV_OFF_ACTION) numberOfEMPTY++;
            }
        }

        if ((dir & 8) == 8 && col < m - 1) {
            for (int nc = col + 1; nc < m; nc++) {
                if (map[row][nc] == WALL) break;
                if (map[row][nc] >= CCTV1) continue;
                map[row][nc] += action;
                if (map[row][nc] == SURV && action == SURV_ON_ACTION) numberOfEMPTY--;
                if (map[row][nc] == EMPTY && action == SURV_OFF_ACTION) numberOfEMPTY++;
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class CCTV {
    int row, col, loop;
    int[] dir;

    CCTV (int row, int col, int type) {
        this.row = row;
        this.col = col;

        switch (type) {
            case 1:
                dir = new int[] {1, 2, 4, 8};
                break;
            case 2:
                dir = new int[] {5, 10};
                break;
            case 3:
                dir = new int[] {3, 6, 12, 9};
                break;
            case 4:
                dir = new int[] {14, 13, 11, 7};
                break;
            default:
                break;
        }
        loop = dir.length;
    }
}