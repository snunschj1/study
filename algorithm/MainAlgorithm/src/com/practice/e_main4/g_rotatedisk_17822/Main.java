package com.practice.e_main4.g_rotatedisk_17822;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dd = {-1, 1, 0, 0};
    private static final int[] di = {0, 0, -1, 1};
    
    private static final int DELETE = -1;
    private static final int CLOCK = 0;
    private static final int COUNTER = 1;

    private static int N, M, T;

    private static int[][] disks;

    private static ArrayDeque<Rotate> rotates = new ArrayDeque<>();
    
    private static int sum = 0;
    private static int cnt;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = s2i(st.nextToken());
        M = s2i(st.nextToken());
        T = s2i(st.nextToken());

        cnt = N * M;
        
        disks = new int[N+1][M+1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= M; j++) {
                disks[i][j] = s2i(st.nextToken());
                sum += disks[i][j];
            }
        }

        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());

            rotates.addLast(new Rotate(s2i(st.nextToken()), s2i(st.nextToken()), s2i(st.nextToken())));
        }
    }

    private static void solve() {
        int cnt = 1;

        while (T-- > 0) {
            rotateDisk();
            findSameNumber();
        }

        System.out.println(sum);
    }

    private static void rotateDisk() {
        Rotate r = rotates.removeFirst();
        int x = r.x;
        int d = r.d;
        int k = r.k;

        for (int i = 1; i <= N; i++) {
            if (i % x != 0) continue;

            int[] tmp = new int[M+1];

            int nj;

            if (d == CLOCK) {
                for (int j = 1; j <= M; j++) {
                    nj = (j + M - k) % M;
                    if (nj == 0) nj = M;
                    tmp[j] = disks[i][nj];
                }
                disks[i] = tmp;
            } else /* if ( d == COUNTER) */ {
                for (int j = 1; j <= M; j++) {
                    nj = (j + k) % M;
                    if (nj == 0) nj = M;
                    tmp[j] = disks[i][nj];
                }
                disks[i] = tmp;
            }
        }
    }

    private static void findSameNumber() {
        int[][] visit = new int[N+1][M+1];
        boolean flag = false;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (visit[i][j] == 0 && disks[i][j] != -1) {
                    boolean tmp = bfs(i, j, visit);

                    if (!flag) {
                        flag = tmp;
                    }
                }
            }
        }

        if (!flag) {
            int average = sum / cnt;

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    if (disks[i][j] == -1) continue;

                    if (disks[i][j] > average) {
                        disks[i][j] -= 1;
                        sum -= 1;
                    } else if (disks[i][j] < average) {
                        disks[i][j] += 1;
                        sum += 1;
                    }
                }
            }
        }
    }

    private static boolean bfs(int disk, int index, int[][] visit) {
        int num = disks[disk][index];
        boolean found = false;

        Queue<Integer> q = new LinkedList<>();
        q.add(disk);
        q.add(index);
        visit[disk][index] = 1;

        while (!q.isEmpty()) {
            int d = q.remove();
            int i = q.remove();

            int nd;
            int ni;

            for (int k = 0; k < 4; k++) {
                nd = d + dd[k];
                ni = i + di[k];

                if (nd == 0) continue;
                if (nd == N+1) continue;

                if (ni == 0) ni = M;
                if (ni == M+1) ni = 1;

                if (visit[nd][ni] == 1) continue;

                if (disks[nd][ni] == num) {
                    q.add(nd);
                    q.add(ni);
                    visit[nd][ni] = 1;
                    disks[nd][ni] = -1;
                    sum -= num;
                    cnt -= 1;
                    found = true;
                }
            }
        }

        if (found) {
            disks[disk][index] = -1;
            sum -= num;
            cnt -= 1;
        }

        return found;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Rotate {
    int x, d, k;

    Rotate (int x, int d, int k) {
        this.x = x;
        this.d = d;
        this.k = k;
    }
}
