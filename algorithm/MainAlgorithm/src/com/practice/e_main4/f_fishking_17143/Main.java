package com.practice.e_main4.f_fishking_17143;

import java.util.*;
import java.io.*;

public class Main {

    private static int R, C, M;

    private static Shark[][] map;

    private static int answer = 0;

    private static int minRow;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = s2i(st.nextToken());
        C = s2i(st.nextToken());
        M = s2i(st.nextToken());

        map = new Shark[R][C];
        minRow = R;

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());

            int r = s2i(st.nextToken()) - 1;
            int c = s2i(st.nextToken()) - 1;
            int s = s2i(st.nextToken()) - 1;
            int d = s2i(st.nextToken());
            int z = s2i(st.nextToken());

            if (c == 0) {
                if (minRow > r) {
                    minRow = r;
                }
            }

            Shark shark = new Shark(s, d, z);

            map[r][c] = shark;
        }
    }

    private static void solve() {
        catchShark(0);
        for (int i = 1; i < C; i++) {
            minRow = R;
            moveShark(i);
            catchShark(i);
        }

        System.out.println(answer);
    }

    private static void catchShark(int col) {
        if (minRow < R) {
            answer += map[minRow][col].z;
            map[minRow][col] = null;
        }
    }

    private static void moveShark(int col) {

        Shark[][] tmp = new Shark[R][C];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] != null) {
                    int r = i;
                    int c = j;
                    int s = map[i][j].s;
                    int d = map[i][j].d;
                    int z = map[i][j].z;

                    int nr, nc, nd, ns;

                    int a, b;

                    if (s == -1) {
                        nr = r;
                        nc = c;
                        nd = d;
                        ns = s;

                    } else {
                        if (d % 2 == 0) {
                            if (d == 2) {
                                ns = s + r;

                                a = ns / (R - 1);
                                b = ns % (R - 1);

                                if (a % 2 == 0) {
                                    nd = d;
                                    nr = (b + 1);
                                    nc = c;
                                } else /* if (a % 2 == 1) */ {
                                    nd = 1;
                                    nr = (R - 2 - b);
                                    nc = c;
                                }

                            } else /* if (d == 4) */ {
                                ns = s + (C - 1 - c);

                                a = ns / (C - 1);
                                b = ns % (C - 1);

                                if (a % 2 == 0) {
                                    nd = d;
                                    nr = r;
                                    nc = (C - 2 - b);
                                } else /* if (a % 2 == 1) */ {
                                    nd = 3;
                                    nr = r;
                                    nc = (b + 1);
                                }
                            }
                        } else /* if (d % 2 == 1) */ {
                            if (d == 1) {
                                ns = s + (R - 1 - r);

                                a = ns / (R - 1);
                                b = ns % (R - 1);

                                if (a % 2 == 0) {
                                    nd = d;
                                    nr = (R - 2 - b);
                                    nc = c;
                                } else /* if (a % 2 == 1) */ {
                                    nd = 2;
                                    nr = (b + 1);
                                    nc = c;
                                }

                            } else /* if (d == 3) */ {
                                ns = s + c;

                                a = ns / (C - 1);
                                b = ns % (C - 1);

                                if (a % 2 == 0) {
                                    nd = d;
                                    nr = r;
                                    nc = (b + 1);
                                } else /* if (a % 2 == 1) */ {
                                    nd = 4;
                                    nr = r;
                                    nc = (C - 2 - b);
                                }
                            }
                        }
                        ns = s;
                    }

                    if (tmp[nr][nc] != null) {
                        if (z < tmp[nr][nc].z) {
                            continue;
                        }
                    }

                    if (nc == col) {
                        if (minRow > nr) {
                            minRow = nr;
                        }
                    }

                    tmp[nr][nc] = new Shark(ns, nd, z);
                }
            }
        }

        map = tmp;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Shark {
    int s, d, z;

    Shark(int s, int d, int z) {
        this.s = s;
        this.d = d;
        this.z = z;
    }
}