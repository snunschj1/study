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

    private static int answer;

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
                } 
            }
        }

        answer = 2 * N;
    }

    private static void solve() {
        int[] activated = new int[M];
        Arrays.fill(activated, -1);
        combination(0, 0, M, activated);
        if (answer == 2 * N) {
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
        arr[cnt] = -1;
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

        int cntVirus = M;

        while(!q.isEmpty()) {

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    System.out.print(visit[r][c] + " ");
                }
                System.out.println();
            }
            System.out.println();


            int row = q.remove();
            int col = q.remove();

            for (int k = 0; k < 4; k++) {
                int nr = row + dr[k];
                int nc = col + dc[k];

                if (0 > nr || N <= nr || 0 > nc || N <= nc) {
                    continue;
                }

                if (map[nr][nc] == WALL) {
                    continue;
                }

                if (visit[nr][nc] == -1) {
                    q.add(nr);
                    q.add(nc);
                    visit[nr][nc] = visit[row][col] + 1;

                    if (map[nr][nc] == VIRUS) {
                        cntVirus++;

                        if (cntVirus == numVirus) {
                            if (answer > visit[nr][nc]) answer = visit[nr][nc];
                            q.clear();
                            break;
                        }
                    }
                }
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