package com.practice.f_main5.c_colorpaper_17136;

/**
 * 출처 : https://www.acmicpc.net/source/16968071
 */

import java.util.*;
import java.io.*;

public class Main3 {

    private static final int EMPTY = 0;
    private static final int TARGET = 1;

    private static int[][] map = new int[10][10];
    private static int[] paper = {0, 5, 5, 5, 5, 5};

    private static int min = 25;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int r = 0; r < 10; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < 10; c++) {
                map[r][c] = s2i(st.nextToken());
            }
        }

        br.close();
    }

    private static void solve() {
        dfs(0, 0);

        if (min == 25) {
            System.out.println("-1");
        } else {
            System.out.println(min);
        }
    }

    private static void dfs(int idx, int paperCnt) {
        // map의 (0, 0) 부터 (9, 9) 까지 검토할 것이다.

        if (idx == 100) {
            if (min > paperCnt) {
                min = paperCnt;
                return;
            }
            return;
        }

        if (min <= paperCnt) {
            // 이미 최소 색종이 사용 개수보다 현재 사용 개수가 넘어간다면 반환
            return;
        }

        int r = idx / 10;
        int c = idx % 10;

        if (map[r][c] == TARGET) {
            for (int size = 5; size >= 1; size--) {
                // 해당 좌표에 색종이 크기 5 부터 색종이를 붙이는 시도를 한다.

                if (paper[size] > 0 && check(r, c, size)) {
                    // 색종이를 붙일 수 있으면 붙이고, 다음 좌표로 넘어간다.

                    attachPaper(r, c, size);
                    dfs(idx+1, paperCnt+1);
                    detachPaper(r, c, size);
                }

                // 반환되면, 해당 좌표에 다음 크기의 색종이를 붙이는 시도를 한다.
            }

        } else {
            // map[r][c] == 0 이면, 다음 좌표로 넘어간다.
            dfs(idx + 1, paperCnt);
        }
    }

    private static boolean check(int row, int col, int size) {

        // 범위 검토
        if (row + size > 10 || col + size > 10) {
            return false;
        }

        // 색종이 채우려는 칸들이 모두 1인지 검토
        for (int r = row; r < row + size; r++) {
            for (int c = col; c < col + size; c++) {
                if (map[r][c] != TARGET) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void attachPaper(int row, int col, int size) {
        setPaper(row, col, size, EMPTY);
        paper[size]--;
    }

    private static void detachPaper(int row, int col, int size) {
        setPaper(row, col, size, TARGET);
        paper[size]++;
    }

    private static void setPaper(int row, int col, int size, int behavior) {
        for (int r = row; r < row + size; r++) {
            for (int c = col; c < col + size; c++) {
                map[r][c] = behavior;
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
