package com.practice.f_main5.g_newgame2_17837;

import java.util.*;
import java.io.*;

public class Main {

    private static final int WHITE = 0;
    private static final int RED = 1;
    private static final int BLUE = 2;

    private static final int ROW = 0;
    private static final int COL = 1;
    private static final int DIR = 2;
    private static final int HEIGHT = 3;

    private static final int[] dr = {0, 0, -1, 1};
    private static final int[] dc = {1, -1, 0, 0};

    private static int N, K;

    private static int[][] map;

    // 각 말(행)의 ROW, COL, DIR, HEIGHT 정보를 열에 담는다.
    private static int[][] horses;

    private static int turn = 1;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        K = s2i(st.nextToken());

        map = new int[N][N];
        horses = new int[K][4];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = s2i(st.nextToken());
            }
        }

        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());

            horses[k][ROW] = s2i(st.nextToken()) - 1;
            horses[k][COL] = s2i(st.nextToken()) - 1;
            horses[k][DIR] = s2i(st.nextToken()) - 1;
            horses[k][HEIGHT] = 1;
        }

        br.close();
    }

    private static void solve() {

        boolean isFound = false;

        for ( ; ; ++turn) {

            if (turn >= 1000) {
                break;
            }

            if (playTurn()) {
                isFound = true;
                break;
            }
        }

        if (isFound) {
            System.out.println(turn);
        } else {
            System.out.println("-1");
        }


    }

    private static boolean playTurn() {
        for (int k = 0; k < K; k++) {
            // 말들을 순서대로 움직이는 시도

            int curRow = horses[k][ROW];
            int curCol = horses[k][COL];
            int curDir = horses[k][DIR];

            int nr = curRow + dr[curDir];
            int nc = curCol + dc[curDir];

            // 한 칸에 4개 이상 쌓이면, go에서 true가 반환된다.
            if (go(nr, nc, k)) {
                return true;
            }
        }

        return false;
    }

    private static boolean go(int nr, int nc, int k) {
        // 다음 칸이 RED, BLUE, WHITE, 범위 밖이냐에 따라 다르게 처리한다.

        if (!checkRange(nr, nc) || map[nr][nc] == BLUE) {
            return moveBlue(k);
        } else if (map[nr][nc] == WHITE) {
            return moveWhite(k);
        } else /* (map[nr][nc] == RED) */ {
            return moveRed(k);
        }
    }

    private static boolean go2(int nr, int nc, int k) {
        // BLUE 이동으로 인해 보게 되는 다음(반대 방향) 칸

        if (!checkRange(nr, nc) || map[nr][nc] == BLUE) {
            return false;
        } else if (map[nr][nc] == WHITE) {
            return moveWhite(k);
        } else /* (map[nr][nc] == RED) */ {
            return moveRed(k);
        }
    }

    private static boolean moveWhite(int k) {
        int cr = horses[k][ROW];
        int cc = horses[k][COL];
        int dir = horses[k][DIR];
        int height = horses[k][HEIGHT];

        int nr = cr + dr[dir];
        int nc = cc + dc[dir];

        return move(nr, nc, cr, cc, height);
    }

    private static boolean moveRed(int k) {
        int cr = horses[k][ROW];
        int cc = horses[k][COL];
        int dir = horses[k][DIR];
        int height = horses[k][HEIGHT];

        int nr = cr + dr[dir];
        int nc = cc + dc[dir];

        // 현재 칸에 있는 말들을 swap 해준다.
        swapHeight(cr, cc, height);

        // 다음 칸으로 말들을 옮긴다.
        return move(nr, nc, cr, cc, height);
    }

    private static boolean moveBlue(int k) {
        int cr = horses[k][ROW];
        int cc = horses[k][COL];
        int dir = horses[k][DIR];

        // 해당 말의 방향을 바꿔준다.
        int nd = changeDir(k, dir);

        int nr = cr + dr[nd];
        int nc = cc + dc[nd];

        // 이동
        if (go2(nr, nc, k)) {
            return true;
        }
        return false;
    }

    private static boolean move(int nr, int nc, int cr, int cc, int cHeight) {
        int maxHeight = 0;

        for (int k = 0; k < K; k++) {
            if (nr == horses[k][ROW] && nc == horses[k][COL]) {
                int th = horses[k][HEIGHT];
                // 이동하려는 칸에 이미 있는 말의 수의 최대값
                if (maxHeight < th) {
                    maxHeight = th;
                }
            }
        }

        for (int k = 0; k < K; k++) {
            if (cr == horses[k][ROW] && cc == horses[k][COL] && horses[k][HEIGHT] >= cHeight) {
                // 이동하려는 칸으로 이동시켜준다.
                horses[k][ROW] = nr;
                horses[k][COL] = nc;

                // 이동하려는 칸으로 갔을 때의 높이를 결정
                horses[k][HEIGHT] = horses[k][HEIGHT] - cHeight + maxHeight + 1;

                if (horses[k][HEIGHT] >= 4) {
                    // 높이가 4이상이면 답을 찾은 것이다.
                    return true;
                }
            }
        }

        return false;
    }

    private static void swapHeight(int cr, int cc, int cHeight) {
        ArrayList<Pair> list = new ArrayList<>();

        for (int k = 0; k < K; k++) {
            if (cr == horses[k][ROW] && cc == horses[k][COL] && horses[k][HEIGHT] >= cHeight) {

                // 현재 칸에 해당 말 포함 위에 있는 말들을 {말 번호, 높이}로 리스트에 담는다.
                list.add(new Pair(k, horses[k][HEIGHT]));
            }
        }

        // 높이를 기준으로 오름차순 정렬
        Collections.sort(list);

        // 높이 swap을 위해 높이를 배열에 담는다.
        int[] tmp = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            tmp[i] = list.get(i).height;
        }

        // swap
        swap(tmp);

        for (int i = 0; i < list.size(); i++) {
            horses[list.get(i).horse][HEIGHT] = tmp[i];
        }
    }

    private static void swap(int[] list) {
        int i = 0;
        int j = list.length - 1;

        int tmp;
        while (i < j) {
            tmp = list[i];
            list[i] = list[j];
            list[j] = tmp;

            i += 1;
            j -= 1;
        }
    }

    private static int changeDir(int k, int d) {
        if (d % 2 == 0) {
            horses[k][DIR] = d + 1;
        } else {
            horses[k][DIR] = d - 1;
        }
        return horses[k][DIR];
    }

    private static boolean checkRange(int row, int col) {
        return 0 <= row && row < N && 0 <= col && col < N;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair implements Comparable<Pair> {
    int horse, height;

    Pair(int horse, int height) {
        this.horse = horse;
        this.height = height;
    }

    @Override
    public int compareTo(Pair o) {
        if (this.height > o.height) {
            return 1;
        } else {
            return -1;
        }
    }
}