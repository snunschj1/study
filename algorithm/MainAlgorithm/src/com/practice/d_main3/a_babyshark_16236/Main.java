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
    private static int[] fishes = new int[7];

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

                if (status == SHARK) {
                    shark = new Shark(r, c, 2, 0, 0);
                } else if (FISH1 <= status && status <= FISH6) {
                    fishes[status] += 1;
                }
            }
        }
    }

    private static void solve() {


        while (isSharkAbleToGo(shark)) {

            int[][] visited = new int[N][N];

            Queue<Shark> q = new LinkedList<>();
            q.add(new Shark(shark.r, shark.c, shark.s, shark.cnt, 0));
            visited[shark.r][shark.c] = 1;
            map[shark.r][shark.c] = EMPTY;

            boolean isCompleted = false;

            boolean eatFish = false;

            while (!q.isEmpty()) {
                Shark cur = q.remove();
                int r = cur.r;
                int c = cur.c;
                int size = cur.s;
                int cnt = cur.cnt;
                int sec = cur.second;

                if (isCompleted) {
                    if(sec >= shark.second) {
                        continue;
                    }
                }

                for (int i = 0; i < 4; i++) {

                    int nr = r + dr[i];
                    int nc = c + dc[i];


                    if (0 > nr || N <= nr || 0 > nc || N <= nc || visited[nr][nc] != 0 || map[nr][nc] > size) {
                        continue;
                    }


                    if (map[nr][nc] == EMPTY || map[nr][nc] == size) {
                        q.add(new Shark(nr, nc, size, cnt, sec + 1));
                        visited[nr][nc] = 1;
                    } else if (map[nr][nc] < size) {
                        visited[nr][nc] = 1;

                        if (size == cnt + 1) {
                            if (isCompleted) {
                                if (shark.r == nr && shark.c < nc) {
                                    continue;
                                }
                                if (shark.r < nr) {
                                    continue;
                                }
                            }
                            shark = new Shark(nr, nc, size+1, 0, sec + 1);
                        } else {
                            if (isCompleted) {
                                if (shark.r == nr && shark.c < nc) {
                                    continue;
                                }
                                if (shark.r < nr) {
                                    continue;
                                }
                            }
                            shark = new Shark(nr, nc, size, cnt + 1, sec + 1);
                        }

                        eatFish = true;
                        isCompleted = true;
                    }
                }

            }

            if (eatFish) {
                fishes[map[shark.r][shark.c]] -= 1;
                second += shark.second;
            } else {
                break;
            }
        }

        System.out.println(second);
    }

    private static boolean isSharkAbleToGo(Shark s) {
        for (int i = 1; i <= 6; i++) {

            if (fishes[i] != 0) {
                if (i < s.s) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Shark {
    int r, c, s, cnt, second;

    Shark(int r, int c, int s, int cnt, int second) {
        this.r = r;
        this.c = c;
        this.s = s;
        this.cnt = cnt;
        this.second = second;
    }
}
