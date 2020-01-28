package com.practice.d_main3.f_gerrymandering_17471;

import java.io.*;
import java.util.*;

public class Dongwook {

    private static int N; // 지역구 수
    private static int[] pop; // 지역구 인구 수
    private static ArrayList<Integer>[] adjs; // 지역구마다 인접 지역구

    private static int[] apop; // 이 배열의 index는 비트마스크를 의미한다. 비트마스크에 따른 지역구의 인구수 총합이 값
    private static int[] team1; // team1 에 속한 지역구의 번호
    private static int totalMask; // 모든 지역구가 team1에 속했을 때를 표현한 비트마스크

    private static int answer = -1;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());

        pop = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            pop[i] = s2i(st.nextToken());
        }

        adjs = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjs[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());

            int cnt = s2i(st.nextToken());
            for (int j = 0; j < cnt; j++) {
                adjs[i].add(s2i(st.nextToken()) - 1);
            }
        }

        apop = new int[1 << N];
        team1 = new int[N];

        br.close();
    }

    private static void solve() {

        /**
         * N = 4 인 경우,
         *
         * totalMask = 1111 : 1 << 4 = 10000 -> ( 1 << 4) -1 = 1111 (15)
         *
         */

        totalMask = (1 << N) - 1;

        for (int i = 0; i < N; i++) {
            int teamOneMask = 1 << i;

            apop[teamOneMask] = pop[i];

            calculateDiff(teamOneMask);

            team1[0] = i;

            BF(1, teamOneMask);
        }

        System.out.println(answer);
    }

    private static void BF(int index, int teamOneMask) {

        for (int i = 0; i < index; i++) {
            int district = team1[i];

            for (int j = 0; j < adjs[district].size(); j++) {
                int adj = adjs[district].get(j);

                // Todo : 이미 teamOne에 들어있는 지역구의 경우, 넘어간다.
                if ((teamOneMask & (1 << adj)) != 0) {
                    continue;
                }

                // Todo : adj 지역구를 teamOne에 추가함
                int newMask = (teamOneMask | (1 << adj));

                // Todo : 새로운 teamOne의 인구수를 이미 구했던 경우, 넘어간다.
                if (apop[newMask] != 0) {
                    continue;
                }

                // Todo : 새로운 teamOne의 인구수를 구한다.
                apop[newMask] = apop[teamOneMask] + pop[adj];

                // Todo : 선거구 2개의 인구수 차이를 구한다.
                calculateDiff(newMask);

                team1[index] = adj;
                BF(index + 1, newMask);
            }
        }
    }

    private static void calculateDiff(int teamOneMask) {
        /**
         * 만약 teamOneMask = 0001 (1),
         * 1111 - 0001 = 15 - 1 = 14
         * 따라서, teamZeroMask = 1110 (14)
         *
         * 만약 teamOneMask = 0011 (3),
         * 1111 - 0011 = 15 - 3 = 12
         * 따라서, teamZeroMask = 1100 (12)
         */

        int teamZeroMask = totalMask - teamOneMask;

        if (apop[teamZeroMask] != 0) {
            int diff = Math.abs(apop[teamOneMask] - apop[teamZeroMask]);

            if (answer == -1 || diff < answer) answer = diff;
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

