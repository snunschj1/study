package com.practice.d_main3.d_movepipe1_17070;

/**
    출처 : https://www.acmicpc.net/source/16579459
 */

import java.util.*;
import java.io.*;

public class Main2 {

    private static final int WALL = 1;
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;
    private static final int DIAGONAL = 2;

    private static int N;

    private static int[][] map;
    private static int[][][] DP;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        map = new int[N][N];
        DP = new int[N][N][3];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = s2i(st.nextToken());
            }
        }

        br.close();


    }

    private static void solve() {
        // Todo : 초기 파이프 ((0,1), 가로)
        DP[0][1][HORIZONTAL] = 1;

        // Todo : 0행은 각 열에 대해서 벽이 아니면 모두 가로 파이프, 벽을 만나면 그 뒤로는 더 이상 건설 불가
        for (int c = 2; c < N; c++) {
            if (map[0][c] != WALL) DP[0][c][HORIZONTAL] = 1;
            else break;
        }

        // Todo : 각 좌표에 대해서 그 전 파이프의 가능한 모양들을 고려해서
        // Todo : 해당 좌표, 해당 좌표에 건설할 파이프 종류마다 지금까지 여기까지 오는데 몇 가지 방법이 가능한지 기록
        for (int r = 1; r < N; r++) {
            for (int c = 1; c < N; c++) {

                if (map[r][c] != WALL) {
                    DP[r][c][HORIZONTAL] += (DP[r][c-1][HORIZONTAL] + DP[r][c-1][DIAGONAL]);
                    DP[r][c][VERTICAL] += (DP[r-1][c][VERTICAL] + DP[r-1][c][DIAGONAL]);

                    if (map[r-1][c] != WALL && map[r][c-1] != WALL) {
                        DP[r][c][DIAGONAL] += (DP[r-1][c-1][HORIZONTAL]
                                                + DP[r-1][c-1][VERTICAL]
                                                + DP[r-1][c-1][DIAGONAL]);
                    }
                }
            }
        }

        System.out.println(DP[N-1][N-1][HORIZONTAL] + DP[N-1][N-1][VERTICAL] + DP[N-1][N-1][DIAGONAL]);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
