package action;

public class BubblesortMethods {

    public int[] arr;

    /***
     * intoarce vector sortat crescator
     * @param ar vector de sortat
     */
    public final void bubbleSort(final int[] ar) {
        int n = ar.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ar[j] < ar[j + 1]) {
                    int temp = ar[j];
                    ar[j] = ar[j + 1];
                    ar[j + 1] = temp;
                }
            }
        }
    }

    /***
     * intoarce vector sortat desccrescator
     * @param ar vector de sortat
     */
    public final void bubbleSortdesc(final int[] ar) {
        int n = ar.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ar[j] > ar[j + 1]) {
                    int temp = ar[j];
                    ar[j] = ar[j + 1];
                    ar[j + 1] = temp;
                }
            }
        }
    }

    /***
     * intoarce vector sortat descrescator
     * @param ar vector de sortat
     */
    public final void bubbleSortdescdouble(final double[] ar) {
        int n = ar.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ar[j] < ar[j + 1]) {
                    double temp = ar[j];
                    ar[j] = ar[j + 1];
                    ar[j + 1] = temp;
                }
            }
        }
    }

    /***
     * intoarce vector sortat crescator
     * @param ar vector de sortat
     */
    public final void bubbleSortascdouble(final double[] ar) {
        int n = ar.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ar[j] > ar[j + 1]) {
                    double temp = ar[j];
                    ar[j] = ar[j + 1];
                    ar[j + 1] = temp;
                }
            }
        }
    }

    public final int[] getArr() {
        return arr;
    }

    public final void setArr(final int[] arr) {
        this.arr = arr;
    }
}
