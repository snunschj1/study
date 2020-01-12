package com.practice.d_main3.c_gerrymandering2_17779;

import java.util.*;
import java.io.*;

public class Main {

    private static int N;

    private static int[][] map;
    private static int[][] visited;

    private static int sum = 0;
    private static int result = 40000;


    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());

        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = s2i(st.nextToken());
                sum += map[i][j];
            }
        }
    }

    private static void solve() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 1; k < N; k++) {
                    for (int l = 1; l < N; l++) {
                        if (i + k + l > N - 1) {
                            continue;
                        }

                        if (j - k < 0) {
                            continue;
                        }

                        if (j + l > N - 1) {
                            continue;
                        }
                        visited = new int[N][N];
                        start(i, j, k, l);
                    }
                }
            }
        }

        System.out.println(result);
    }

    private static void start(int r, int c, int d1, int d2) {
        drawLine(r, c, d1, d2);
        paint(r, c);

        int n1 = sumPop(0, r+d1, 0, c+1);
        int n2 = sumPop(0, r+d2+1, c+1, N);
        int n3 = sumPop(r+d1, N, 0, c-d1+d2);
        int n4 = sumPop(r+d2+1, N, c-d1+d2, N);
        int n5 = sum - n1 - n2 - n3 - n4;

        int[] arr = new int[]{n1, n2, n3, n4, n5};

        int min = findMin(arr);
        int max = findMax(arr);

        int tmp = Math.abs(max - min);

        if (result > tmp) result = tmp;
    }

    private static void drawLine(int r, int c, int d1, int d2) {
        visited[r][c] = 5;
        // line 1
        for (int i = 1; i <= d1; i++) {
            visited[r + i][c - i] = 5;
        }
        // line 2
        for (int i = 1; i <= d2; i++) {
            visited[r + i][c + i] = 5;
        }
        // line 3
        for (int i = 1; i <= d2; i++) {
            visited[r + d1 + i][c - d1 + i] = 5;
        }
        // line 4
        for (int i = 1; i < d1; i++) {
            visited[r + d2 + i][c + d2 - i] = 5;
        }
    }

    private static void paint(int r, int c) {

        for (int i = r; i < N; i++) {

            int col1 = -1;
            int col2 = -1;

            for (int j = 0; j < N; j++) {
                if (i == r && j < c) {
                    continue;
                }

                if (visited[i][j] == 5) {
                    if (col1 == -1) col1 = j;
                    else col2 = j;
                }
            }

            if (col2 != -1) {
                for (int j = col1 + 1; j < col2; j++) {
                    visited[i][j] = 5;
                }
            }
        }
    }

    private static int sumPop(int startRow, int finishRow, int startCol, int finishCol) {
        int sum = 0;

        for (int i = startRow; i < finishRow; i++) {
            for (int j = startCol; j < finishCol; j++) {
                if (visited[i][j] != 5) {
                    sum += map[i][j];
                }
            }
        }
        return sum;
    }

    private static int findMin(int[] a) {
        int min = a[0];
        for (int i = 1; i < 5; i++) {
            if (min > a[i]) {
                min = a[i];
            }
        }
        return min;
    }

    private static int findMax(int[] a) {
        int max = a[0];
        for (int i = 1; i < 5; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }

}

