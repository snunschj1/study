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

    private static Deque<Pair> d;
    private static Deque<Pair> remainedTrees;
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

        d = new ArrayDeque<>();
        remainedTrees = new ArrayDeque<>();
        deadTrees = new ArrayDeque<>();

        Pair[] tmp = new Pair[M];
        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());

            int row = s2i(st.nextToken()) - 1;
            int col = s2i(st.nextToken()) - 1;
            int age = s2i(st.nextToken());

            tmp[m] = new Pair(row, col, age);
        }

        Arrays.sort(tmp);

        for (int m = 0; m < M; m++) {
            d.addLast(tmp[m]);
        }

//        for (int i = 0; i < d.size(); i++) {
//            System.out.printf("Tree : row = %d col = %d age = %d\n", d.get(i).r, d.get(i).c, d.get(i).age);
//        }

    }

    private static void solve() {
        while (K-- > 0) {
            spring();

//            for (int i = 0; i < d.size(); i++) {
//                System.out.printf("%d년 남음 After Spring Tree : row = %d col = %d age = %d\n", K, d.get(i).r, d.get(i).c, d.get(i).age);
//            }

            summer();

//            for (int i = 0; i < d.size(); i++) {
//                System.out.printf("%d년 남음 After Summer Tree : row = %d col = %d age = %d\n", K, d.get(i).r, d.get(i).c, d.get(i).age);
//            }

            fall();

//            for (int i = 0; i < d.size(); i++) {
//                System.out.printf("%d년 남음 After fall Tree : row = %d col = %d age = %d\n", K, d.get(i).r, d.get(i).c, d.get(i).age);
//            }

            winter();

//            for (int i = 0; i < d.size(); i++) {
//                System.out.printf("%d년 남음 After winter Tree : row = %d col = %d age = %d\n", K, d.get(i).r, d.get(i).c, d.get(i).age);
//            }
        }

        System.out.println(countAliveTree());
    }

    private static void spring() {
        while (!d.isEmpty()) {
            Pair p = d.removeFirst();

            int tr = p.r;
            int tc = p.c;
            int tAge = p.age;

            if (map[tr][tc] - tAge < 0) {
                deadTrees.addLast(p);
            } else {
                map[tr][tc] -= tAge;
                remainedTrees.addLast(new Pair(tr, tc, tAge + 1));
            }
        }
    }

    private static void summer() {
        while (!deadTrees.isEmpty()) {
            Pair dead = deadTrees.removeFirst();
            int dr = dead.r;
            int dc = dead.c;
            int dAge = dead.age;

            map[dr][dc] += (dAge / 2);
        }
    }

    private static void fall() {
        while (!remainedTrees.isEmpty()) {
            Pair p = remainedTrees.removeFirst();

            int tr = p.r;
            int tc = p.c;
            int tAge = p.age;

            d.addLast(new Pair(tr, tc, tAge));

            if (tAge % 5 == 0) {
                for (int k = 0; k < 8; k++) {
                    int nr = tr + dr[k];
                    int nc = tc + dc[k];

                    if (0 > nr || nr >= N || 0 > nc || nc >= N) {
                        continue;
                    }

                    d.addFirst(new Pair(nr, nc, 1));
                }
            }
        }
    }

    private static void winter() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                map[r][c] += a[r][c];
            }
        }
    }

    private static int countAliveTree() {
        return d.size();
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
            if (this.c == o.c) {
                return Integer.compare(this.r, o.r);
            }
            return Integer.compare(this.c, o.c);
        }
        return Integer.compare(this.age, o.age);
    }
}
