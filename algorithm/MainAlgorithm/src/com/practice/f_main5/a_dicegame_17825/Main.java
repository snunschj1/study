package com.practice.f_main5.a_dicegame_17825;

import java.util.*;
import java.io.*;

public class Main {

    private static BufferedOutputStream bs;

    static {
        try {
            bs = new BufferedOutputStream(new FileOutputStream("/Users/hyunjun/Documents/output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final int ARRIVE = -1;

    private static final ArrayList<int[]> map = new ArrayList<>();
    private static int[][] pieces = new int[5][2];
    private static int[] diceResult = new int[10];

    private static int answer = 0;

    private static int[][] test = new int[10][5];

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        prepareMapInfo();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < 10; i++) {
            diceResult[i] = s2i(st.nextToken());
        }

        br.close();
    }

    private static void prepareMapInfo() {
        map.add(new int[]{0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40});
        map.add(new int[]{10, 13, 16, 19, 25, 30, 35, 40});
        map.add(new int[]{20, 22, 24, 25, 30, 35, 40});
        map.add(new int[]{30, 28, 27, 26, 25, 30, 35, 40});
    }

    private static void solve() {
        go(0, 0, 0);
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
        System.out.println(answer);
    }

    private static void go(int piece, int cnt, int sum) {

        if (cnt >= 10) {

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
//                System.out.printf("%d 번째 : move = %d, 말 = %d, 경로 = %d, 위치 = %d, 현재 얻은 점수 = %d, 현재 총점 = %d\n", i+1, move, tp, tPath, tIdx, tCur, tSum);
            }
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
                if (nIdx != 20 && nIdx % 5 == 0) {
                    // Todo : 파란원을 만나는 경우
                    nPath = nIdx / 5;
                    nIdx = 0;
                }
            }

            if (nIdx >= map.get(nPath).length) {

                // Todo : 도착지에 도착한 경우
                pieces[p][0] = nPath;
                pieces[p][1] = ARRIVE;

                test(cnt, p, nPath, ARRIVE, 0, sum);
                go(nPiece, cnt + 1, sum);
                pieces[p][0] = path;
                pieces[p][1] = idx;

            } else if (!checkExistPiece(p, nPiece, nPath, nIdx)) {

                // Todo : 도착지에 도착하지 않은 경우
                pieces[p][0] = nPath;
                pieces[p][1] = nIdx;

                test(cnt, p, nPath, nIdx, map.get(nPath)[nIdx], sum + + map.get(nPath)[nIdx]);
                go(nPiece, cnt + 1, sum + map.get(nPath)[nIdx]);
                pieces[p][0] = path;
                pieces[p][1] = idx;
            }
        }
    }

    private static boolean checkExistPiece(int piece, int pieceCnt, int path, int idx) {
        for (int i = 1; i <= pieceCnt; i++) {
            // Todo : 이동시켜려는 위치에 다른 말이 있는지 유무
            if (piece == i) continue;

            if (pieces[i][0] == path && pieces[i][1] == idx) {
                return true;
            }
        }
        return false;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }

    private static void test(int cnt, int p, int path, int idx, int cur, int sum) {
        test[cnt][0] = p;
        test[cnt][1] = path;
        test[cnt][2] = idx;
        test[cnt][3] = cur;
        test[cnt][4] = sum;
    }
}

