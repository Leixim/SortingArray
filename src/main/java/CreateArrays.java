import java.util.Arrays;
import java.util.Random;

public class CreateArrays {
    Random random = new Random();

    @CreateMethod(info = "Random Array")
    public int[] createRandomArray(int lengthOfArray) {
        int[] arrayOfInt = new int[lengthOfArray];
        for (int i = 0; i < arrayOfInt.length; i++) {
            arrayOfInt[i] = random.nextInt(9);
        }
        return arrayOfInt;
    }

    @CreateMethod(info = "Sorted Array(Max to Min)")
    public int[] createMaxToMinArray(int lengthOfArray) {
        int[] arrayOfInt = new int[lengthOfArray];
        for (int i = 0; i < arrayOfInt.length; i++) {
            arrayOfInt[i] = random.nextInt(9);
        }
        int temp;
        for (int i = 0; i < arrayOfInt.length; i++) {
            for (int j = i + 1; j < arrayOfInt.length; j++) {
                if (arrayOfInt[i] < arrayOfInt[j]) {
                    temp = arrayOfInt[i];
                    arrayOfInt[i] = arrayOfInt[j];
                    arrayOfInt[j] = temp;
                }
            }
        }
        return arrayOfInt;
    }

    @CreateMethod(info = "Sorted Array(Min to Max)")
    public int[] createMinToMaxArray(int lengthOfArray) {
        int[] arrayOfInt = createRandomArray(lengthOfArray);
        Arrays.sort(arrayOfInt);
        return arrayOfInt;
    }

    @CreateMethod(info = "Sorted Array(Min to Max) + 1")
    public int[] createMinToMaxArrayPlus(int lengthOfArray) {
        Random random = new Random();
        int[] arrayOfInt = createMinToMaxArray(lengthOfArray);
        int[] arrayOfIntPlus = Arrays.copyOf(arrayOfInt, arrayOfInt.length + 1);
        arrayOfIntPlus[arrayOfIntPlus.length - 1] = random.nextInt(9);
        return arrayOfIntPlus;
    }
}
