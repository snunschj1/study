package com.practice.a_pre.h_tetromino_14500;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] a = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                a[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int result = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                int tmp = 0;

                /** ㅁㅁㅁㅁ */
                if (j+3 < m) {
                    for (int k = 0; k < 4; k++) {
                        tmp += a[i][j+k];
                    }

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 * ㅁ
                 * ㅁ
                 * ㅁ
                 * ㅁ
                 */
                if (i+3 < n) {
                    tmp = 0;
                    for (int k = 0; k < 4; k++) {
                        tmp += a[i+k][j];
                    }

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 * ㅁㅁ
                 * ㅁㅁ
                 */
                if (i+1 < n && j+1 < m) {
                    tmp=0;
                    for (int k = 0; k < 2; k++) {
                        for (int l = 0; l < 2; l++) {
                            tmp += a[i+k][j+l];
                        }
                    }
                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 * ㅁ
                 * ㅁ
                 * ㅁ ㅁ
                 */

                if (i+2 < n && j+1 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+k][j];
                    }
                    tmp += a[i+2][j+1];

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 * ㅁ ㅁ ㅁ
                 * ㅁ
                 */

                if (i+1 < n && j+2 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i][j+k];
                    }
                    tmp += a[i+1][j];

                    if (result < tmp) {
                        result = tmp;
                    }

                }

                /**
                 * ㅁㅁ
                 *  ㅁ
                 *  ㅁ
                 */
                if (i+2 < n && j+1 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+k][j+1];
                    }
                    tmp += a[i][j];

                    if (result < tmp) {
                        result = tmp;
                    }

                }

                /**
                 *    ㅁ
                 * ㅁㅁㅁ
                 */

                if (i+1 < n && j+2 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+1][j+k];
                    }
                    tmp += a[i][j+2];

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 *  ㅁ
                 *  ㅁ
                 * ㅁㅁ
                 */

                if (i+2 < n && j+1 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+k][j+1];
                    }
                    tmp += a[i+2][j];

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 * ㅁ
                 * ㅁㅁㅁ
                 */

                if (i+1 < n && j+2 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+1][j+k];
                    }
                    tmp += a[i][j];

                    if (result < tmp) {
                        result = tmp;
                    }

                }

                /**
                 * ㅁㅁ
                 * ㅁ
                 * ㅁ
                 */
                if (i+2 < n && j+1 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+k][j];
                    }
                    tmp += a[i][j+1];

                    if (result < tmp) {
                        result = tmp;
                    }

                }

                /**
                 * ㅁㅁㅁ
                 *    ㅁ
                 */

                if (i+1 < n && j+2 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i][j+k];
                    }
                    tmp += a[i+1][j+2];

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 * ㅁ
                 * ㅁㅁ
                 *  ㅁ
                 */

                if (i+2 < n && j+1 < m) {
                    tmp = 0;
                    tmp += a[i][j];
                    tmp += a[i+1][j];
                    tmp += a[i+1][j+1];
                    tmp += a[i+2][j+1];

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 *  ㅁㅁ
                 * ㅁㅁ
                 */

                if (i+1 < n && j+2 < m) {
                    tmp = 0;
                    tmp += a[i][j+1];
                    tmp += a[i][j+2];
                    tmp += a[i+1][j];
                    tmp += a[i+1][j+1];

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 *  ㅁ
                 * ㅁㅁ
                 * ㅁ
                 */

                if (i+2 < n && j+1 < m) {
                    tmp = 0;
                    tmp += a[i][j+1];
                    tmp += a[i+1][j];
                    tmp += a[i+1][j+1];
                    tmp += a[i+2][j];

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 * ㅁㅁ
                 *  ㅁㅁ
                 */

                if (i+1 < n && j+2 < m) {
                    tmp = 0;
                    tmp += a[i][j];
                    tmp += a[i][j+1];
                    tmp += a[i+1][j+1];
                    tmp += a[i+1][j+2];

                    if (result < tmp) {
                        result = tmp;
                    }
                }

                /**
                 * ㅁㅁㅁ
                 *  ㅁ
                 */

                if (i +1 < n && j+2 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i][j+k];
                    }
                    tmp += a[i+1][j+1];

                    if (result < tmp) {
                        result = tmp;
                    }

                }

                /**
                 *  ㅁ
                 * ㅁㅁ
                 *  ㅁ
                 */

                if (i + 2 < n && j+1 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+k][j+1];
                    }
                    tmp += a[i+1][j];

                    if (result < tmp) {
                        result = tmp;
                    }

                }

                /**
                 *  ㅁ
                 * ㅁㅁㅁ
                 */

                if (i +1 < n && j+2 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+1][j+k];
                    }
                    tmp += a[i][j+1];

                    if (result < tmp) {
                        result = tmp;
                    }

                }

                /**
                 * ㅁ
                 * ㅁㅁ
                 * ㅁ
                 */

                if (i + 2 < n && j+1 < m) {
                    tmp = 0;
                    for (int k = 0; k < 3; k++) {
                        tmp += a[i+k][j];
                    }
                    tmp += a[i+1][j+1];

                    if (result < tmp) {
                        result = tmp;
                    }

                }
            }
        }

        System.out.println(result);
    }
}
