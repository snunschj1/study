package com.practice.e_main4.b_lab3_17142;

import java.util.*;
import java.io.*;

public class Repeat1 {

    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int INACT = 2;
    private static final int ACT = 3;

    private static final int[] dr = {0, 0, 1, -1};
    private static final int[] dc = {1, -1, 0, 0};

    private static int N, M;

    private static int[][] map;
    private static ArrayList<Plot> viruses = new ArrayList<>();

    private static int cntEmpty = 0;
    private static int cntVirus = 0;

    private static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        inputData();
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

                if (map[r][c] == EMPTY) cntEmpty++;
                else if (map[r][c] == INACT) {
                    viruses.add(new Plot(r, c));
                    cntVirus++;
                }
            }
        }
        solve();
    }

    private static void solve() {
        int[] start = new int[M];
        dfs(start, 0, 0);

        if (ans == Integer.MAX_VALUE) {
            System.out.println("-1");
        } else {
            System.out.println(ans);
        }
    }

    private static void dfs(int[] a, int idx, int cnt) {
        // Todo : 활성으로 바꿀 바이러스를 M개 선택한다.

        if (cnt == M) {
            spread(a);
            return;
        }

        for (int i = idx; i < cntVirus; i++) {
            a[cnt] = i;
            Plot p = viruses.get(i);
            map[p.r][p.c] = ACT;
            dfs(a, i + 1, cnt + 1);
            map[p.r][p.c] = INACT;
        }
    }

    private static void spread(int[] a) {
        int[][] visit = new int[N][N];

        int time = 0;
        int empty = cntEmpty;

        Queue<Plot> q = new LinkedList<>();
        for (int i = 0; i < M; i++) {       // Todo : 초기 활성 바이러스
            int idx = a[i];
            Plot p = viruses.get(idx);
            q.add(p);
            visit[p.r][p.c] = 1;
        }

        while (!q.isEmpty()) {

            if (empty == 0) {
                break;
            }

            Plot p = q.remove();

            for (int k = 0; k < 4; k++) {
                int nr = p.r + dr[k];
                int nc = p.c + dc[k];

                if (0 <= nr && nr < N && 0 <= nc && nc < N) {
                    if (visit[nr][nc] == 0) {

                        if (map[nr][nc] == WALL) continue;

                        if (map[nr][nc] == EMPTY) {
                            empty--;
                        }

                        visit[nr][nc] = visit[p.r][p.c] + 1;
                        if (time < visit[nr][nc] - 1) time = visit[nr][nc] - 1;
                        q.add(new Plot(nr, nc));
                    }
                }
            }
        }

        if (empty == 0) {
            if (ans > time) {
                ans = time;
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Plot {
    int r, c;

    public Plot(int r, int c) {
        this.r = r;
        this.c = c;
    }
}