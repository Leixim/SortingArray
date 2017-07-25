import java.util.Arrays;

public class SortArrays {

    @SortMethod(info = "Bubble Sort (Down)")
    public long bubbleDownSortArray(int[] arrayInt) {
        long timeBefore = System.nanoTime();
        int length = arrayInt.length;
        int temp = 0;
        for (int i = 0; i < length; i++) {
            for (int j = (length - 1); j > 0; j--) {
                if (arrayInt[j] < arrayInt[j - 1]) {
                    temp = arrayInt[j - 1];
                    arrayInt[j - 1] = arrayInt[j];
                    arrayInt[j] = temp;
                }
            }
        }
        return (System.nanoTime() - timeBefore);
    }

    @SortMethod(info = "Bubble Sort (Up)")
    public long bubbleUpSortArray(int[] arrayInt) {
        long timeBefore = System.nanoTime();
        int length = arrayInt.length;
        int temp = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 1; j < (length - i); j++) {
                if (arrayInt[j - 1] > arrayInt[j]) {
                    temp = arrayInt[j - 1];
                    arrayInt[j - 1] = arrayInt[j];
                    arrayInt[j] = temp;
                }
            }
        }
        return (System.nanoTime() - timeBefore);
    }

    @SortMethod(info = "Native Sort")
    public long nativeSortArray(int[] arrayInt) {
        long timeBefore = System.nanoTime();
        Arrays.sort(arrayInt);
        return (System.nanoTime() - timeBefore);
    }

    @SortMethod(info = "Selection Sort")
    public long selectionSortArray(int[] arrayInt) {
        long timeBefore = System.nanoTime();
        int min, temp;
        for (int i = 0; i < arrayInt.length - 1; i++) {
            min = i;
            for (int scan = i + 1; scan < arrayInt.length; scan++) {
                if (arrayInt[scan] < arrayInt[min])
                    min = scan;
            }
            temp = arrayInt[min];
            arrayInt[min] = arrayInt[i];
            arrayInt[i] = temp;
        }
        return (System.nanoTime() - timeBefore);
    }

    @SortMethod(info = "Merge Sort")
    public long mergeSortArray(int[] arrayInt) {
        long timeBefore = System.nanoTime();
        int length = arrayInt.length;
        int n = 1;
        int shift;
        int two_size;
        int[] arr2;
        while (n < length) {
            shift = 0;
            while (shift < length) {
                if (shift + n >= length) break;
                two_size = (shift + n * 2 > length) ? (length - (shift + n)) : n;
                arr2 = merge(Arrays.copyOfRange(arrayInt, shift, shift + n),
                        Arrays.copyOfRange(arrayInt, shift + n, shift + n + two_size));
                for (int i = 0; i < n + two_size; ++i)
                    arrayInt[shift + i] = arr2[i];
                shift += n * 2;
            }
            n *= 2;
        }
        return (System.nanoTime() - timeBefore);
    }

    private static int[] merge(int[] arr_1, int[] arr_2) {
        int len_1 = arr_1.length, len_2 = arr_2.length;
        int a = 0, b = 0, len = len_1 + len_2;
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            if (b < len_2 && a < len_1) {
                if (arr_1[a] > arr_2[b]) result[i] = arr_2[b++];
                else result[i] = arr_1[a++];
            } else if (b < len_2) {
                result[i] = arr_2[b++];
            } else {
                result[i] = arr_1[a++];
            }
        }
        return result;
    }
}
