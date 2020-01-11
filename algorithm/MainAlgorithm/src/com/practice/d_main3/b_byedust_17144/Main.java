package com.practice.d_main3.b_byedust_17144;

import java.util.*;
import java.io.*;

public class Main {

    // 동, 북, 서, 남
    private static final int[] dr = {0, -1, 0, 1};
    private static final int[] dc = {1, 0, -1, 0};

    private static int R, C, T;

    private static int[][] a;

    private static int r1 = -1, r2 = -1;


    public static void main(String[] args) throws Exception {

        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = s2i(st.nextToken());
        C = s2i(st.nextToken());
        T = s2i(st.nextToken());

        a = new int[R][C];

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < C; j++) {
                int value = s2i(st.nextToken());

                if (value == -1) {
                    if (r1 == -1) r1 = i;
                    else r2 = i;
                }

                a[i][j] = value;
            }
        }
    }

    private static void solve() {
        for (int t = 0; t < T; t++) {
            diffuse();
            go1(r1, 0, -1);
            go2(r2, 0, -1);
        }

        int result = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (a[i][j] == 0) {
                    continue;
                }

                if (a[i][j] == -1) {
                    continue;
                }

                result += a[i][j];
            }
        }

        System.out.println(result);
    }

    private static void diffuse() {
        // Todo : 확산을 위해 방문하는 좌표의 순서가 확산되는 양에 영향을 미치면 안된다.
        int[][] tmp = new int[R][C];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (a[i][j] >= 5) {
                    int dust = a[i][j];

                    for (int k = 0; k < 4; k++) {
                        int nr = i + dr[k];
                        int nc = j + dc[k];

                        if (0 > nr || R <= nr || 0 > nc || C <= nc) {
                            continue;
                        }

                        if (a[nr][nc] == -1) {
                            continue;
                        }

                        tmp[nr][nc] += dust / 5;
                        tmp[i][j] -= dust / 5;
                    }
                }
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                a[i][j] += tmp[i][j];
            }
        }

    }

    private static void go1(int r, int c, int d) {
        // Todo : 해당 좌표에 방문하면 해당 좌표의 미세먼지를 저장한 뒤, 전달된 미세먼지로 좌표 값을 바꾼다.

        if (r == r1 && c == 0 && d != -1) {
            return;
        }

        int dust;
        if (d == -1) {
            dust = 0;
        } else {
            dust = a[r][c];
            a[r][c] = d;
        }

        int nr = r;
        int nc = c;


        if (nr == r1) {
            if (nc == C-1) {
                nr += dr[1];
                nc += dc[1];
            } else {
                nr += dr[0];
                nc += dc[0];
            }
        } else if (0 < nr && nr < r1) {
            if (nc == C-1) {
                nr += dr[1];
                nc += dc[1];
            } else if (nc == 0) {
                nr += dr[3];
                nc += dc[3];
            }
        } else if (nr == 0) {
            if (nc == 0) {
                nr += dr[3];
                nc += dc[3];
            } else {
                nr += dr[2];
                nc += dc[2];
            }
        }

        go1(nr, nc, dust);
    }

    private static void go2(int r, int c, int d) {
        // Todo : 해당 좌표에 방문하면 해당 좌표의 미세먼지를 저장한 뒤, 전달된 미세먼지로 좌표 값을 바꾼다.

        if (r == r2 && c == 0 && d != -1) {
            return;
        }

        int dust;
        if (d == -1) {
            dust = 0;
        } else {
            dust = a[r][c];
            a[r][c] = d;
        }

        int nr = r;
        int nc = c;

        if (nr == r2) {
            if (nc == C-1) {
                nr += dr[3];
                nc += dc[3];
            } else {
                nr += dr[0];
                nc += dc[0];
            }
        } else if (r2 < nr && nr < R-1) {
            if (nc == C-1) {
                nr += dr[3];
                nc += dc[3];
            } else if (nc == 0) {
                nr += dr[1];
                nc += dc[1];
            }
        } else if (nr == R-1) {
            if (nc == 0) {
                nr += dr[1];
                nc += dc[1];
            } else {
                nr += dr[2];
                nc += dc[2];
            }
        }

        go2(nr, nc, dust);
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
