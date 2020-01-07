package com.practice.c_main2.g_treeinv_16235;

import java.util.*;
import java.io.*;

public class Main {

    private static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

    private static int N;
    private static int M;
    private static int K;

    private static int[][] map;
    private static int[][] a;

    private static LinkedList<Pair>[] d;
    private static Deque<Pair> deadTrees;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());
        K = s2i(st.nextToken());

        map = new int[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                map[r][c] = 5;
            }
        }

        a = new int[N][N];
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                a[r][c] = s2i(st.nextToken());
            }
        }

        d = new LinkedList[N];
        for (int r = 0; r < N; r++) {
            d[r] = new LinkedList<>();
        }

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());

            int row = s2i(st.nextToken()) - 1;
            int col = s2i(st.nextToken()) - 1;
            int age = s2i(st.nextToken());

            d[row].add(new Pair(row, col, age));
        }

        for (int r = 0; r < N; r++) {
            Collections.sort(d[r]);
        }

        deadTrees = new ArrayDeque<>();
    }

    private static void solve() {
        while(K-- > 0) {
            spring();
            summer();
            fall();
            winter();
        }

        System.out.println(countAliveTree());
    }

    private static void spring() {
        nourishment();
        getOlder();
    }

    private static void summer() {
        fertilizer();
    }

    private static void fall() {
        addTree();
    }

    private static void winter() {
        addNutrient();
    }

    private static void nourishment() {
        for (int r = 0; r < N; r++) {
            int s;
            for (s = 0; s < d[r].size(); s++) {
                Pair p = d[r].get(s);

                int tr = p.r;
                int tc = p.c;
                int tAge = p.age;

                if (map[tr][tc] - tAge < 0) {
                    Pair dead = d[r].remove(s);
                    s--;
                    deadTrees.addLast(dead);
                } else {
                    map[tr][tc] -= tAge;
                }
            }
        }
    }

    private static void getOlder() {
        for (int r = 0; r < N; r++) {
            int size = d[r].size();
            for (int s = 0; s < size; s++) {
                Pair p = d[r].get(s);
                d[r].set(s, new Pair(p.r, p.c, p.age + 1));
            }
        }
    }

    private static void fertilizer() {
        while (!deadTrees.isEmpty()) {
            Pair dead = deadTrees.removeFirst();
            int dr = dead.r;
            int dc = dead.c;
            int dAge = dead.age;

            map[dr][dc] += (dAge / 2);
        }
    }

    private static void addTree() {
        for (int r = 0; r < N; r++) {
            int s;
            for (s = 0; s < d[r].size(); s++) {
                Pair p = d[r].get(s);

                int tr = p.r;
                int tc = p.c;
                int tAge = p.age;

                if (tAge % 5 == 0) {
                    for (int k = 0; k < 8; k++) {
                        int nr = tr + dr[k];
                        int nc = tc + dc[k];

                        if (0 > nr || nr >= N || 0 > nc || nc >= N) {
                            continue;
                        }

                        d[nr].addFirst(new Pair(nr, nc, 1));

                        if (nr == r) {
                            s++;
                        }
                    }
                }
            }
        }
    }

    private static void addNutrient() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                map[r][c] += a[r][c];
            }
        }
    }

    private static int countAliveTree() {
        int sum = 0;

        for (int r = 0; r < N; r++) {
            sum += d[r].size();
        }

        return sum;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair implements Comparable<Pair> {
    int r, c, age;

    Pair(int r, int c, int age) {
        this.r = r;
        this.c = c;
        this.age = age;
    }

    @Override
    public int compareTo(Pair o) {
        if (this.age == o.age) {
            return Integer.compare(this.c, o.c);
        }
        return Integer.compare(this.age, o.age);
    }
}
