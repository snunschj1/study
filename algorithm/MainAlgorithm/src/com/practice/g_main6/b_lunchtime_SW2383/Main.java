package com.practice.g_main6.b_lunchtime_SW2383;

import java.util.*;
import java.io.*;

public class Main {

    private static final int PEOPLE = 1;

    private static int T;

    private static int N;
    private static int[][] map;

    private static Plot[] people;
    private static int peopleCnt;

    private static Plot[] doors;
    private static int doorCnt;

    private static int answer;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = s2i(br.readLine());

        for (int t = 1; t <= T; t++) {
            people = new Plot[10];
            peopleCnt = 0;
            doors = new Plot[2];
            doorCnt = 0;
            answer = Integer.MAX_VALUE;


            N = s2i(br.readLine());

            map = new int[N][N];

            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < N; c++) {
                    map[r][c] = s2i(st.nextToken());

                    if (map[r][c] == PEOPLE) {
                        people[peopleCnt++] = new Plot(r, c);
                    } else if (map[r][c] >= 2) {
                        doors[doorCnt++] = new Plot(r, c, map[r][c]);
                    }
                }
            }

            solve(t);
        }
    }

    private static void solve(int t) {
        int[] combination = new int[peopleCnt];
        combination(0, 0, combination);
        System.out.println("#" + t + " " + answer);
    }

    private static void combination(int index, int cnt, int[] c) {

        if (cnt >= peopleCnt) {
            divideTeam(c);
            return;
        }

        c[index] = 1;
        combination(index + 1, cnt + 1, c);
        c[index] = 0;
        combination(index + 1, cnt + 1, c);
    }

    private static void divideTeam(int[] c) {
        ArrayList<Integer>[] teams = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            teams[i] = new ArrayList<>();
        }

        for (int i = 0; i < peopleCnt; i++) {
            // 조합에 따라 해당 사람이 특정 계단에 도달하는 시간 + 1 (내려가기 시작하는 시간)을 리스트에 담는다
            if (c[i] == 0) {
                teams[0].add(calculateTime(people[i], doors[0]) + 1);
            } else if (c[i] == 1) {
                teams[1].add(calculateTime(people[i], doors[1]) + 1);
            }
        }

        int max = 0;

        for (int i = 0; i < 2; i++) {   // 먼저 도달하는 순으로 정렬
           Collections.sort(teams[i]);
        }


        for (int i = 0; i < 2; i++) {
            int tmp = downStair(teams[i], doors[i].k);
            if (max < tmp) {
                max = tmp;
            }
        }

        if (answer > max) {
            answer = max;
        }
    }

    private static int downStair(ArrayList<Integer> team, int k) { // 해당 계단을 모두 내려가는데 걸리는 시간을 구한다.

        Queue<Integer> q = new LinkedList<>();  // 계단에 내려가는 중인 사람을 담음
        int finishTime = 0;

        int index = 0;

        while (index < team.size()) {   // 팀에 들어있는 모든 사람을 하나씩 꺼낸다.

            if (q.size() < 3) {     // 큐에 3명 미만으로 있는 경우는 큐에 추가한다.

                if (finishTime < team.get(index)) {     // 현재 시각 < 계단 내려가기 시작하는 시각
                    q.add(team.get(index) + k);     // 계단 내려가기 시작하는 시각 + 계단 다 내려가는 시간
                } else {                                // 현재 시각 > 계단 내려가기 시작하는 시각
                    q.add(finishTime + k);          // 현재 시각 + 계단 다 내려가는 시간
                }

                index++;

            } else {
                // 큐에 3명 이상 있는 경우 -> index 변화주지 않는다 (현재 시각과 큐에 담겨 있는 사람만 변화주고, 해당 사람은 다시 반복문 거친다)

                if (q.peek() < team.get(index)) {       // 최선두 사람이 계단 다 내려가는 시각 < 계단 내려가기 시작하는 시각
                    finishTime = q.remove();            // 현재 시각을 최선두 사람이 계단 다 내려가는 시각으로 바꾸고, 최선두 사람 remove

                } else {                                // 최선두 사람이 계단 다 내려가는 시각 > 계단 내려가기 시작하는 시각
                    int tmp = q.peek();

                    while (!q.isEmpty() && q.peek() == tmp) {   // 최선두 사람과 똑같은 시각에 계단 다 내려오는 사람들
                        finishTime = q.remove();                // 모두 제거하고, 현재 시각을 그 시각으로 바꿈
                    }
                }
            }
        }

        while(!q.isEmpty()) {
            finishTime = q.remove();
        }

        return finishTime;
    }

    private static int calculateTime(Plot people, Plot stair) {
        // Todo : 사람이 해당 계단에 도달하는데 걸리는 시간 계산
        return Math.abs(people.r - stair.r) + Math.abs(people.c - stair.c);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Plot {
    int r, c, k;

    Plot(int r, int c) {
        this(r, c, 0);
    }

    Plot(int r, int c, int k) {
        this.r = r;
        this.c = c;
        this.k = k;
    }
}