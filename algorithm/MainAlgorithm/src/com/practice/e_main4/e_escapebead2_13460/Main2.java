package com.practice.e_main4.e_escapebead2_13460;

import java.util.*;
import java.io.*;

public class Main2 {

    private static final char RED = 'R';
    private static final char BLUE = 'B';
    private static final char WALL = '#';
    private static final char HOLE = 'O';

    /** 동서남북 */
    private static final int[] dr = {0, 0, 1, -1};
    private static final int[] dc = {1, -1, 0, 0};

    private static int N, M;
    private static char[][] map;

    /** redRow / redCol / blueRow / blueCol */
    private static boolean[][][][] visited;

    public static void main(String[] args) throws Exception {
        Beads start = inputData();
        solve(start);
    }

    private static Beads inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());

        map = new char[N][M];
        visited = new boolean[N][M][N][M];

        Beads beads = new Beads();

        for (int r = 0; r < N; r++) {
            String s = br.readLine();
            for (int c = 0; c < M; c++) {
                map[r][c] = s.charAt(c);

                if (map[r][c] == RED) {
                    beads.rRow = r;
                    beads.rCol = c;
                } else if (map[r][c] == BLUE) {
                    beads.bRow = r;
                    beads.bCol = c;
                }
            }
        }

        return beads;
    }

    private static void solve(Beads start) {
        bfs(start);
    }

    private static void bfs(Beads start) {
        Queue<Beads> q = new LinkedList<>();
        q.offer(start);

        while(!q.isEmpty()) {
            Beads beads = q.poll();
            visited[beads.rRow][beads.rCol][beads.bRow][beads.bCol] = true;

            // 11번 이상 굴렸을 경우 -1 출력
            if (beads.cnt >= 10) {
                System.out.println(-1);
                return;
            }

            // 현재 두 구슬의 위치를 기준으로 동, 서, 남, 북으로 굴려본다.
            for (int dir = 0; dir < 4; dir++) {

                // Blue 먼저 굴린다.
                int bnRow = beads.bRow;
                int bnCol = beads.bCol;

                while (map[bnRow + dr[dir]][bnCol + dc[dir]] != WALL) {
                    bnRow += dr[dir];
                    bnCol += dc[dir];

                    if (map[bnRow][bnCol] == HOLE) {
                        break;
                    }
                }

                // Red 굴린다.
                int rnRow = beads.rRow;
                int rnCol = beads.rCol;

                while (map[rnRow + dr[dir]][rnCol + dc[dir]] != WALL) {
                    rnRow += dr[dir];
                    rnCol += dc[dir];

                    if (map[rnRow][rnCol] == HOLE) {
                        break;
                    }
                }

                // BLUE가 HOLE에 빠졌으면, 탐색을 멈춘다.
                if (map[bnRow][bnCol] == HOLE) continue;

                // RED만 HOLE에 빠졌다면, 정답을 출력한다.
                if (map[rnRow][rnCol] == HOLE) {
                    System.out.println(beads.cnt + 1);
                    return;
                }

                // 두 구슬의 위치가 같다면, 위치를 조정한다.
                if (rnRow == bnRow && rnCol == bnCol) {
                    switch (dir) {
                        case 0: // 동
                            if (beads.rCol > beads.bCol) bnCol -= 1;
                            else rnCol -= 1;
                            break;

                        case 1: // 서
                            if (beads.rCol > beads.bCol) rnCol += 1;
                            else bnCol += 1;
                            break;

                        case 2: // 남
                            if (beads.rRow > beads.bRow) bnRow -= 1;
                            else rnRow -= 1;
                            break;

                        case 3: // 북
                            if (beads.rRow > beads.bRow) rnRow += 1;
                            else bnRow += 1;
                            break;
                    }
                }

                // 두 구슬을 굴린 후의 각각의 위치가 처음 탐색하는 것이라면 큐에 넣는다.
                if (!visited[rnRow][rnCol][bnRow][bnCol]) {
                    q.offer(new Beads(rnRow, rnCol, bnRow, bnCol, beads.cnt + 1));
                }
            }
        }

        System.out.println(-1);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Beads {
    int rRow, rCol, bRow, bCol, cnt;

    Beads(int rRow, int rCol, int bRow, int bCol, int cnt) {
        this.rRow = rRow;
        this.rCol = rCol;
        this.bRow = bRow;
        this.bCol = bCol;
        this.cnt = cnt;
    }

    Beads() {
        this(-1, -1, -1, -1, 0);
    }
}