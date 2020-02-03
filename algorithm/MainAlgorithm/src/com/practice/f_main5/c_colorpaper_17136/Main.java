package com.practice.f_main5.c_colorpaper_17136;

import java.util.*;
import java.io.*;

/**
 * 틀린 풀이
 */
public class Main {

    private static final int ATTATCH = 2;
    private static final int[] dr = {0, 1, 1};
    private static final int[] dc = {1, 1, 0};

    private static int[][] map = new int[10][10];
    private static int[][] visit;

    private static int[] papers = {0, 5, 5, 5, 5, 5};

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int r = 0; r < 10; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < 10; c++) {
                map[r][c] = s2i(st.nextToken());
            }
        }

        br.close();
    }

    private static void solve() {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (map[r][c] == 1) {
                    int l = bfs(r, c);
                    int length = checkPaper(l);

                    if (length == -1) {
                        System.out.println("-1");
                        System.exit(0);
                    } else {
                        attachPaper(r, c, length);
                    }
                }
            }
        }

        System.out.println(countUsedPaper());

    }

    private static int countUsedPaper() {
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum += papers[i];
        }

        return 25 - sum;
    }

    private static int bfs(int row, int col) {
        int length = 1;

        visit = new int[6][6];

        Queue<Integer> q = new LinkedList<>();
        q.add(row);
        q.add(col);
        visit[0][0] = 1;

        while(q != null && !q.isEmpty()) {
            int r = q.remove();
            int c = q.remove();

            if (visit[r - row][c - col] == 6) {
                length = 5;
                break;
            }

            int nr;
            int nc;
            for (int k = 0; k < 3; k++) {
                nr = r + dr[k];
                nc = c + dc[k];

                if (0 > nr || nr >= 10 || 0 > nc || nc >= 10) {
                    length = visit[r - row][c - col];
                    q = null;
                    break;
                }

                if (map[nr][nc] == 0 || map[nr][nc] == ATTATCH) {
                    length = visit[r - row][c - col];

                    q = null;
                    break;
                }

                if (visit[nr - row][nc - col] != 0) {
                    continue;
                }

                q.add(nr);
                q.add(nc);
                visit[nr - row][nc - col] = visit[r - row][c - col] + 1;
            }
        }

        return length;
    }

    private static int checkPaper(int length) {
        if (length == 5) {
            if (papers[length] > 0) {
                papers[length] -= 1;
                return length;
            }

        } else if (length == 4) {
            if (papers[length] > 0) {
                papers[length] -= 1;
                return length;
            } else if (papers[length - 2] >= 4) {
                    papers[length - 2] -= 4;
                    return length;
            }
        } else if (length == 3) {
            if (papers[length] > 0) {
                papers[length] -= 1;
                return length;
            } else if (papers[length - 1] >= 1 && papers[length - 2] >= 5) {
                papers[length - 1] -= 1;
                papers[length - 2] -= 5;
                return length;
            }
        } else if (length == 2) {
            if (papers[length] > 0) {
                papers[length] -= 1;
                return length;
            } else if (papers[length - 1] >= 4) {
                papers[length - 1] -= 4;
                return length;
            }
        } else if (length == 1) {
            if (papers[length] > 0) {
                papers[length] -= 1;
                return length;
            }
        }

        return -1;
    }

    private static void attachPaper(int row, int col, int length) {
        for (int r = row; r < row + length; r++) {
            for (int c = col; c < col + length; c++) {
                map[r][c] = ATTATCH;
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}
