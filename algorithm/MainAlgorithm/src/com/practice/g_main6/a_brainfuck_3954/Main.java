package com.practice.g_main6.a_brainfuck_3954;

import java.util.*;
import java.io.*;

public class Main {

    private static final int PLUS = 0;
    private static final int MINUS = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int LB = 4;
    private static final int RB = 5;
    private static final int PERIOD = 6;
    private static final int COMMA = 7;

    private static int T;

    private static int sm, sc, si;

    private static int pointer;
    private static int bracketCnt;

    private static int[] nums;
    private static int[][] codes;
    private static LinkedList<Integer> words;
    private static int[][] brackets;

    public static void main(String[] args) throws Exception {
        inputData();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;
        T = s2i(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());

            pointer = 0;

            sm = s2i(st.nextToken());
            sc = s2i(st.nextToken());
            si = s2i(st.nextToken());
            st = null;

            nums = new int[sm];
            codes = new int[sc][2];
            words = new LinkedList<>();

            String tmp = br.readLine();
            for (int i = 0; i < tmp.length(); i++) {
                char code = tmp.charAt(i);

                if (code == '+') {
                    codes[i][0] = PLUS;
                } else if (code == '-') {
                    codes[i][0] = MINUS;
                } else if (code == '<') {
                    codes[i][0] = LEFT;
                } else if (code == '>') {
                    codes[i][0] = RIGHT;
                } else if (code == '[') {
                    codes[i][0] = LB;
                } else if (code == ']') {
                    codes[i][0] = RB;
                    bracketCnt++;
                } else if (code == '.') {
                    codes[i][0] = PERIOD;
                } else if (code == ',') {
                    codes[i][0] = COMMA;
                }
            }

            tmp = br.readLine();
            for (int i = 0; i < tmp.length(); i++) {
                int word = (int) tmp.charAt(i);
                words.add(word);
            }
            solve();
        }
        br.close();
    }

    private static void solve() {
        brackets = new int[bracketCnt][2];

        findBracketPair();      // input 으로 주어진 LB와 RB의 서로의 짝을 찾아준다.

        int turn = 1;
        int codePointer = 0;
        boolean exit = false;

        Stack<Integer> stack = new Stack<>();
        int loopBracket = 0;

        while (turn <= 50000000) {

            int code = codes[codePointer][0];

            switch (code) {
                case PLUS:
                    nums[pointer] += 1;
                    nums[pointer] = checkNums();
                    codePointer++;
                    break;

                case MINUS:
                    nums[pointer] -= 1;
                    nums[pointer] = checkNums();
                    codePointer++;
                    break;

                case LEFT:
                    movePointerLeft();
                    codePointer++;
                    break;

                case RIGHT:
                    movePointerRight();
                    codePointer++;
                    break;

                case LB:    // Todo : LB를 만나면 무조건 Stack 에 push
                    if (nums[pointer] == 0) {
                        int bracketIndex = codes[codePointer][1];   // 해당 LB의 몇 번째 괄호 짝 index
                        codePointer = brackets[bracketIndex][1];    // 해당 LB와 짝을 이루는 RB 위치로 코드 포인터를 이동시키다.
                        stack.push(bracketIndex);
                    } else {
                        stack.push(codes[codePointer][1]);
                        codePointer++;
                    }
                    break;

                case RB:    // Todo : RB를 만나면 무조건 Stack 에서 pop
                    if (nums[pointer] != 0) {
                        int bracketIndex = codes[codePointer][1];   // 해당 RB가 몇 번째 괄호 짝 index
                        codePointer = brackets[bracketIndex][0];    // 해당 RB와 짝을 이루는 LB 위치로 코드 포인터를 이동시킨다.
                        loopBracket = stack.pop();
                    } else {
                        codePointer++;
                        loopBracket = stack.pop();
                    }
                    break;

                case PERIOD:
                    codePointer++;
                    break;

                case COMMA:
                    int read = 255;
                    if (!words.isEmpty()) {
                        read = words.removeFirst();
                    }
                    nums[pointer] = read;
                    codePointer++;
                    break;
            }

            if (codePointer >= sc) {
                exit = true;
                break;
            }
            turn++;
        }

        if (exit) {
            System.out.println("Terminates");
        } else {

            // Todo : Stack의 마지막에 남아 있는 짝이 무한 루프 짝이다.
            while (!stack.isEmpty()) {
                loopBracket = stack.pop();
            }

            System.out.println("Loops " + brackets[loopBracket][0] + " " + brackets[loopBracket][1]);
        }

    }

    private static void movePointerLeft() {
        pointer -= 1;
        checkPointer();
    }

    private static void movePointerRight() {
        pointer += 1;
        checkPointer();
    }

    private static void checkPointer() {
        if (pointer > sm - 1) {
            pointer = 0;
        } else if (pointer < 0) {
            pointer = sm - 1;
        }
    }


    private static int checkNums() {
        if (nums[pointer] > 255) {
            return 0;
        } else if (nums[pointer] < 0) {
            return 255;
        } else {
            return nums[pointer];
        }
    }

    private static void findBracketPair() {
        Stack<Integer> stack = new Stack<>();

        int index = 0;

        for (int i = 0; i < codes.length; i++) {
            if (codes[i][0] == LB) {
                stack.push(i);
            } else if (codes[i][0] == RB) {
                int lbIndex = stack.pop();

                // Todo : input 으로 주어진 코드 내의 LB 와 RB 짝을 구분할 수 있는 index 값 부여
                codes[lbIndex][1] = index;
                codes[i][1] = index;

                // Todo : [index][0] = index 번째 괄호 짝 중 LB의 input 으로 주어진 코드 내의 위치
                //        [index][1] = index 번째 괄호 짝 중 RB의 input 으로 주어진 코드 내의 위치
                brackets[index][0] = lbIndex;
                brackets[index][1] = i;

                index++;
            }
        }
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}


class Test {

    static void recordStr(String s) {
        Print.write(s);
    }

    static void recordArr(int[] a) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < a.length; i++) {
            builder.append(a[i]).append(" ");
        }
        Print.write(builder.toString());
    }

    static void recordArr2(int[][] a) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                builder.append(a[i][j]).append(" ");
            }
            builder.append("\n");
        }
        Print.write(builder.toString());
    }

    static void close() {
        Print.flush();
    }

}


class Print {

    static BufferedOutputStream out;

    static {
        try {
            out = new BufferedOutputStream(new FileOutputStream("re/output.txt"));
        } catch (Exception e) {
        }
    }

    static void flush() {
        try {
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }

    static void write(String s) {
        try {
            out.write(s.getBytes());
        } catch (Exception e) {
        }
    }
}