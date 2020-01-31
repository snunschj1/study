package com.practice.f_main5.a_dicegame_17825;

import java.util.*;
import java.io.*;

public class Main {

    private static final int ARRIVE = -1;

    private static final ArrayList<int[]> map = new ArrayList<>();
    private static int[][] pieces = new int[5][2];
    private static int[] diceResult = new int[10];

    private static int answer = 0;

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
        System.out.println(answer);
    }

    private static void go(int piece, int cnt, int sum) {

        if (cnt >= 10) {
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
                go(nPiece, cnt + 1, sum);
                pieces[p][0] = path;
                pieces[p][1] = idx;

            } else if (!checkExistPiece(p, nPiece, nPath, nIdx)) {

                // Todo : 도착지에 도착하지 않은 경우
                pieces[p][0] = nPath;
                pieces[p][1] = nIdx;
                go(nPiece, cnt + 1, sum + map.get(nPath)[nIdx]);
                pieces[p][0] = path;
                pieces[p][1] = idx;
            }
        }
    }

    private static boolean checkExistPiece(int piece, int pieceCnt, int path, int idx) {
        // Todo : 이동시켜려는 위치에 다른 말이 있는지 유무
        for (int i = 1; i <= pieceCnt; i++) {

            // Todo : 비교하려는 말이 1) 현재 말이거나 2) 이미 도착지에 도달한 경우는 제외
            if (piece == i || pieces[i][1] == ARRIVE) continue;

            if (pieces[i][0] == path && pieces[i][1] == idx) {
                // Todo : 비교하려는 말과 현재 말이 같은 위치에 있는 경우
                return true;

            } else if (idx == map.get(path).length - 1) {
                // Todo : 40점 위치는 따로 고려해야 한다.
                if (pieces[i][1] == map.get(pieces[i][0]).length - 1) {
                    // Todo : 현재 말과 비교하려는 말의 경로의 idx가 경로 배열의 끝 index이면 둘 다 40점에 위치
                    return true;
                }
            }
        }
        return false;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}


