package com.practice.f_main5.e_2048easy_12100;

import java.util.*;
import java.io.*;

public class Main {

    // 0 : 위 1 : 아래 2 : 오른쪽 3 : 왼쪽
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, 1, -1};

    private static int N;
    private static int[][] map;
    private static int[][] tMap;
    private static int[][] unite;

    static int[] dirs = new int[5];

    private static int defaultMax = 0;

    private static int answer = 0;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());

        map = new int[N][N];
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = s2i(st.nextToken());

                if (map[r][c] != 0 && defaultMax < map[r][c]) {
                    defaultMax = map[r][c];
                }
            }
        }

        br.close();
    }

    private static void solve() {
        decideDirections(0);
        System.out.println(answer);
    }

    private static void decideDirections(int cnt) {
        if (cnt >= 5) {

            // Todo : test
            Test.printStr("==== START ====\n");
            Test.printMap(map, N);

            adaptDirections();
            if (answer < defaultMax) {

                // Todo : test
                Test.printStr("MAX = " + defaultMax + "\n");
                answer = defaultMax;
            }
            return;
        }

        for (int d = 0; d < 4; d++) {
            dirs[cnt] = d;
            decideDirections(cnt + 1);
        }
    }

    private static void adaptDirections() {
        tMap = copyMap(N);

        for (int d = 0; d < 5; d++) {
            unite = new int[N][N];
            moveBlocks(dirs[d]);

            // Todo : test
            Test.printStr((d + 1) + "번 움직임 : ");
            Test.printDir(dirs[d]);
            Test.printMap(tMap, N);
        }
    }

    private static void moveBlocks(int dir) {
        int s;
        int e;

        if (dir == UP || dir == DOWN) {

            if (dir == UP) {
                s = 1;
                e = N - 1;
                moveBlocksUP(s, e, dir);
            } else /* if (dir == DOWN) */ {
                s = N - 2;
                e = 0;
                moveBlocksDOWN(s, e, dir);
            }

        } else /* if (dir == RIGHT || dir == LEFT) */ {

            if (dir == RIGHT) {
                s = N - 2;
                e = 0;
                moveBlocksRIGHT(s, e, dir);
            } else /* if (dir == LEFT) */ {
                s = 1;
                e = N - 1;
                moveBlocksLEFT(s, e, dir);
            }

        }
    }

    private static void moveBlocksUP(int start, int end, int dir) {
        for (int c = 0; c < N; c++) {
            for (int r = start; r <= end; r -= dr[dir]) {
                if (tMap[r][c] == 0) continue;
                moveBlock(r, c, dir);
            }
        }
    }

    private static void moveBlocksDOWN(int start, int end, int dir) {
        for (int c = 0; c < N; c++) {
            for (int r = start; r >= end; r -= dr[dir]) {
                if (tMap[r][c] == 0) continue;
                moveBlock(r, c, dir);
            }
        }
    }

    private static void moveBlocksRIGHT(int start, int end, int dir) {

        for (int r = 0; r < N; r++) {
            for (int c = start; c >= end; c -= dc[dir]) {
                if (tMap[r][c] == 0) continue;
                moveBlock(r, c, dir);
            }
        }
    }

    private static void moveBlocksLEFT(int start, int end, int dir) {

        for (int r = 0; r < N; r++) {
            for (int c = start; c <= end; c -= dc[dir]) {
                if (tMap[r][c] == 0) continue;
                moveBlock(r, c, dir);
            }
        }
    }

    private static void moveBlock(int r, int c, int dir) {
        int cr = r;
        int cc = c;
        int mr = r + dr[dir];
        int mc = c + dc[dir];

        while (check(mr, mc)) {
            if (tMap[mr][mc] == 0) {
                swap(cr, cc, mr, mc);
            } else if (tMap[mr][mc] != tMap[cr][cc]) {
                break;
            } else if (tMap[mr][mc] == tMap[cr][cc]) {
                if (unite[cr][cc] == 1 || unite[mr][mc] == 1) {
                    break;
                } else {
                    int uniteNum = tMap[mr][mc] + tMap[cr][cc];
                    tMap[mr][mc] = uniteNum;
                    if (tMap[mr][mc] > defaultMax) defaultMax = tMap[mr][mc];
                    tMap[cr][cc] = 0;
                    unite[mr][mc] = 1;
                }
            }

            cr += dr[dir];
            cc += dc[dir];
            mr += dr[dir];
            mc += dc[dir];
        }
    }


    private static boolean check(int r, int c) {
        if (0 <= r && r < N && 0 <= c && c < N) {
            return true;
        }
        return false;
    }

    private static void swap(int cr, int cc, int mr, int mc) {
        int tmp = tMap[cr][cc];
        tMap[cr][cc] = tMap[mr][mc];
        tMap[mr][mc] = tmp;

        int tmpU = unite[cr][cc];
        unite[cr][cc] = unite[mr][mc];
        unite[mr][mc] = tmpU;
    }


    private static int[][] copyMap(int size) {
        int[][] tmp = new int[size][size];

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                tmp[r][c] = map[r][c];
            }
        }
        return tmp;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Test {

    static void flush() {
        Print.print();
    }

    static void printStr(String str) {
        Print.write(str);
    }

    static void printDir(int dir) {

        String str;

        if (dir == 0) {
            str = "UP";
        } else if (dir == 1) {
            str = "DOWN";
        } else if (dir == 2) {
            str = "RIGHT";
        } else {
            str = "LEFT";
        }

        str += "\n";

        Print.write(str);
    }

    static void printMap(int[][] map, int size) {
        StringBuilder str = new StringBuilder();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                String tmp = String.format("%4d ", map[r][c]);
                str.append(tmp);
            }
            str.append("\n");
        }
        str.append("\n");

        Print.write(str.toString());
    }
}

class Print {
    static BufferedOutputStream bs;

    static {
        try {
            bs = new BufferedOutputStream(new FileOutputStream("/Users/hyunjun/Documents/output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void print() {
        try {
            bs.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void write(String str) {
        try {
            bs.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

