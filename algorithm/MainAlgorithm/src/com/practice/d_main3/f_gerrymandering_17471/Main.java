package com.practice.d_main3.f_gerrymandering_17471;

import java.util.*;
import java.io.*;

public class Main {

    private static final int TEAM1 = 1;
    private static final int TEAM0 = 0;

    private static final int VISITED = 1;
    private static final int NON_VISITED = 0;

    private static int N;
    private static LinkedList<Integer>[] list;

    private static int[] pop;

    private static boolean flag = false;
    private static int answer = 1000;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());

        list = new LinkedList[N];

        pop = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            list[i] = new LinkedList<>();
            pop[i] = s2i(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int k = s2i(st.nextToken());

            while (k-- > 0) {
                list[i].add(s2i(st.nextToken()) - 1);
            }
        }

        br.close();
    }

    private static void solve() {
        for (int cnt = 1; cnt < N; cnt++) {
            int[] vertexes = new int[N];
            // Todo : 정점 조합을 구한다.
            selectVertexes(0, 0, cnt, vertexes);
        }

        if (flag) {
            System.out.println(answer);
        } else {
            System.out.println("-1");
        }
    }

    private static void selectVertexes(int index, int cnt, int target, int[] vertexes) {

        if (cnt == target) {

            // Todo : TEAM1, TEAM0 으로 나눠진 정점들이 각각 서로 인접하는지 확인한다.
            int popOne = checkTeam(vertexes, cnt, TEAM1);
            if (popOne != -1) {
                int popZero = checkTeam(vertexes, N-cnt, TEAM0);

                if (popZero != -1) {
                    int result = Math.abs(popOne - popZero);

                    if (!flag) {
                        flag = true;
                    }

                    if (answer > result) {
                        answer = result;
                    }
                }
            }
            return;

        } else {
            if (index >= N) {
                return;
            }
        }

        // Todo : 정점들을 팀 0 / 팀 1 로 나눈다.
        vertexes[index] = TEAM1;
        selectVertexes(index + 1, cnt + 1, target, vertexes);
        vertexes[index] = TEAM0;
        selectVertexes(index + 1, cnt, target, vertexes);
    }

    private static int checkTeam(int[] vertexes, int cnt, int team) {
        int[] v = new int[N];

        int startIndex = 0;

        for (int i = 0; i < N; i++) {
            if (vertexes[i] == team) {
                startIndex = i;
                break;
            }
        }

        // Todo : 해당 팀으로 정해진 정점들이 인접한지 BFS로 확인한다.
        Queue<Integer> q = new LinkedList<>();
        q.add(startIndex);
        v[startIndex] = VISITED;

        int population = pop[startIndex];
        int count = cnt - 1;

        while(!q.isEmpty()) {
            int i = q.remove();

            for (int j = 0; j < list[i].size(); j++) {
                int next = list[i].get(j);

                if (v[next] == NON_VISITED) {
                    if (vertexes[next] == team) {
                        v[next] = VISITED;
                        q.add(next);
                        population += pop[next];
                        --count;
                    }
                }
            }
        }
        
        if (count == 0) {
            return population;
        } else {
            return -1;
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
