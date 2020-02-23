package com.practice.e_main4.a_twodemension_17140;

import java.io.*;
import java.util.*;

public class Repeat1 {

    private static final int OP_R = 0;
    private static final int OP_C = 1;

    private static int R, C, K;
    private static int[][] a = new int[100][100];

    private static int maxRow = 3;
    private static int maxCol = 3;
    private static int ans = 0;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = s2i(st.nextToken()) - 1;
        C = s2i(st.nextToken()) - 1;
        K = s2i(st.nextToken());

        for (int r = 0; r < 3; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < 3; c++) {
                a[r][c] = s2i(st.nextToken());
            }
        }

        solve();
    }

    private static void solve() {

        while (true) {

            if (a[R][C] == K) {     // Todo : 정답 찾음
                break;
            }

            if (decideOperation() == OP_R) {
                for (int r = 0; r < maxRow; r++) {
                    calculateOPR(r, maxCol);
                }
            } else if (decideOperation() == OP_C) {
                for (int c = 0; c < maxCol; c++) {
                    calculateOPC(c, maxRow);
                }
            }
            ans += 1;

            if (ans > 100) {        // Todo : 100초 넘어가면 -1 출력
                ans = -1;
                break;
            }
        }

        System.out.println(ans);

    }

    private static void calculateOPR(int index, int last) {
        int[][] count = new int[10][10];

        // Todo : 탐색 중인 1차원 배열에 각 숫자가 몇 개 중복되는지 count 배열에 기록
        for (int i = 0; i < last; i++) {
            if (a[index][i] == 0) continue;
            if (a[index][i] == 100) count[0][0] += 1;   // 100 은 count[0][0]에 기록
            else {
                int ten = a[index][i] / 10;
                int one = a[index][i] % 10;
                count[ten][one] += 1;
            }
        }

        // Todo : count에 기록되어 있는 (숫자, 중복 개수)를 우선순위 큐에 담는다
        PriorityQueue<Pair2> queue = new PriorityQueue<>();
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (count[r][c] == 0) continue;

                // 숫자 = 100
                if (r == 0 && c == 0) {
                    queue.add(new Pair2(100, count[r][c]));
                    continue;
                }

                // 그 외 경우
                queue.add(new Pair2((r * 10 + c), count[r][c]));
            }
        }

        // Todo : maxCol 을 갱신한다.
        if (maxCol < queue.size() * 2) {
            maxCol = queue.size() * 2;
        }

        // Todo : 우선순위 큐를 순서대로 꺼내서 탐색했던 1차원 배열을 갱신한다.
        int idx = 0;
        while (!queue.isEmpty() && idx < 100) {
            Pair2 p = queue.remove();
            a[index][idx++] = p.num;
            a[index][idx++] = p.cnt;
        }

        // Todo : 나머지는 0으로 채운다.
        for (int i = idx; i < last; i++) {
            a[index][i] = 0;
        }
    }

    private static void calculateOPC(int index, int last) {
        int[][] count = new int[10][10];

        for (int i = 0; i < last; i++) {
            if (a[i][index] == 0) continue;
            if (a[i][index] == 100) count[0][0] += 1;
            else {
                int ten = a[i][index] / 10;
                int one = a[i][index] % 10;
                count[ten][one] += 1;
            }
        }

        PriorityQueue<Pair2> queue = new PriorityQueue<>();
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (count[r][c] == 0) continue;

                if (r == 0 && c == 0) {
                    queue.add(new Pair2(100, count[r][c]));
                    continue;
                }

                queue.add(new Pair2((r * 10 + c), count[r][c]));
            }
        }

        if (maxRow < queue.size() * 2) {
            maxRow = queue.size() * 2;
        }

        int idx = 0;
        while (!queue.isEmpty() && idx < 100) {
            Pair2 p = queue.remove();
            a[idx++][index] = p.num;
            a[idx++][index] = p.cnt;
        }

        for (int i = idx; i < last; i++) {
            a[i][index] = 0;
        }

    }

    private static int decideOperation() {
        return maxRow >= maxCol ? OP_R : OP_C;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair2 implements Comparable<Pair2> {
    int num, cnt;

    Pair2(int num, int cnt) {
        this.num = num;
        this.cnt = cnt;
    }

    @Override
    public int compareTo(Pair2 o) {
        if (this.cnt < o.cnt) {
            return -1;
        } else if (this.cnt > o.cnt) {
            return 1;
        } else {
            if (this.num < o.num) {
                return -1;
            } else if (this.num > o.num) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}