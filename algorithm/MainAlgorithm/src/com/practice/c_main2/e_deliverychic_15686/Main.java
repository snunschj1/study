package com.practice.c_main2.e_deliverychic_15686;

import java.util.*;
import java.io.*;

public class Main {

    private static final int[] dr = {0, 0, 1, -1};
    private static final int[] dc = {1, -1, 0, 0};

    private static final int MAP_EMPTY = 0;
    private static final int MAP_HOUSE = 1;
    private static final int MAP_CHICKEN = 2;

    private static int n;
    private static int m;

    private static int[][] map;
    private static int[][] check;

    private static int numberOfHouse = 0;
    private static int numberOfChicken = 0;

    private static int[][] numberingHouseMap;
    private static Chicken[] chickens;
    private static int[][] distHouseFromChicken;

    private static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = s2i(st.nextToken());
        m = s2i(st.nextToken());

        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = s2i(st.nextToken());

                if (map[i][j] == MAP_HOUSE) {
                    numberOfHouse++;
                } else if (map[i][j] == MAP_CHICKEN) {
                    numberOfChicken++;
                }
            }
        }
    }

    private static void solve() {
        recordHouseAndChicken();
        recordDistHouseFromChicken();
        findMinChickenDist();
        System.out.println(min);
    }

    private static void recordHouseAndChicken() {
        numberingHouseMap = new int[n][n];
        chickens = new Chicken[numberOfChicken];
        distHouseFromChicken = new int[numberOfHouse][numberOfChicken];


        int cntHouse = 0;
        int cntChicken = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == MAP_HOUSE) {
                    numberingHouseMap[i][j] = cntHouse++;
                } else if (map[i][j] == MAP_CHICKEN) {
                    chickens[cntChicken++] = new Chicken(i, j);
                }
            }
        }
    }

    private static void recordDistHouseFromChicken() {
        for (int i = 0; i < numberOfChicken; i++) {
            bfs(i);
        }
    }

    private static void bfs(int idxChicken) {
        check = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(check[i], -1);
        }

        Chicken chicken = chickens[idxChicken];

        Queue<Location> q = new LinkedList<>();
        q.add(new Location(chicken.r, chicken.c));
        check[chicken.r][chicken.c] = 0;

        while(!q.isEmpty()) {
            Location l = q.remove();
            int r = l.r;
            int c = l.c;

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                if (0 > nr || nr >= n || 0 > nc || nc >= n) {
                    continue;
                }

                if (check[nr][nc] != -1) {
                    continue;
                }

                q.add(new Location(nr, nc));
                check[nr][nc] = check[r][c] + 1;

                if (map[nr][nc] == MAP_HOUSE) {

                    int idxHouse = numberingHouseMap[nr][nc];

                    distHouseFromChicken[idxHouse][idxChicken] = check[nr][nc];
                }
            }
        }
    }

    private static void findMinChickenDist() {
        findMinIfOneChicken();
        for (int i = 2; i <= m; i++) {
            for (int j = 0; j < numberOfChicken; j++) {
                int[] chicks = new int[m];
                findMin(j, 0, i, chicks);
            }
        }
    }

    private static void findMinIfOneChicken() {
        for (int i = 0; i < numberOfChicken; i++) {
            int sum = 0;
            for (int j = 0; j < numberOfHouse; j++) {
                sum += distHouseFromChicken[j][i];

                if (sum > min) {
                    break;
                }
            }

            if (min > sum) {
                min = sum;
            }
        }
    }

    private static void findMin(int idx, int cnt, int targetSize, int[] chicks) {

        if (idx > numberOfChicken) {
            return;
        }

        if (cnt == targetSize) {

            int sum = 0;
            for (int i = 0; i < numberOfHouse; i++) {
                int tmp = 2 * (n - 1);
                for (int j = 0; j < targetSize; j++) {
                    int chickIdx = chicks[j];
//                    System.out.print(chickIndx + " ");

                    if (tmp > distHouseFromChicken[i][chickIdx]) {
                        tmp = distHouseFromChicken[i][chickIdx];
                    }
                }
//                System.out.println();
                sum += tmp;

                if (sum > min) {
                    return;
                }
            }

            if (min > sum) {
                min = sum;
                return;
            } else {
                return;
            }
        }

        chicks[cnt] = idx;
        findMin(idx + 1, cnt + 1, targetSize, chicks);
        findMin(idx + 1, cnt, targetSize, chicks);

    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Chicken {
    int r;
    int c;

    Chicken(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

class Location {
    int r;
    int c;

    Location(int r, int c) {
        this.r = r;
        this.c = c;
    }
}