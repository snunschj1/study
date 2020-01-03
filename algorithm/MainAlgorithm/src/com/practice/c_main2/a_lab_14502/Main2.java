package com.practice.c_main2.a_lab_14502;

/**
 * 출처 : https://www.acmicpc.net/source/16584638
 */

import java.io.*;
import java.util.*;

public class Main2 {

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, 1, -1};

    private static final int VIRUS = 2;
    private static final int WALL = 1;
    private static final int EMPTY = 0;

    private static int n;
    private static int m;

    private static int[][] map;
    private static int[][] tmpMap;

    private static int chanceOfBuildingWalls = 3;
    private static int maxCountOfSafetyAreas = 0;

    public static void main(String[] args) throws IOException {
        inputData();
        System.out.println(solve());
    }

    private static void inputData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];
        tmpMap = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static int solve() {
        buildWalls(0);
        return maxCountOfSafetyAreas;
    }

    // Todo : 새로운 벽 3개를 건설 - 재귀를 활용한 dfs
    private static void buildWalls(int startIndex) {
        if (chanceOfBuildingWalls == 0) {
            // 벽 3개 건설 완료
            copyMap();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    // Todo : 바이러스 퍼지기 - dfs 처리
                    if (map[i][j] == VIRUS) {
                        spreadVirus(i, j);
                    }
                }
            }

            maxCountOfSafetyAreas = Math.max(maxCountOfSafetyAreas, getSafetyAreas());
            return;
        }

        for (int i = startIndex; i < n * m; i++) {
            int row = i / m;
            int col = i % m;

            if (map[row][col] == EMPTY) {
                map[row][col] = WALL;
                chanceOfBuildingWalls--;
                buildWalls(i + 1);
                chanceOfBuildingWalls++;
                map[row][col] = EMPTY;
            }
        }
    }

    private static void copyMap() {
        for (int i = 0; i < n; i++) {
            if (m >= 0) {
                System.arraycopy(map[i], 0, tmpMap[i], 0, m);
            }
        }
    }

    private static void spreadVirus(int row, int col) {
        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];

            if (0 <= newRow && newRow < n && 0 <= newCol && newCol < m) {
                if (tmpMap[newRow][newCol] == EMPTY) {
                    tmpMap[newRow][newCol] = VIRUS;
                    spreadVirus(newRow, newCol);
                }
            }
        }
    }

    private static int getSafetyAreas() {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (tmpMap[i][j] == EMPTY) {
                    ++cnt;
                }
            }
        }
        return cnt;
    }
}
