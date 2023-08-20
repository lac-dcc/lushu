#include <stdio.h>

int main() {
    int n = 3;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                printf("i: %d, j: %d, k: %d\n", i, j, k);
            }
        }
    }

    int m = 2;

    for (int a = 0; a < m; a++) {
        for (int b = 0; b < m; b++) {
            for (int c = 0; c < m; c++) {
                for (int d = 0; d < m; d++) {
                    printf("(%d, %d, %d, %d) ", a, b, c, d);
                }
                printf("\n");
            }
        }
    }

    int rows = 3;
    int cols = 4;

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("(%d, %d) ", i, j);
        }
        printf("\n");
    }

    return 0;
}
