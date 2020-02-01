package com.practice.f_main5.b_addbracket_16637;

import java.util.*;
import java.io.*;

public class Main {

    private static final int PLUS = 43;
    private static final int TIME = 42;
    private static final int MINUS = 45;
    private static final int LEFT = 40;

    private static ArrayDeque<Integer> num = new ArrayDeque<>();
    private static ArrayDeque<Character> operator = new ArrayDeque<>();
    private static long answer = - Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Formula.setN(s2i(br.readLine()));
        Formula.setFormula(br.readLine());
    }

    private static void solve() {
        addBracket(1);
        System.out.println(answer);
    }

    private static void addBracket(int idx) {

        if (idx > Formula.getLastOperatorIndex()) {
            String formulaWithBracket = Formula.makeFormulaBracket();
            calculate(formulaWithBracket);
            return;
        }

        // Todo : 해당 idx 선택 X
        addBracket(idx + 2);

        // Todo : 해당 idx 선택 O
        if (Formula.ableAddBracket(idx)) {
            Formula.setCheckBracket(idx);
            addBracket(idx + 2);
            Formula.setUnCheckBracket(idx);
        }
    }

    private static void calculate(String s) {

        // Todo : 괄호까지 있는 수식을 계산하기 위해서, 정수와 연산자 Deque에 담아서 계산을 준비하는 로직
        for (int i = 0; i < s.length(); i++) {
            char tmp = s.charAt(i);

            if (48 <= tmp && tmp <= 57) {
                num.addLast(tmp - 48);
            } else if (tmp == PLUS || tmp == MINUS || tmp == TIME) {
                operator.addLast(tmp);
            } else if (tmp == LEFT) {
                int tmpNum1 = s.charAt(i + 1) - 48;
                char tmpOp = s.charAt(i + 2);
                int tmpNum2 = s.charAt(i + 3) - 48;

                num.addLast(tmpCalculateBracket(tmpNum1, tmpNum2, tmpOp));
                i += 4;
            }
        }

        calculateResult();
    }

    private static void calculateResult() {
        // Todo : Deque 에 담긴 정수, 연산자를 활용해서 수식을 계산하는 함수
        while (num.size() != 1) {
            int n1 = num.pop();
            int n2 = num.pop();
            char op = operator.pop();

            num.addFirst(tmpCalculateBracket(n1, n2, op));
        }

        int result = num.pop();
        if (answer < result) answer = result;
    }

    private static int tmpCalculateBracket(int n1, int n2, int op) {
        // Todo : 괄호 ( 를 만났을 때, 괄호 ) 전까지 미리 계산하는 함수
        int tmp = n1;
        if (op == PLUS) tmp += n2;
        else if (op == MINUS) tmp -= n2;
        else if (op == TIME) tmp *= n2;

        return tmp;
    }

    private static int s2i(String s) {
        return Integer.parseInt(s);
    }
}

class Formula {

    private static final int LEFT = 1;
    private static final int NUM = 2;
    private static final int RIGHT = 3;

    private static int N;
    private static char[] formula;
    private static int[] check;

    static void setN(int n) {
        N = n;
        formula = new char[N];
        check = new int[N];
    }

    static int getN() {
        return N;
    }

    static int getLastOperatorIndex() {
        // Todo : 마지막 연산자가 있는 index
        return N - 2;
    }

    static void setFormula(String f) {
        for (int i = 0; i < N; i++) {
            formula[i] = f.charAt(i);
        }
    }

    static void setCheckBracket(int idx) {
        // Todo : 해당 연산자를 괄호로 감싸는 것을 선택했을 때,
        //  연산자 index 포함해서 앞 뒤 index에 숫자를 부여해 괄호로 감싸진 것을 나타냄
        check[idx-1] = LEFT;
        check[idx] = NUM;
        check[idx+1] = RIGHT;
    }

    static void setUnCheckBracket(int idx) {
        // Todo : 괄호 해체
        check[idx-1] = 0;
        check[idx] = 0;
        check[idx+1] = 0;
    }

    static boolean ableAddBracket(int idx) {
        // Todo : 해당 연산자가 괄호로 감싸질 수 있는지 볼 때는
        //  check[idx - 1] 이 0 인지 보면 된다.
        return check[idx - 1] == 0;
    }

    static String makeFormulaBracket() {
        // Todo : 수식을 괄호가 있는 수식으로 바꾸는 로직
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < N; i++) {
            if (check[i] == 0 || check[i] == NUM) builder.append(formula[i]);
            else if (check[i] == LEFT) {
                builder.append("(");
                builder.append(formula[i]);
            } else if (check[i] == RIGHT) {
                builder.append(formula[i]);
                builder.append(")");
            }
        }
        return builder.toString();
    }
}


