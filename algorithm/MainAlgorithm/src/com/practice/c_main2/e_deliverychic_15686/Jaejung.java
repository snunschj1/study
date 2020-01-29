package com.practice.c_main2.e_deliverychic_15686;

import java.util.*;
import java.io.*;

public class Jaejung {

    private static final int HOUSE = 1;
    private static final int CHICKEN = 2;

    private static int N, M;

    private static int[][] map;

    private static Pair[] houses = new Pair[100];
    private static Pair[] chickens = new Pair[13];
    private static boolean[] checkChick = new boolean[13];

    private static int houseNums = 0, chickenNums = 0;

    private static int answer = Integer.MAX_VALUE;

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

                if (map[r][c] == HOUSE) {
                    houses[houseNums++] = new Pair(r, c);
                } else if (map[r][c] == CHICKEN) {
                    chickens[chickenNums++] = new Pair(r, c);
                }
            }
        }

        br.close();
    }

    private static void solve() {
        selectChickens(0, 0);
        System.out.println(answer);
    }

    private static void selectChickens(int cnt, int num) {
        if (cnt == M) {
            answer = Math.min(answer, calculate());
            return;
        }

        for (int i = num; i < chickenNums; i++) {
            checkChick[i] = true;
            selectChickens(cnt+1, i+1);
            checkChick[i] = false;
        }
    }

    private static int calculate() {
        int sum = 0;

        for (int i = 0; i < houseNums; i++) {
            int min = Integer.MAX_VALUE;

            for (int j = 0; j < chickenNums; j++) {
                if (!checkChick[j]) continue;
                min = Math.min(min, Math.abs(chickens[j].r - houses[i].r) + Math.abs(chickens[j].c - houses[i].c));
            }

            sum += min;
        }

        return sum;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair {
    int r, c;

    Pair(int r, int c) {
        this.r = r;
        this.c = c;
    }
}
