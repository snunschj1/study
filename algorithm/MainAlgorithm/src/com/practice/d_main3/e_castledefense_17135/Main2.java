package com.practice.d_main3.e_castledefense_17135;

import java.io.*;
import java.util.*;

/**
 * 출처 : https://www.acmicpc.net/source/15729406
 */

public class Main2 {

    private static final int ENEMY = 1;

    private static int N;
    private static int M;
    private static int D;

    private static int[][] map;

    private static int endLine = 0;
    private static int answer = 0;

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

        map = new int[N][M];

        boolean endLineFlag = true;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                map[i][j] = s2i(st.nextToken());

                if (endLineFlag) {
                    if (map[i][j] == ENEMY) {
                        endLineFlag = false;
                        endLine = i;
                    }
                }
            }
        }

        br.close();
    }

    private static void solve() {
        for (int i = 0; i < M - 2; i++) {
            for (int j = i + 1; j < M - 1; j++) {
                for (int k = j + 1; k < M; k++) {
                    simulation(i, j, k);
                }
            }
        }
        System.out.println(answer);
    }

    private static void simulation(int a1, int a2, int a3) {
        int sumKill = 0;

        int[] archers = new int[3];
        archers[0] = a1;
        archers[1] = a2;
        archers[2] = a3;

        ArrayDeque<Node> killedEnemies = new ArrayDeque<>();

        // 궁수 조합 Case 마다 사용할 임시 map 배열
        int tmpMap[][] = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                tmpMap[i][j] = map[i][j];
            }
        }

        // N행 부터 적이 존재하는 최상단의 행의 직전까지 반복
        for (int r = N; r > endLine; r--) {

            // 각 궁수마다 죽이는 적이 어떤 좌표에 있는지 찾음
            for (int archer = 0; archer < 3; archer++) {
                int row = r;
                int col = archers[archer];

                boolean kill = false;

                for (int distance = 1; distance <= D; distance++) {

                    for (int nc = col - (distance - 1); nc <= (col + distance -1); nc++) {
                        int nr = row - (distance - Math.abs(nc - col));

                        if (check(nr, nc)) {
                            if (tmpMap[nr][nc] == ENEMY) {
                                killedEnemies.add(new Node(nr, nc));
                                kill = true;
                                break;
                            }
                        }
                    }

                    if (kill) {
                        break;
                    }
                }
            }

            while (!killedEnemies.isEmpty()) {
                Node dead = killedEnemies.pop();
                if (tmpMap[dead.r][dead.c] == ENEMY) {
                    tmpMap[dead.r][dead.c] = 0;
                    sumKill++;
                }
            }
        }

        answer = Math.max(answer, sumKill);

    }

    private static boolean check(int nr, int nc) {
        if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
            return true;
        }
        return false;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Node {
    int r, c;

    Node(int r, int c) {
        this.r = r;
        this.c = c;
    }
}