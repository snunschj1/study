package com.practice.g_main6.g_breakblock_SW5656;

import java.io.*;
import java.util.*;

public class Main {

    private static final int[] dr = {0, 0, -1, 1};
    private static final int[] dc = {-1, 1, 0, 0};

    private static int T, N, W, H;

    private static int[][] map;
    private static int[][] tMap;    // 각 조합마다 사용할 map

    private static int[] combination;
    private static Queue<Block> bombs;      // 한 번 구슬 쏠 때마다, 첫 파괴될 블록과 그로 인해서 연쇄적으로 폭파되는 블록 중 숫자가 2 이상인 것을 담는 큐
    private static ArrayList<Integer> remains;      // 한 번 구슬 쏠 때마다, 남은 블록들 밑으로 내리기 위해서 남은 블록들만 임시로 담는 리스트

    private static int brokenBlocks;
    private static int totalBlocks;
    private static int answer;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = s2i(br.readLine());

        for (int t = 1; t <= T; t++) {

            st = new StringTokenizer(br.readLine());

            N = s2i(st.nextToken());
            W = s2i(st.nextToken());
            H = s2i(st.nextToken());


            map = new int[H][W];
            totalBlocks = 0;

            for (int r = 0; r < H; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < W; c++) {
                    map[r][c] = s2i(st.nextToken());
                    if (map[r][c] != 0) {
                        totalBlocks++;
                    }
                }
            }

            solve(t);
        }

        br.close();
    }

    private static void solve(int test) {
        answer = Integer.MAX_VALUE;
        combination = new int[N];

        makeCombination(0);
        System.out.printf("#%d %d\n", test, answer);
    }

    private static void makeCombination(int cnt) {
        // Todo : N 번 구슬을 몇 열에 쏠지 조합을 정한다.

        if (cnt >= N) {
            tMap = copyMap(map);
            brokenBlocks = 0;

            startGame();
            return;
        }

        for (int i = 0; i < W; i++) {
            combination[cnt] = i;
            makeCombination(cnt + 1);
        }
    }

    private static void startGame() {
        for (int n = 0; n < N; n++) {
            // Todo : N 번 구슬을 쏜다

            int start = combination[n];     // 구슬을 쏘는 열

            for (int r = 0; r < H; r++) {

                if (tMap[r][start] != 0) {
                    // Todo : 해당 열의 가장 위의 블록

                    breakBlock(new Block(r, start, tMap[r][start]));
                    moveRemainBlocks();
                    break;
                }
            }
        }

        if (answer > (totalBlocks - brokenBlocks)) {
            answer = totalBlocks - brokenBlocks;
        }
    }

    private static void breakBlock(Block start) {
        bombs = new LinkedList<>();
        bombs.add(start);
        tMap[start.r][start.c] = 0;
        brokenBlocks++;

        while (!bombs.isEmpty()) {
            // Todo : 블록 파괴가 끝날 때까지 진행

            Block broken = bombs.remove();
            explode(broken);
        }
    }

    private static void explode(Block block) {
        // Todo : 상하좌우로 폭발하는 로직 처리

        int nr;
        int nc;

        for (int k = 0; k < 4; k++) {   // 4 방향

            nr = block.r;
            nc = block.c;

            for (int n = 1; n < block.n; n++) {
                nr += dr[k];
                nc += dc[k];

                if (0 > nr || nr >= H || 0 > nc || nc >= W) break; // 범위 밖
                if (tMap[nr][nc] == 0) continue;  // 블록이 없거나 이미 파괴된 경우

                if (tMap[nr][nc] == 1) { // 블록의 숫자가 1인 경우, visit 처리와 brokenBlocks++ 만 한다.
                    tMap[nr][nc] = 0;
                    brokenBlocks++;
                } else {
                    bombs.add(new Block(nr, nc, tMap[nr][nc])); // 그 외의 숫자는 연쇄작용이 일어나므로, 큐에 추가한다.
                    tMap[nr][nc] = 0;
                    brokenBlocks++;
                }
            }
        }
    }

    private static void moveRemainBlocks() {

        for (int c = 0; c < W; c++) {
            remains = new ArrayList<>();

            for (int r = H - 1; r >= 0; r--) {  // Todo : 열의 마지막 행부터 0행까지 검토
                if (tMap[r][c] != 0) {
                    remains.add(tMap[r][c]);
                }
            }

            for (int i = 0; i < remains.size(); i++) {
                // Todo : 남아있는 블록을 마지막 행부터 채워준다.
                tMap[H - i - 1][c] = remains.get(i);
            }

            for (int i = 0; i < H - remains.size(); i++) {
                // Todo : 남아있는 블록을 다 채운 뒤, 나머지는 0으로 채운다.
                tMap[i][c] = 0;
            }
        }

    }

    private static int[][] copyMap(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];

        for (int r = 0; r < original.length; r++) {
            copy[r] = Arrays.copyOf(original[r], original[r].length);
        }

        return copy;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }

}

class Block {
    int r, c, n;

    Block(int r, int c, int n) {
        this.r = r;
        this.c = c;
        this.n = n;
    }
}
