package com.practice.d_main3.f_gerrymandering_17471;

import java.util.*;
import java.io.*;

public class Main {

    private static int N;

    private static LinkedList<Integer>[] list;

    private static int[] pop;
    private static int[] visit;

    private static boolean flag = false;

    private static int answer = 1000;
    private static int sum = 0;

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
        visit = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            list[i] = new LinkedList<>();
            pop[i] = s2i(st.nextToken());
            sum += pop[i];
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
        visit[0] = 1;
        dfs1(0, 1, true);
        dfs1(0, 1, false);

        if (flag) {
            System.out.println(answer);
        } else {
            System.out.println("-1");
        }
    }

    private static void dfs1(int n, int cnt, boolean isFinished) {
        if (isFinished) {
            int m = findNonVisitDistrict();
            if (m != -1) {
                if (dfs2(m, N - cnt)) {
                    System.out.println("FIND!");
                    int ans = calculateAnswer();

                    if (answer > ans) {
                        answer = ans;
                        flag = true;
                    }
                }
            }
            return;
        }

        for (int i = 0; i < list[n].size(); i++) {
            int nn = list[n].get(i);

            if (visit[nn] == 0) {
                visit[nn] = 1;
                dfs1(nn, cnt + 1, true);
                if (cnt + 1 < N) {
                    dfs1(nn, cnt + 1, false);
                }
                visit[nn] = 0;
            }
        }
    }

    private static boolean dfs2(int m, int remainCnt) {

        int[] tmp = copyArr(visit);
        int tmpCnt = 0;

        Queue<Integer> q = new LinkedList<>();
        q.add(m);
        tmp[m] = 1;
        tmpCnt += 1;

        while(!q.isEmpty()) {
            int n = q.remove();

            for (int i = 0; i < list[n].size(); i++) {
                int nn = list[n].get(i);

                if (tmp[nn] == 0) {
                    q.add(nn);
                    tmp[nn] = 1;
                    tmpCnt += 1;
                }
            }
        }

        if (tmpCnt == remainCnt) {
            return true;
        } else {
            return false;
        }

    }

    private static int calculateAnswer() {
        int tmp = 0;

        System.out.println("START");
        for (int i = 0; i < N; i++) {
            System.out.println("checking : i = " + i);
            if (visit[i] == 1) {
                tmp += pop[i];
                System.out.println("i = " + i + ", pop[i] = " + pop[i]);
            }
        }
        System.out.println("tmp = " + tmp);

        return Math.abs(sum - 2 * tmp);
    }

    private static int findNonVisitDistrict() {
        int n = -1;
        for (int i = 0; i < N; i++) {
            if (visit[i] == 0) {
                n = i;
            }
        }
        return n;
    }

    private static int[] copyArr(int[] o) {
        int[] tmp = new int[o.length];
        for (int i = 0; i < o.length; i++) {
            tmp[i] = o[i];
        }
        return tmp;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
