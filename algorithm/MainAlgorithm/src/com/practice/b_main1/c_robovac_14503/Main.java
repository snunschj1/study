package com.practice.b_main1.c_robovac_14503;

import java.util.*;
import java.io.*;

public class Main {

    /**
     * n : 세로
     * m : 가로
     *
     * (r, c) : 현재 위치
     * d : 현재 방향
     */
    static int n;
    static int m;
    static int r;
    static int c;
    static int d;

    static int[][] map;

    /** 북 (0) 동(1) 남(2) 서(3) */
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        LinkedList<Pair> q = new LinkedList<>();
        q.add(new Pair(r, c, d));
        map[r][c] = 2;

        boolean isCompleted = false;

        while(!isCompleted) {
            Pair p = q.remove();
            int x = p.x;
            int y = p.y;
            int d = p.d;

            int nx;
            int ny;
            int nd;

            // Todo : 4 방향으로 돌리기
            for (int i = 0; i < 4; i++) {

                // 0 -> 3 / 1 -> 0 / 2 -> 1 / 3 -> 2
                nd = (d + 3) % 4;
                nx = x + dx[nd];
                ny = y + dy[nd];


                if (0 > nx || nx >= n || 0 > ny || ny >= m || map[nx][ny] == 1 || map[nx][ny] == 2) {
                    // Todo : 벽(1)이거나 이미 청소한 장소(2)를 마주쳤을 때

                    if (i <= 2) {
                        // Todo : 3번까지는 계속 방향을 돌린다.
                        d = nd;
                        continue;
                    }

                    // Todo : 4번째
                    // 0 -> 2 / 1 -> 3 / 2 -> 0 / 3 -> 1
                    int md = (nd + 2) % 4;
                    int mx = x + dx[md];
                    int my = y + dy[md];

                    if (map[mx][my] == 2) {
                        q.add(new Pair(mx, my, nd));
                    } else if (map[mx][my] == 1) {
                        isCompleted = true;
                        break;
                    }
                } else if (map[nx][ny] == 0) {
                    // Todo : 아직 청소 안 한 장소(0) 이면 청소한다.
                    map[nx][ny] = 2;
                    q.add(new Pair(nx, ny, nd));
                    break;
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 2) {
                    ans += 1;
                }
            }
        }
        System.out.println(ans);
    }
}

class Pair {
    int x;
    int y;
    int d;

    Pair(int x, int y, int d) {
        this.x = x;
        this.y = y;
        this.d = d;
    }
}