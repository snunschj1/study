package com.practice.c_main2.f_movpop_16234;

import java.util.*;
import java.io.*;

public class Main {

    private static int[] dr = {0, 0, 1, -1};
    private static int[] dc = {1, -1, 0, 0};

    private static int n;
    private static int left;
    private static int right;

    private static int[][] map;
    private static int[][] check;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = s2i(st.nextToken());
        left = s2i(st.nextToken());
        right = s2i(st.nextToken());

        map = new int[n][n];

        for (int r = 0; r < n; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < n; c++) {
                map[r][c] = s2i(st.nextToken());
            }
        }
    }

    private static void solve() {
        int result = movePopulation();
        System.out.println(result);
    }

    private static int movePopulation() {
        boolean repeat;

        int result = -1;

        do {
            ++result;
            repeat = false;
            check = new int[n][n];

            int cnt = 0;
            ArrayList<Integer> arr = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (check[i][j] == 0) {
                        int tmp = bfs(cnt + 1, i, j);

                        if (tmp != 0) {
                            arr.add(tmp);
                            ++cnt;
                        }
                    }

                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (cnt > 0 && check[i][j] != 0 && check[i][j] != -1) {
                        int tmp = check[i][j];
                        map[i][j] = arr.get(tmp - 1);
                    }

                    if (check[i][j] != -1) {
                        repeat = true;
                    }
                }
            }

        } while (repeat);

        return result;
    }

    private static int bfs(int cnt, int row, int col) {
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(row, col));

        check[row][col] = cnt;
        int sum = map[row][col];
        int count = 1;

        while (!q.isEmpty()) {
            Pair p = q.remove();
            int r = p.r;
            int c = p.c;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (0 > nr || nr >= n || 0 > nc || nc >= n) {
                    continue;
                }

                if (check[nr][nc] == 0) {
                    int diff = Math.abs(map[nr][nc] - map[r][c]);

                    if (left <= diff && diff <= right) {
                        q.add(new Pair(nr, nc));
                        check[nr][nc] = cnt;
                        sum += map[nr][nc];
                        count += 1;
                    }
                }
            }
        }

        if (count == 1) {
            check[row][col] = -1;
            return 0;
        } else {
            return sum / count;
        }


    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair {
    int r;
    int c;

    Pair(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

