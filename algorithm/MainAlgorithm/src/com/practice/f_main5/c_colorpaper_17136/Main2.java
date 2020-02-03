package com.practice.f_main5.c_colorpaper_17136;

import java.util.*;
import java.io.*;

public class Main2 {

    private static final int TARGET = 1;

    private static int oneCnt;

    private static int[][] map = new int[10][10];
    private static int[][] tMap;

    private static int[] paperCount = new int[6];

    private static int answer = 25;

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

                if (map[r][c] == 1) {
                    oneCnt++;
                }
            }
        }
        br.close();
    }

    private static void solve() {
        decidePaperCount(1);

        if (answer == 25) {
            System.out.println("-1");
        } else {
            System.out.println(answer);
        }
    }

    private static void decidePaperCount(int index) {
        // 각 색종이마다 몇 개를 배당할지 정하는 재귀식
        if (index > 5) {

            if (checkPaperCount()) {
                // 색종이 개수 조합이 1을 모두 cover 할 수 있는 경우

                if (countPaper() > answer) {
                    // 색종이 개수가 최소값보다 크다면 건너띔
                    return;
                }

                tMap = copyMap(map);

                // 색종이 크기 5 부터 색종이 위치를 결정한다.
                int target = paperCount[5];
                paperCombination(0, target, 5, tMap);
            }

            return;
        }

        for (int i = 0; i <= 5; i++) {
            paperCount[index] = i;
            decidePaperCount(index + 1);
        }

    }

    private static void paperCombination(int cnt, int target, int paperSize, int[][] map) {
        // 해당 색종이 개수만큼 색종이 위치 조합을 만듦

        if (cnt >= target) {

            if (paperSize <= 1) {
                // 색종이 크기 1까지 모두 위치 조합을 완성하면, 사용한 색종이가 몇 개인지 계산
                int usedPaper = countPaper();

                if (answer > usedPaper) {
                    answer = usedPaper;
                }
                return;
            }

            // 해당 색종이의 개수 만큼 위치를 결정했다면, 다음 색종이로 넘어간다.
            int nextTarget = paperCount[paperSize - 1];
            paperCombination(0, nextTarget, paperSize - 1, map);
            return;
        }


        for (int r = 0; r <= 10 - paperSize; r++) {
            for (int c = 0; c <= 10 - paperSize; c++) {

                if (map[r][c] != TARGET) continue;

                if (Paper.checkPaper(r, c, paperSize, map)) {
                    // 색종이를 붙일 수 있는 경우
                    Paper.attachPaper(r, c, paperSize, map);
                    paperCombination(cnt + 1, target, paperSize, map);
                    Paper.detachPaper(r, c, paperSize, map);
                }
            }
        }
    }

    private static boolean checkPaperCount() {
        int sum = 0;

        for (int i = 1; i <= 5; i++) {
            sum += (i * i * paperCount[i]);
        }
        return sum == oneCnt;
    }

    private static int countPaper() {
        int sum = 0;

        for (int i = 1; i <= 5; i++) {
            sum += paperCount[i];
        }
        return sum;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }

    private static int[][] copyMap(int[][] map) {
        int[][] tmp = new int[10][10];

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                tmp[r][c] = map[r][c];
            }
        }
        return tmp;
    }
}

class Paper {

    private static final int EMPTY = 0;
    private static final int TARGET = 1;
    private static final int ATTACH = 2;

    static boolean checkPaper(int row, int col, int paperSize, int[][] map) {
        // 색종이를 붙일 수 있는지 본다.
        for (int r = row; r < row + paperSize; r++) {
            for (int c = col; c < col + paperSize; c++) {
                if (map[r][c] == EMPTY || map[r][c] == ATTACH) {
                    return false;
                }
            }
        }
        return true;
    }

    static void attachPaper(int row, int col, int paperSize, int[][] map) {
        setPaper(row, col, paperSize, map, TARGET, ATTACH);
    }

    static void detachPaper(int row, int col, int paperSize, int[][] map) {
        setPaper(row, col, paperSize, map, ATTACH, TARGET);
    }

    private static void setPaper(int row, int col, int paperSize, int[][] map, int current, int change) {
        for (int r = row; r < row + paperSize; r++) {
            for (int c = col; c < col + paperSize; c++) {
                if (map[r][c] == current) {
                    map[r][c] = change;
                }
            }
        }
    }
}

class Test {

    static void flush() {
        Print.flush();
    }

    static void printMap(int[][] map) {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Print.write(map[r][c] + " ");
            }
            Print.write("\n");
        }
        Print.write("\n");
    }

    static void printStr(String s) {
        Print.write(s);
    }

    static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            Print.write(arr[i] + " ");
        }
        Print.write("\n");
    }

}

class Print {

    static BufferedOutputStream out;

    static {
        try {
            out = new BufferedOutputStream(new FileOutputStream("/Users/hyunjun/Documents/output.txt"));
        } catch (Exception e) {
        }
    }

    static void flush() {
        try {
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }

    static void write(String s) {
        try {
            out.write(s.getBytes());
        } catch (Exception e) {
        }
    }
}


