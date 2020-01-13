package com.practice.d_main3.e_castledefense_17135;

import java.util.*;
import java.io.*;

public class Main {

    private static int[] dr = {0, -1, 0};
    private static int[] dc = {-1, 0, 1};

    private static final int EMPTY = 0;
    private static final int ENEMY = 1;

    private static int N, M, D;

    private static int[][] map;
    private static int[][] tmpMap;

    private static int numEnemy;
    private static int tmpNumEnemy;

    private static int max;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());
        D = s2i(st.nextToken());

        map = new int[N + 1][M];
        tmpMap = new int[N + 1][M];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++) {
                map[r][c] = s2i(st.nextToken());
                tmpMap[r][c] = map[r][c];

                if (map[r][c] == ENEMY) {
                    ++numEnemy;
                    ++tmpNumEnemy;
                }
            }
        }
    }

    private static void solve() {
        int[] archerCol = new int[3];
        select(0, 0, archerCol);
        System.out.println(max);
    }

    private static void select(int col, int cnt, int[] archerCol) {
        if (col >= M) {
            return;
        } else {
            if (cnt == 3) {

                for (int i = 0; i < N + 1; i++) {
                    for (int j = 0; j < M; j++) {
                        map[i][j] = tmpMap[i][j];
                    }
                }
                numEnemy = tmpNumEnemy;

                int tmp = 0;

                while (numEnemy > 0) {
                    tmp += attack(archerCol);
                    moveEnemy();
                }

                if (max < tmp) {
                    max = tmp;
                }

                return;
            }
        }

        archerCol[cnt] = col;
        select(col + 1, cnt + 1, archerCol);
        select(col + 1, cnt, archerCol);

    }

    private static int attack(int[] archerCol) {
        // Todo : 3명의 궁수가 1초에 공격하는 적들

        boolean[][] attacks = new boolean[N][M];

        for (int i = 0; i < archerCol.length; i++) {
            bfs(N, archerCol[i], attacks);
        }

        int killedEnemy = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (attacks[i][j]) {
                    map[i][j] = EMPTY;
                    --numEnemy;
                    ++killedEnemy;
                }
            }
        }
        return killedEnemy;
    }

    private static void bfs(int row, int col, boolean[][] attacks) {
        int[][] visited = new int[N + 1][M];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = -1;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(row);
        q.add(col);

        visited[row][col] = 0;

        while (!q.isEmpty()) {
            int r = q.remove();
            int c = q.remove();

            if (map[r][c] == ENEMY) {
                attacks[r][c] = true;
                break;
            }

            for (int i = 0; i < 3; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (0 <= nr && nr < N + 1 && 0 <= nc && nc < M) {
                    if (visited[nr][nc] == -1) {
                        visited[nr][nc] = visited[r][c] + 1;

                        if (visited[nr][nc] <= D) {
                            q.add(nr);
                            q.add(nc);
                        }
                    }
                }
            }
        }
    }

    private static void moveEnemy() {
        for (int c = 0; c < M; c++) {
            if (map[N - 1][c] == ENEMY) {
                map[N - 1][c] = EMPTY;

                if (numEnemy > 0) {
                    --numEnemy;
                }
            }
        }

        for (int r = N - 2; r >= 0; r--) {
            for (int c = 0; c < M; c++) {
                if (map[r][c] == ENEMY) {
                    map[r][c] = EMPTY;
                    map[r + 1][c] = ENEMY;
                }
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }

    private static void printMap() {
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
