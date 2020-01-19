package com.practice.e_main4.b_lab3_17142;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int VIRUS = 2;

    private static int N, M;
    private static int[][] map;

    private static Virus[] viri = new Virus[10];
    private static int numVirus = 0;
    private static int numEmpty = 0;

    private static int answer = Integer.MAX_VALUE;
    private static boolean flag = false;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());

        map = new int[N][N];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = s2i(st.nextToken());

                if (map[r][c] == VIRUS) {
                    viri[numVirus++] = new Virus(r, c);
                } else if (map[r][c] == EMPTY) {
                    numEmpty++;
                }
            }
        }
    }

    private static void solve() {
        int[] activated = new int[M];
        Arrays.fill(activated, -1);
        combination(0, 0, M, activated);

        if (!flag) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }
    }

    private static void combination(int index, int cnt, int target, int[] arr) {
        if (cnt == target) {
            activateVirus(arr);
            return;
        }

        if (index >= numVirus) {
            return;
        }

        arr[cnt] = index;
        combination(index + 1, cnt + 1, target, arr);
        combination(index + 1, cnt, target, arr);
    }

    private static void activateVirus(int[] arr) {

        int[][] visit = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(visit[i], -1);
        }

        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < M; i++) {
            int index = arr[i];
            Virus virus = viri[index];
            q.add(virus.r);
            q.add(virus.c);

            visit[virus.r][virus.c] = 0;
        }

        int second = 0;
        int cntEmpty = 0;

        while(!q.isEmpty()) {

            int row = q.remove();
            int col = q.remove();

            for (int k = 0; k < 4; k++) {
                int nr = row + dr[k];
                int nc = col + dc[k];

                if (0 > nr || N <= nr || 0 > nc || N <= nc || map[nr][nc] == WALL) {
                    continue;
                }

                if (visit[nr][nc] == -1) {
                    q.add(nr);
                    q.add(nc);
                    visit[nr][nc] = visit[row][col] + 1;

                    if (map[nr][nc] == EMPTY) {
                        cntEmpty++;

                        if (second < visit[nr][nc]) {
                            second = visit[nr][nc];
                        }
                    }
                }
            }
        }

        if (cntEmpty == numEmpty) {
            if (answer > second) {
                flag = true;
                answer = second;
            }
        }

    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Virus {
    int r, c;

    Virus(int r, int c) {
        this.r = r;
        this.c = c;
    }
}