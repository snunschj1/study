package com.practice.c_main2.e_deliverychic_15686;

/**
 * 출처 : https://www.acmicpc.net/source/16451230
 */

import java.util.*;
import java.io.*;

public class Main3 {

    private static final int HOUSE = 1;
    private static final int CHICKEN = 2;

    private static int n;
    private static int m;

    private static Point[] house;
    private static Point[] chick = new Point[13];
    private static Dist[][] distMap;
    private static boolean[] checkChick = new boolean[13];

    private static int numHouse = 0;
    private static int numChicken = 0;

    private static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = s2i(st.nextToken());
        m = s2i(st.nextToken());

        house = new Point[2*n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int tmp = s2i(st.nextToken());

                if (tmp == HOUSE) {
                    house[numHouse++] = new Point(i, j);
                } else if (tmp == CHICKEN) {
                    chick[numChicken++] = new Point(i, j);
                }
            }
        }


    }

    private static void solve() {
        recordDistHouseFromChicken();
        dfs(0, 0);
        System.out.println(min);
    }

    private static void recordDistHouseFromChicken() {
        distMap = new Dist[numHouse][numChicken];

        for (int i = 0; i < numHouse; i++) {
            for (int j = 0; j < numChicken; j++) {
                distMap[i][j] = new Dist(j, calculateChickDist(i, j));
            }

            Arrays.parallelSort(distMap[i]);
        }
    }

    private static void dfs(int cnt, int idx) {
        if (cnt == m) {
            int sum = 0;

            if (sum < min) {
                min = sum;
            }
            return;
        }

        for (int i = idx; i < numChicken; i++) {
            checkChick[i] = true;
            dfs(cnt + 1, i + 1);
            checkChick[i] = false;
        }
    }


    private static int calculateChickDist(int idxHouse, int idxChick) {
        return Math.abs(house[idxHouse].r - chick[idxChick].r) + Math.abs(house[idxHouse].c - chick[idxChick].c);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Point {
    int r, c;

    Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

class Dist implements Comparable<Dist> {
    int idxChick, dist;

    Dist(int idxChick, int dist) {
        this.idxChick = idxChick;
        this.dist = dist;
    }

    @Override
    public int compareTo(Dist d) {
        if (this.dist == d.dist) {
            return Integer.compare(this.idxChick, d.idxChick);
        }
        return Integer.compare(this.dist, d.dist);
    }
}