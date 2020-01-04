package com.practice.c_main2.c_ladder_15684;

import java.util.*;
import java.io.*;

public class Main {

    private static final int EXIST = 1;
    private static final int NON_EXIST = 0;

    private static int row;
    private static int col;

    private static int numberOfInitLadder;

    private static ArrayList<int[]> map;
    private static int[][] ladders;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        col = s2i(st.nextToken());
        numberOfInitLadder = s2i(st.nextToken());
        row = s2i(st.nextToken());

        map = new ArrayList<>();
        for (int i = 0; i < col; i++) {
            map.add(new int[row]);
            Arrays.fill(map.get(i), -1);
        }

        ladders = new int[row][col - 1];

        for (int i = 0; i < numberOfInitLadder; i++) {
            st = new StringTokenizer(br.readLine());

            int r = s2i(st.nextToken()) - 1;
            int c = s2i(st.nextToken()) - 1;

            ladders[r][c] = EXIST;

            map.get(c)[r] = c + 1;
            map.get(c+1)[r] = c;
        }
    }

    private static void solve() {
        for (int i = 0; true; i++) {
            if (i == 4) {
                System.out.println("-1");
                break;
            } else if (addLine(i, 0)) {
                System.out.println(i);
                break;
            }
        }
    }

    private static boolean addLine(int cntLine, int index) {

        if (cntLine == 0) {
            return check();
        }

        for (int idx = index; idx < row * (col-1); idx++) {
            int i = idx / (col - 1);
            int j = idx % (col - 1);

//            System.out.printf("index = %d, i = %d, j = %d\n", idx, i , j);

            if (ladders[i][j] == NON_EXIST) {
                if (1 <= j && j < col && ladders[i][j-1] == EXIST) {
                    continue;
                }

                if (j < col - 2 && ladders[i][j + 1] == EXIST) {
                    continue;
                }

                ladders[i][j] = EXIST;
                map.get(j)[i] = j + 1;
                map.get(j+1)[i] = j;

                int nd = i * (col - 1) + j + 1;

                if(addLine(--cntLine, nd)) {
                    return true;
                }
                ++cntLine;
                ladders[i][j] = NON_EXIST;
                map.get(j)[i] = -1;
                map.get(j+1)[i] = -1;

            }
        }

        return false;
    }

    private static boolean check() {
//        print();

        for (int i = 0; i < col; i++) {
            if (i != goDown(i, -1)) {
                return false;
            }
        }
        return true;
    }

//    private static void print() {
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < col - 1; j++) {
//                System.out.print(ladders[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }

    private static int goDown(int curCol, int curRow) {
        if (curRow == row) {
            return curCol;
        }

        int[] relations = map.get(curCol);
        for (int i = curRow + 1; i < row; i++) {
            if (relations[i] != -1) {
                return goDown(relations[i], i);
            }
        }
        return goDown(curCol, row);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
