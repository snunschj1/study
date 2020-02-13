package com.practice.g_main6.f_stemcell_SW5653;

import java.io.*;
import java.util.*;

public class Main2 {

    private static final int[] dr = {1, -1, 0, 0};
    private static final int[] dc = {0, 0, 1, -1};

    private static int T, N, M, K, now;

    private static int[][] map;
    private static boolean[][] visit;

    private static LinkedList<Cell> q;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = s2i(br.readLine());

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());

            N = s2i(st.nextToken());
            M = s2i(st.nextToken());
            K = s2i(st.nextToken());

            map = new int[600 + N][600 + M];
            visit = new boolean[600 + N][600 + M];
            q = new LinkedList<>();

            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < M; c++) {
                    int life = s2i(st.nextToken());

                    if (life > 0) {
                        map[300 + r][300 + c] = life;
                        visit[300 + r][300 + c] = true;
                        q.add(new Cell(false, 300 + r, 300 + c, life, life, life));
                    }
                }
            }
            st = null;

            solve(t);
        }
        br.close();
    }

    private static void solve(int test) {
        now = 0;

        for (int k = 0; k < K; k++) {
            now++;
            Collections.sort(q);    // 리스트를 life 큰 순서대로 정렬한다.
            spread();
        }
        System.out.printf("#%d %d\n", test, q.size());
    }

    private static void spread() {
        int size = q.size();
        for (int i = 0; i < size; i++) {
            Cell c = q.poll();

            if (c.inactiveTime == now) {    // 활성화된다.
                c.status = true;
                q.add(c);
                continue;
            } else if (c.inactiveTime > now) { // 활성화되기 전
                q.add(c);
                continue;
            }

            if (c.status) {

                if (c.activeLife == c.life) {   // 사실상 처음에만 퍼진다.

                    for (int d = 0; d < 4; d++) {
                        int nr = c.r + dr[d];
                        int nc = c.c + dc[d];

                        if (!visit[nr][nc]) {
                            visit[nr][nc] = true;
                            map[nr][nc] = c.life;
                            q.add(new Cell(false, nr, nc, c.life, now + c.life, c.life));
                        }
                    }
                }
                c.activeLife--;

                if (c.activeLife > 0) q.add(c);
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Cell implements Comparable<Cell> {
    boolean status;
    int r, c;
    int life;
    int inactiveTime;   // 비활성화 상태가 끝나는 시각
    int activeLife;     // 활성화 상태로 있는 시간

    Cell (boolean status, int r, int c, int life, int inactiveTime, int activeLife) {
        this.status = status;
        this.r = r;
        this.c = c;
        this.life = life;
        this.inactiveTime = inactiveTime;
        this.activeLife = activeLife;
    }

    @Override
    public int compareTo(Cell o) {
        // 동시에 번식하는 경우, life 큰 줄기세포가 칸을 차지함
        // 이를 위해서 매 시간마다 연결리스트를 life 큰 순서로 sorting
        if (this.life > o.life) {
            return -1;
        } else if (this.life < o.life){
            return 1;
        } else {
            return 0;
        }
    }
}