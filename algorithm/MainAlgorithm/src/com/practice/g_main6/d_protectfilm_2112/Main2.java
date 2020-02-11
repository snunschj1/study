package com.practice.g_main6.d_protectfilm_2112;

import java.util.*;
import java.io.*;

public class Main2 {

    private static final int A = 1;
    private static final int B = 0;

    private static int D, W, K;
    private static int ans;

    private static int[][] arr, copy;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = s2i(br.readLine());

        for (int t = 1; t <= T; t++) {
            String[] s = br.readLine().split(" ");

            D = s2i(s[0]);
            W = s2i(s[1]);
            K = s2i(s[2]);

            arr = new int[D][W];
            for (int r = 0; r < D; r++) {
                s = br.readLine().split(" ");
                for (int c = 0; c < W; c++) {
                    arr[r][c] = s2i(s[c]);
                }
            }

            solve(t);
        }
    }

    private static void solve(int test) {
        if (K == 1) {
            System.out.printf("#%d %d\n", test, 0);
        } else {
            ans = Integer.MAX_VALUE;
            copy = copyMap(arr);

            dfs(0, 0);
            System.out.printf("#%d %d\n", test, ans);
        }
    }

    private static void dfs(int floor, int inserCnt) {

        if (inserCnt >= ans) return;

        if (floor >= D) {
            if (check()) {
                ans = inserCnt;
            }
            return;
        }

        // 주입 X
        dfs(floor + 1, inserCnt);

        // A 주입
        Arrays.fill(arr[floor], A);
        dfs(floor + 1, inserCnt + 1);

        // B 주입
        Arrays.fill(arr[floor], B);
        dfs(floor + 1, inserCnt + 1);

        // 해제
        arr[floor] = copyArr(copy[floor]);
    }

    private static boolean check() {

        int status;
        int cnt;

        for (int c = 0; c < W; c++) {

            status = arr[0][c];
            cnt = 1;

            for (int r = 1; r < D; r++) {

                if (arr[r][c] == status) {
                    cnt += 1;
                } else {
                    status = arr[r][c];
                    cnt = 1;
                }

                if (cnt == K) break;

                if (r == D - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[] copyArr(int[] a) {
        int[] tmp = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            tmp[i] = a[i];
        }

        return tmp;
    }

    private static int[][] copyMap(int[][] a) {
        int[][] tmp = new int[a.length][a[0].length];

        for (int r = 0; r < a.length; r++) {
            for (int c = 0; c < a[0].length; c++) {
                tmp[r][c] = a[r][c];
            }
        }

        return tmp;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
