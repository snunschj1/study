package com.practice.f_main5.d_buildbridge2_17472;

import java.util.*;
import java.io.*;

class Pair {
    int r, c;

    Pair(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {

    private static final int[] dr = {0, 0, 1, -1};
    private static final int[] dc = {1, -1, 0, 0};

    private static final int SEA = 0;
    private static final int ISLAND = 1;
    private static final int BRIDGE = 7;

    private static int N, M;
    private static int[][] map;

    private static int islandCnt = 0;
    private static ArrayList<ArrayList<Pair>> islands = new ArrayList<>();

    private static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = s2i(st.nextToken());
        M = s2i(st.nextToken());
        map = new int[N][M];

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < M; c++) {
                map[r][c] = s2i(st.nextToken());
            }
        }

        br.close();
    }

    private static void solve() {
        setIsland();
        setBridge();
        buildBridge(1, 0);

        if (answer == Integer.MAX_VALUE) {
            System.out.println("-1");
        } else {
            System.out.println(answer);
        }
    }

    private static void setIsland() {
        islands.add(new ArrayList<>());
        int[][] tmp = new int[N][M];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (map[r][c] == ISLAND && tmp[r][c] == 0) {
                    islands.add(new ArrayList<>());
                    ++islandCnt;
                    bfs(r, c, tmp);
                }
            }
        }
    }

    private static void setBridge() {
        Bridge.setBridges(islandCnt);
    }


    private static void bfs(int row, int col, int[][] tmp) {
        Queue<Integer> q = new LinkedList<>();
        q.add(row);
        q.add(col);
        map[row][col] = islandCnt;
        tmp[row][col] = 1;
        islands.get(islandCnt).add(new Pair(row, col));

        while (!q.isEmpty()) {
            int r = q.remove();
            int c = q.remove();

            int nr;
            int nc;
            for (int k = 0; k < 4; k++) {
                nr = r + dr[k];
                nc = c + dc[k];

                if (!checkRange(nr, nc) || map[nr][nc] == SEA || tmp[nr][nc] == 1) {
                    continue;
                }

                q.add(nr);
                q.add(nc);
                map[nr][nc] = map[r][c];
                tmp[nr][nc] = 1;
                islands.get(islandCnt).add(new Pair(nr, nc));
            }
        }
    }

    static void buildBridge(int index, int sum) {

        if (index >= islandCnt) {

            if (Bridge.isLinked()) {
                if (answer > sum) answer = sum;
            }
            return;
        }

        // 해당 섬에 포함된 좌표들을 모두 검토한다.
        for (int i = 0; i < islands.get(index).size(); i++) {

            Pair p = islands.get(index).get(i);

            // 해당 좌표 기준으로 4방향으로 직진한다.
            for (int k = 0; k < 4; k++) {
                int nr = p.r;
                int nc = p.c;

                int length = 0;
                int already = 0;

                while (true) {
                    nr += dr[k];
                    nc += dc[k];

                    if (!checkRange(nr, nc)) {
                        // 범위 넘어갈 때까지 섬에 도달하지 못함
                        length = 0;
                        break;
                    }

                    if (map[nr][nc] == SEA) {
                        // 바다면 다리 길이 + 1
                        length += 1;

                    } else if (map[nr][nc] == BRIDGE) {
                        // 건설되어 있는 다리를 만나는 경우
                        length += 1;
                        already += 1;

                        if (already >= 2 && length == 2) {
                            // 건설하려는 다리가 다른 섬에서 이미 건설한 다리인 경우
                            length = 0;
                            break;
                        }
                    } else if (map[nr][nc] == index) {
                        // 건설하려는 다리로 인해 가는 섬이 자기 자신인 경우
                        length = 0;
                        break;
                    } else if (map[nr][nc] != index) {
                        // 건설하려는 다리로 인해 가는 섬이 자기 자신이 아닌 경우
                        break;
                    }
                }

                if (length >= 2) {
                    // 다리 길이는 2 이상이어야 한다.

                    if (sum + length > answer) {
                        // 지금까지 더한 다리 길이가 이미 이전에 구한 최소 다리 길이를 넘어가면
                        // 더이상 진행할 필요 없다.
                        return;
                    }

                    build(p.r, p.c, k, length, index, map[nr][nc], index, sum);
                }
            }
        }
    }

    private static void build(int row, int col, int dir, int length, int island1, int island2, int index, int sum) {
        // 다리 건설하는 로직

        if(Bridge.isAdjs(island1, island2)) {
            // 두 섬이 이미 연결되어 있다면 진행 X
            return;
        }

        int[] tmp = new int[length];

        int r = row;
        int c = col;

        for (int i = 0; i < length; i++) {
            r += dr[dir];
            c += dc[dir];

            tmp[i] = map[r][c];
            map[r][c] = BRIDGE;
        }

        Bridge.addAdjs(island1, island2);

        buildBridge(index + 1, sum + length);

        r = row;
        c = col;

        for (int i = 0; i < length; i++) {
            r += dr[dir];
            c += dc[dir];

            map[r][c] = tmp[i];
        }

        Bridge.removeAdjs(island1, island2);
    }

    private static boolean checkRange(int row, int col) {
        if (0 <= row && row < N && 0 <= col && col < M) {
            return true;
        } else {
            return false;
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Bridge {

    private static int islandCnt;
    private static LinkedList<Integer>[] bridges;

    static void setBridges(int cnt) {
        islandCnt = cnt;
        bridges = new LinkedList[islandCnt + 1];
        for (int i = 1; i <= islandCnt; i++) {
            bridges[i] = new LinkedList<>();
        }
    }

    static void addAdjs(int island1, int island2) {
        // 인접리스트에 두 섬이 연결됨 표시
        bridges[island1].add(island2);
        bridges[island2].add(island1);
    }

    static void removeAdjs(int island1, int island2) {
        // 두 섬의 연결 해제
        for (int i = 0; i < bridges[island1].size(); i++) {
            if (bridges[island1].get(i).equals(island2)) {
                bridges[island1].remove(i);
            }
        }

        for (int i = 0; i < bridges[island2].size(); i++) {
            if (bridges[island2].get(i).equals(island1)) {
                bridges[island2].remove(i);
            }
        }

    }

    static boolean isAdjs(int island1, int island2) {
        // 두 섬이 연결되어 있는지
        for (int i = 0; i < bridges[island1].size(); i++) {
            if (bridges[island1].get(i).equals(island2)) {
                return true;
            }
        }
        return false;
    }

    static boolean isLinked() {
        // 모든 섬이 연결된 상태인지
        boolean[] tmp = new boolean[islandCnt + 1];

        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        tmp[1] = true;

        while (!q.isEmpty()) {
            int island = q.remove();

            for (int i = 0; i < bridges[island].size(); i++) {

                int nextIsland = bridges[island].get(i);

                if (!tmp[nextIsland]) {
                    q.add(nextIsland);
                    tmp[nextIsland] = true;
                }
            }
        }

        for (int i = 1; i <= islandCnt; i++) {
            if (!tmp[i]) {
                return false;
            }
        }
        return true;
    }
}

class Test {

    private static final int SEA = 0;
    private static final int BRIDGE = 7;

    static void flush() {
        try {
            Print.flush();
        } catch (Exception e) {

        }
    }

    static void printStr(String s) {
        try {
            Print.write(s);
        } catch (Exception e) {}
    }

    static void printMap(int[][] map, int R, int C) {
        try {
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {

                    if (map[r][c] == SEA) {
                        Print.write(9 + " ");
                    } else if (map[r][c] == BRIDGE) {
                        Print.write(0 + " ");
                    } else {
                        Print.write(map[r][c] + " ");
                    }
                }
                Print.write("\n");
            }
            Print.write("\n");
        } catch (Exception e) {}
    }

    static void printIsland(ArrayList<Pair> list, int islandNum) {
        try {
            Print.write("====" + islandNum + "====\n");
            for (int i = 0; i < list.size(); i++) {
                Pair p = list.get(i);

                Print.write("r = " + p.r + ", c = " + p.c + "\n");
            }
        } catch (Exception e) {}
    }


}

class Print {

    static BufferedOutputStream out;

    static {
        try {
            out = new BufferedOutputStream(new FileOutputStream("/Users/hyunjun/Documents/output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void write(String str) throws IOException {
        out.write(str.getBytes());
    }

    static void flush() throws IOException {
        out.flush();
        out.close();
    }
}

