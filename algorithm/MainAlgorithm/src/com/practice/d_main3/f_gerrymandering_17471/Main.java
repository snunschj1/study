package com.practice.d_main3.f_gerrymandering_17471;

import java.util.*;
import java.io.*;

public class Main {

    private static final int CANCELED = 0;
    private static final int VISITED = 1;
    private static final int CHECKED = 2;

    private static int N;

    private static LinkedList<Integer>[] list;

    private static int[] pop;

    private static ArrayList<Pair> edges = new ArrayList<>();

    private static boolean flag = false;

    private static int answer = 1000;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = s2i(br.readLine());

        list = new LinkedList[N];

        pop = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            list[i] = new LinkedList<>();
            pop[i] = s2i(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int k = s2i(st.nextToken());

            while (k-- > 0) {
                int adjacentVertex = s2i(st.nextToken()) - 1;
                list[i].add(adjacentVertex);

                if (i < adjacentVertex) {
                    edges.add(new Pair(i, adjacentVertex));
                }
            }
        }

        br.close();
    }

    private static void solve() {
        for (int cnt = 1; cnt <= edges.size(); cnt++) {
            int[] checkEdges = new int[edges.size()];
            // Todo : 간선 조합을 구한다.
            selectEdges(0, 0, cnt, checkEdges);
        }

        if (flag) {
            System.out.println(answer);
        } else {
            System.out.println("-1");
        }
    }

    private static void selectEdges(int index, int cnt, int target, int[] checkEdges) {

        if (cnt == target) {
            // Todo : 간선 조합에 따라 정점들을 팀 0 / 팀 1 로 나눈다.
            int[] vertexes = selectVertexes(checkEdges);
            int[] tmpVertexes = new int[vertexes.length];
            for (int i = 0; i < vertexes.length; i++) {
                tmpVertexes[i] = vertexes[i];
            }

            int popOne = checkTeamOne(vertexes);
            int popZero = checkTeamTwo(tmpVertexes);

            if (popOne != -1 && popZero != -1) {
                int result = Math.abs(popOne - popZero);

                if (!flag) {
                    flag = true;
                }

                if (answer > result) {
                    answer = result;
                }
            }

            return;
        } else {
            if (index >= checkEdges.length) {
                return;
            }
        }

        checkEdges[index] = VISITED;
        selectEdges(index + 1, cnt + 1, target, checkEdges);
        checkEdges[index] = CANCELED;
        selectEdges(index + 1, cnt, target, checkEdges);
    }

    private static int checkTeamOne(int[] vertexes) {
        int[] c = new int[vertexes.length];

        int startIndex = 0;

        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == VISITED) {
                startIndex = i;
                break;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(startIndex);
        vertexes[startIndex] = CHECKED;
        c[startIndex] = VISITED;

        while(!q.isEmpty()) {
            int i = q.remove();

            for (int j = 0; j < list[i].size(); j++) {
                int next = list[i].get(j);

                if (c[next] == 0) {
                    if (vertexes[next] == VISITED) {
                        vertexes[next] = CHECKED;
                        c[next] = VISITED;
                        q.add(next);
                    }
                }
            }
        }

        int p = 0;

        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == VISITED) {
                p = -1;
                break;
            }

            if (vertexes[i] == CHECKED) {
                p += pop[i];
            }
        }

        return p;
    }

    private static int checkTeamTwo(int[] vertexes) {
        int[] c = new int[vertexes.length];

        int startIndex = 0;

        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == CANCELED) {
                startIndex = i;
                break;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(startIndex);
        vertexes[startIndex] = CHECKED;
        c[startIndex] = VISITED;

        while(!q.isEmpty()) {
            int i = q.remove();

            for (int j = 0; j < list[i].size(); j++) {
                int next = list[i].get(j);

                if (c[next] == 0) {
                    if (vertexes[next] == CANCELED) {
                        vertexes[next] = CHECKED;
                        c[next] = VISITED;
                        q.add(next);
                    }
                }
            }
        }

        int p = 0;

        for (int i = 0; i < vertexes.length; i++) {
            if (vertexes[i] == CANCELED) {
                p = -1;
                break;
            }

            if (vertexes[i] == CHECKED) {
                p += pop[i];
            }
        }

        return p;
    }

    private static int[] selectVertexes(int[] checkEdges) {
        int[] vertexes = new int[N];

        for (int i = 0; i < checkEdges.length; i++) {
            if (checkEdges[i] == VISITED) {
                Pair p = edges.get(i);
                int x = p.x;
                int y = p.y;

                vertexes[x] = VISITED;
                vertexes[y] = VISITED;
            }
        }

        return vertexes;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Pair {
    int x, y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}