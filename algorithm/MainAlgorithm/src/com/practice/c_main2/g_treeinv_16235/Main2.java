package com.practice.c_main2.g_treeinv_16235;

/**
 * 출처 : https://www.acmicpc.net/source/16289858
 */

import java.io.*;
import java.util.*;

public class Main2 {

    private static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

    private static int N, M, K;

    private static int[][] map;
    private static int[][] add;
    private static int[][] newTreesNums;

    private static ArrayList<Integer>[][] trees;

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
        add = new int[N][N];
        newTreesNums = new int[N][N];

        trees = new ArrayList[N][N];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = 5;
                add[r][c] = s2i(st.nextToken());
                trees[r][c] = new ArrayList<>();
            }
        }

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());

            int row = s2i(st.nextToken()) - 1;
            int col = s2i(st.nextToken()) - 1;
            int age = s2i(st.nextToken());
            trees[row][col].add(age);
        }
    }

    private static void solve() {
        while (K-- > 0) {
            nourishAndFertilize();
            addNewTree();
        }

        System.out.println(countAliveTree());
    }

    private static void nourishAndFertilize() {
        int addFromDeadTrees;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {

                int index = trees[r][c].size() - 1;

                addFromDeadTrees = 0;
                newTreesNums[r][c] = 0;

                for (int i = index; i >= 0; i--) {
                    int curAge = trees[r][c].get(i);

                    if (curAge > map[r][c]) {
                        addFromDeadTrees += (curAge / 2);
                        trees[r][c].remove(i);
                    } else {
                        map[r][c] -= curAge;
                        trees[r][c].set(i, ++curAge);
                        if (curAge % 5 == 0) {
                            newTreesNums[r][c] += 1;
                        }
                    }
                }

                map[r][c] += (addFromDeadTrees + add[r][c]);
            }
        }
    }

    private static void addNewTree() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {

                int cntNewTree = newTreesNums[r][c];

                for (int k = 0; k < 8; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];

                    if (0 > nr || nr >= N || 0 > nc || nc >= N) {
                        continue;
                    }

                    for (int i = 0; i < cntNewTree; i++) {
                        trees[nr][nc].add(1);
                    }
                }
            }
        }
    }

    private static int countAliveTree() {
        int result = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                result += trees[r][c].size();
            }
        }
        return result;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
