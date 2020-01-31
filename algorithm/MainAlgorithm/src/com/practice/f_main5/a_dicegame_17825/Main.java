package com.practice.f_main5.a_dicegame_17825;

import java.util.*;
import java.io.*;

public class Main {

    private static final int ARRIVE = -1;

    private static boolean[] visit = new boolean[41];

    private static int[][] pieces = new int[5][2];
    private static int[] diceResult = new int[10];

    private static int answer = 0;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < 10; i++) {
            diceResult[i] = s2i(st.nextToken());
        }
        br.close();
    }

    private static void solve() {
        go(0, 0, 0);
        Test.print();
        System.out.println(answer);
    }

    private static void go(int piece, int cnt, int sum) {

        if (cnt >= 10) {
            Test.write(diceResult);

            if (answer < sum) answer = sum;
            return;
        }

        for (int p = 1; p <= piece + 1 && p <= 4; p++) {
            // Todo : 4개의 말 중에서 하나 선택 (말은 시작에서 게임판으로 나와야 다른 말과 구별이 의미 있어짐)

            // Todo : 이미 도착한 말은 제외
            int path = pieces[p][0];
            int idx = pieces[p][1];
            if (idx == ARRIVE) continue;

            // Todo : 새로운 말이면 piece + 1;
            int nPiece = piece;
            if (p == piece + 1) {
                nPiece = piece + 1;
            }

            // Todo : 선택한 말의 다음 위치 구하기
            int nPath = path;
            int nIdx = idx + diceResult[cnt];

            if (nPath == 0) {
                // Todo : 윷판의 외곽 경로로 가고 있던 경우
                if (0 < nIdx && nIdx < 20 && nIdx % 5 == 0) {
                    // Todo : 파란원을 만나는 경우
                    nPath = nIdx / 5;
                    nIdx = 0;
                }
            }

            if (nIdx >= Map.getPathDest(nPath)) {

                // Todo : 도착지에 도착한 경우
                pieces[p][0] = nPath;
                pieces[p][1] = ARRIVE;
                visit[Map.getPathIdx(path, idx)] = false;
                Test.record(cnt, p, nPath, ARRIVE, 0, sum);
                go(nPiece, cnt + 1, sum);
                visit[Map.getPathIdx(path, idx)] = true;
                pieces[p][0] = path;
                pieces[p][1] = idx;

            } else if (!checkExistPiece(Map.getPathIdx(nPath, nIdx))) {

                // Todo : 도착지에 도착하지 않은 경우
                pieces[p][0] = nPath;
                pieces[p][1] = nIdx;
                visit[Map.getPathIdx(nPath, nIdx)] = true;
                visit[Map.getPathIdx(path, idx)] = false;
                Test.record(cnt, p, nPath, nIdx, Map.getPathIdx(nPath, nIdx), sum + Map.getPathIdx(nPath, nIdx));
                go(nPiece, cnt + 1, sum + Map.getPathIdx(nPath, nIdx));
                pieces[p][0] = path;
                pieces[p][1] = idx;
                visit[Map.getPathIdx(nPath, nIdx)] = false;
                visit[Map.getPathIdx(path, idx)] = true;
            }
        }
    }

    private static boolean checkExistPiece(int score) {
        if (visit[score]) {
            return true;
        }
        return false;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Map {

    static final int[] destinationIdx = {21, 8, 7, 8};

    static final int[] path0 = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40};
    static final int[] path1 = {10, 13, 16, 19, 25, 30, 35, 40};
    static final int[] path2 = {20, 22, 24, 25, 30, 35, 40};
    static final int[] path3 = {30, 28, 27, 26, 25, 30, 35, 40};

    static int getPathIdx(int path, int idx) {
        if (path == 0) return path0[idx];
        else if (path == 1) return path1[idx];
        else if (path == 2) return path2[idx];
        else /*if (path == 3) */ return path3[idx];

    }

    static int getPathDest(int path) {
        return destinationIdx[path];
    }
}

class Test {
    static BufferedOutputStream bs;
    static int[][] test = new int[10][5];

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

    static void write(int[] diceResult) {
        for (int i = 0; i < 10; i++) {
            int move = diceResult[i];
            int tp = test[i][0];
            int tPath = test[i][1];
            int tIdx = test[i][2];
            int tCur = test[i][3];
            int tSum = test[i][4];

            String str = String.format("%d 번째 : move = %d, 말 = %d, 경로 = %d, 위치 = %d, 현재 얻은 점수 = %d, 현재 총점 = %d\n", i+1, move, tp, tPath, tIdx, tCur, tSum);
            try {
                bs.write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void record(int cnt, int p, int path, int idx, int cur, int sum) {
        test[cnt][0] = p;
        test[cnt][1] = path;
        test[cnt][2] = idx;
        test[cnt][3] = cur;
        test[cnt][4] = sum;
    }


}


