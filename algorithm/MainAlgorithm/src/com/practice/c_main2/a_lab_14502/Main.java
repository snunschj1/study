package com.practice.c_main2.a_lab_14502;

import java.util.*;
import java.io.*;

public class Main {

    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n;
        int m;

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // Todo : 1 선택 0 / 선택 X
        // n*m 개의 좌표에서 3개 고르기 위함
        int[] a = new int[n*m];
        for (int i = 0; i < 3; i++) {
            a[i] = 1;
        }

        int ans = 0;
        boolean isBuildingWallsCompleted = true;

        do {
            // Todo : 새로운 벽 3개 세우기
            for (int i = 0; i < 3; i++) {
                int mx = a[i] / m;
                int my = a[i] % m;

                /** 해당 좌표가 바이러스(2) 이거나 기존 벽(1) 인 경우, 반복문 넘어가야 한다. **/
                if (map[mx][my] != 0) {
                    isBuildingWallsCompleted = false;
                    break;
                }

                map[mx][my] = 1;
            }

            if (!isBuildingWallsCompleted) {
                isBuildingWallsCompleted = true;
                continue;
            }

            Queue<Pair> q = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (map[i][j] == 2) {
                        q.add(new Pair(i, j));
                    }
                }
            }

            // Todo : 바이러스 퍼뜨리기 - BFS
            while(!q.isEmpty()) {
                Pair p = q.remove();
                int x = p.x;
                int y = p.y;

                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if (0 > nx || nx >= n || 0 > ny || ny >= m) {
                        continue;
                    }

                    if (map[nx][ny] == 0) {
                        q.add(new Pair(nx, ny));
                        map[nx][ny] = 2;
                    }
                }
            }

            // Todo : 안전 영역(0) 크기
            int tmp = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (map[i][j] == 0) {
                        tmp += 1;
                    }
                }
            }

            // Todo : 안전 영역(0) 최대 크기
            if (ans < tmp) {
                ans = tmp;
            }

        } while (prev_permutation(a));

        System.out.println(ans);
    }


    private static boolean prev_permutation(int[] a) {
        int i = a.length -1;

        while (i > 0 && a[i] >= a[i-1]) {
            i -= 1;
        }

        if (i <= 0) {
            return false;
        }

        int j = a.length - 1;

        while (a[j] >= a[i-1]) {
            j -= 1;
        }

        int tmp = a[i-1];
        a[i-1] = a[j];
        a[j] = tmp;

        j = a.length - 1;

        while (i < j) {
            tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;

            i += 1;
            j -= 1;
        }

        return true;
    }
}

class Pair {
    int x;
    int y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}