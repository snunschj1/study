package com.practice.f_main5.c_colorpaper_17136;

import java.util.*;
import java.io.*;

public class Main2 {

    private static int remainOne;

    private static int[][] map = new int[10][10];
    private static int[][] tMap;

    private static int[] paperOrder = {1, 2, 3, 4, 5};

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
                    remainOne++;
                }
            }
        }
        br.close();
    }

    private static void solve() {
        int tRemainOne;
        int[] paperCnt;

        do {
            // Todo : test
            Test.printStr("===START===\n");
            Test.printArr(paperOrder);

            tMap = copyMap(map);
            tRemainOne = remainOne;
            paperCnt = new int[]{0, 5, 5, 5, 5, 5};

            for (int i = 0; i < 5; i++) {
                int usedPaperCnt = attachPaper(paperOrder[i], paperCnt);

                tRemainOne -= usedPaperCnt;

                // Todo : test
                Test.printMap(tMap);
                Test.printStr("removeOne = " + usedPaperCnt + "\n");
                Test.printStr("remainOne = " + tRemainOne + "\n");
            }

            if (tRemainOne == 0) {
                // Todo : test
                Test.printStr("SUCCESS\n");

                int usedPaper = countUsedPaper(paperCnt);

                if (answer > usedPaper) {
                    answer = usedPaper;
                }
            }
        } while (next_permutation(paperOrder));

        if (answer == 25) {
            System.out.println("-1");
        } else {
            System.out.println(answer);
        }

        // Todo : test
        Test.flush();
    }


    private static int attachPaper(int paperSize, int[] paperCnt) {
        int sum = 0;

        for (int r = 0; r <= 10 - paperSize; r++) {
            for (int c = 0; c <= 10 - paperSize; c++) {

                if (paperCnt[paperSize] == 0) {
                    break;
                }

                if (Paper.checkPaper(r, c, paperSize, tMap)) {
                    Paper.attachPaper(r, c, paperSize, tMap);
                    sum += (paperSize * paperSize);
                    paperCnt[paperSize] -= 1;
                }
            }
        }

        return sum;
    }

    private static int countUsedPaper(int[] paperCnt) {
        int sum = 0;

        for (int i = 1; i <= 5; i++) {
            sum += paperCnt[i];
        }

        return 25 - sum;
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

    private static boolean next_permutation(int[] a) {
        int i = a.length - 1;

        while (i > 0 && a[i] <= a[i - 1]) {
            i -= 1;
        }

        if (i <= 0) {
            return false;
        }

        int j = a.length - 1;

        while (a[j] <= a[i - 1]) {
            j -= 1;
        }

        int tmp = a[j];
        a[j] = a[i - 1];
        a[i - 1] = tmp;

        j = a.length - 1;

        while (i < j) {
            tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;

            i += 1;
            j -= 1;
        }

        return true;
    }
}

class Paper {

    private static final int EMPTY = 0;
    private static final int TARGET = 1;
    private static final int ATTACH = 2;

    static boolean checkPaper(int row, int col, int paperSize, int[][] map) {
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
        for (int r = row; r < row + paperSize; r++) {
            for (int c = col; c < col + paperSize; c++) {
                if (map[r][c] == TARGET) {
                    map[r][c] = ATTACH;
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


