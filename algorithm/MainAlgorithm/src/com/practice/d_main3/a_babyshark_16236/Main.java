package com.practice.d_main3.a_babyshark_16236;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dr = {-1, 0, 0, 1};
    private static final int[] dc = {0, -1, 1, 0};

    private static final int EMPTY = 0;
    private static final int FISH1 = 1;
    private static final int FISH6 = 6;
    private static final int SHARK = 9;

    private static int N;
    private static int[][] map;
    private static LinkedList<Integer> fishes = new LinkedList<>();

    private static Shark shark = null;
    private static int second = 0;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = s2i(br.readLine());
        map = new int[N][N];

        StringTokenizer st;
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                int status = s2i(st.nextToken());
                map[r][c] = status;

                if (FISH1 <= status && status <= FISH6) {
                    fishes.add(status);
                } else if (status == SHARK) {
                    shark = new Shark(r, c, 2, 0);
                }
            }
        }

        Collections.sort(fishes);
    }

    private static void solve() {

        while (!fishes.isEmpty() && fishes.getFirst() < shark.s) {

            int[][] visited = new int[N][N];

            Queue<Shark> q = new LinkedList<>();
            q.add(new Shark(shark.r, shark.c, shark.s, shark.cnt));
            visited[shark.r][shark.c] = 1;
            map[shark.r][shark.c] = EMPTY;

            while (!q.isEmpty()) {
                Shark cur = q.remove();
                int r = cur.r;
                int c = cur.c;
                int size = cur.s;
                int cnt = cur.cnt;

                boolean eatFish = false;

                for (int i = 0; i < 4; i++) {
                    int nr = r + dr[i];
                    int nc = c + dc[i];

                    if (0 > nr || N >= nr || 0 > nc || N >= nc) {
                        continue;
                    }

                    if (visited[nr][nc] != 0) {
                        continue;
                    }

                    if (map[nr][nc] > size) {
                        continue;
                    }

                    if (map[nr][nc] == EMPTY || map[nr][nc] == size) {
                        q.add(new Shark(nr, nc, size, cnt));
                        visited[nr][nc] = visited[r][c] + 1;
                    } else if (map[nr][nc] < size) {
                        eatFish = true;
                        fishes.removeFirst();
                        visited[nr][nc] = visited[r][c] + 1;
                        second += visited[nr][nc] - 1;
                        map[nr][nc] = SHARK;

                        if (size == cnt + 1) {
                            shark = new Shark(nr, nc, size+1, 0);
                        } else {
                            shark = new Shark(nr, nc, size, cnt + 1);
                        }
                        break;
                    }
                }

                if (eatFish) {
                    break;
                }
            }
        }

        System.out.println(second);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Shark {
    int r, c, s, cnt;

    Shark(int r, int c, int s, int cnt) {
        this.r = r;
        this.c = c;
        this.s = s;
        this.cnt = cnt;
    }
}
