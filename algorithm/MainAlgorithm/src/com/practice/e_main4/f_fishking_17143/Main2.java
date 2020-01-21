package com.practice.e_main4.f_fishking_17143;

import java.util.*;
import java.io.*;

public class Main2 {

    private static int R, C, M;

    private static ArrayDeque<Shark2> sharks = new ArrayDeque<>();

    private static int minRow;

    private static int answer;

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

        minRow = R;

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());

            int r = s2i(st.nextToken()) - 1;
            int c = s2i(st.nextToken()) - 1;
            int s = s2i(st.nextToken()) - 1;
            int d = s2i(st.nextToken());
            int z = s2i(st.nextToken());

            if (c == 0 && r < minRow) {
                minRow = r;
                sharks.addFirst(new Shark2(r, c, s, d, z));
            } else {
                sharks.addLast(new Shark2(r, c, s, d, z));
            }
        }
    }

    private static void solve() {
        catchShark();
        for (int c = 1; c < C; c++) {
            minRow = R;
            moveShark(c);
            catchShark();
        }
        System.out.println(answer);
    }

    private static void catchShark() {
        if (minRow < R) {
            Shark2 shark = sharks.removeFirst();
            System.out.printf("catch r = %d, c = %d, size = %d\n", shark.r, shark.c, shark.z);
            answer += shark.z;
        }
    }

    private static void moveShark(int col) {
        ArrayDeque<Shark2> tmp = new ArrayDeque<>();
        int[][] tmpMap = new int[R][C];

        while (!sharks.isEmpty()) {
            Shark2 shark = sharks.removeFirst();

            int r = shark.r;
            int c = shark.c;
            int s = shark.s;
            int d = shark.d;
            int z = shark.z;

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

            if (tmpMap[nr][nc] < z) {

                if (tmpMap[nr][nc] == 0) {

                } else {
                    System.out.printf("EAT r = %d, c = %d, size = %d\n", nr, nc, z);
                }

                tmpMap[nr][nc] = z;



                if (nc == col) {
                    if (minRow >= nr) {
                        minRow = nr;
                        tmp.addFirst(new Shark2(nr, nc, ns, nd, z));
                    } else {
                        tmp.addLast(new Shark2(nr, nc, ns, nd, z));
                    }
                } else {
                    tmp.addLast(new Shark2(nr, nc, ns, nd, z));
                }

            }
        }

        sharks = tmp;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Shark2 {
    int r, c, s, d, z;

    Shark2(int r, int c, int s, int d, int z) {
        this.r = r;
        this.c = c;
        this.s = s;
        this.d = d;
        this.z = z;
    }

}