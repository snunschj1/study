package com.practice.a_pre.e_lotto_6603;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /**
         * k : 배열 s의 length
         * s : 입력받은 수를 저장하는 배열
         * a : 0, 1로 이루어진 length = k 인 배열 - 0 = 선택 / 1 = 선택 X
         */
        int k;
        int[] s;
        int[] a;

        while (true) {
            st = new StringTokenizer(br.readLine());

            k = Integer.parseInt(st.nextToken());
            if (k == 0) {
                // 0 입력 받으면 끝낸다.
                break;
            }

            s = new int[k];
            for (int i = 0; i < k; i++) {
                s[i] = Integer.parseInt(st.nextToken());
            }

            a = new int[k];
            for (int i = 6; i < k; i++) {
                // 배열 a : 6개의 0 & (k - 6)개의 1
                a[i] = 1;
            }

            do {
                StringBuilder b = new StringBuilder();

                for (int i = 0; i < k; i++) {
                    if (a[i] == 0) {
                        // a[i] == 0 일 때만, 값을 append 한다.
                        b.append(s[i] + " ");
                    }
                }

                b.substring(0, b.length() - 1);
                System.out.println(b.toString());
            } while (next_permutation(a));

            // 각 테스트 케이스 사이에는 빈 줄 하나 출력
            System.out.println();
        }
    }

    private static boolean next_permutation(int[] a) {
        int i = a.length - 1;

        while (i > 0 && a[i] <= a[i-1]) {
            i -= 1;
        }

        if (i <= 0) {
            return false;
        }

        int j = a.length - 1;

        while (a[j] <= a[i-1]) {
            j -= 1;
        }

        int tmp = a[i-1];
        a[i-1] = a[j];
        a[j] = tmp;

        j = a.length - 1;

        while (i < j) {
            tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;

            i += 1;
            j -= 1;
        }

        return true;
    }
}
