package com.practice.a_pre.h_tetromino_14500;

import java.util.*;
import java.io.*;

public class Main2 {

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    static int n;
    static int m;

    static int[][] a;
    static int[][] c;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());



        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        a = new int[n][m];
        c = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                a[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, dfs(i, j, 1));

                /**
                 * ㅁㅁㅁ
                 *  ㅁ
                 */

                int tmp;
                if (i +1 < n && j+2 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i][j+k];
                    }
                    tmp += a[i+1][j+1];

                    if (ans < tmp) {
                        ans = tmp;
                    }

                }

                /**
                 *  ㅁ
                 * ㅁㅁ
                 *  ㅁ
                 */

                if (i + 2 < n && j+1 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+k][j+1];
                    }
                    tmp += a[i+1][j];

                    if (ans < tmp) {
                        ans = tmp;
                    }

                }

                /**
                 *  ㅁ
                 * ㅁㅁㅁ
                 */

                if (i +1 < n && j+2 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+1][j+k];
                    }
                    tmp += a[i][j+1];

                    if (ans < tmp) {
                        ans = tmp;
                    }

                }

                /**
                 * ㅁ
                 * ㅁㅁ
                 * ㅁ
                 */

                if (i + 2 < n && j+1 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+k][j];
                    }
                    tmp += a[i+1][j+1];

                    if (ans < tmp) {
                        ans = tmp;
                    }
                }
            }
        }

        System.out.println(ans);

    }

    static int dfs(int x, int y, int cnt) {
        if (cnt == 4) {
            return a[x][y];
        }

        c[x][y] = 1;

        int tmp = 0;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (0 > nx || nx >= n || 0 > ny || ny >= m) {
                continue;
            }

            if (c[nx][ny] == 1) {
                continue;
            }

            tmp = Math.max(tmp, a[x][y] + dfs(nx, ny, cnt + 1));

        }

        c[x][y] = 0;

        return tmp;
    }
}
