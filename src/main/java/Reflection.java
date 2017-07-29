
import com.sun.istack.internal.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Reflection {
    static int countOfRunArray = 15;//count of sort runs for measuring different array sizes
    static int countOfSortMethod = 0;
    static int countOfCreateMethod = 0;

    public static ArrayList<ContainerOfSortTime> TimeOfSortArray() throws IllegalAccessException, InstantiationException {
        Class createArraysClass = CreateArrays.class;
        Class sortArraysClass = SortArrays.class;
        ArrayList<ContainerOfSortTime> arrayInfoContainers = new ArrayList<>();
        Method[] sortArraysMethod = sortArraysClass.getDeclaredMethods();
        Object instanceOfSortArray = sortArraysClass.newInstance();
        Method[] createArraysMethod = createArraysClass.getDeclaredMethods();
        Object instanceOfCreateArray = createArraysClass.newInstance();
        System.out.print("LOADING");
        for (Method createMethod : createArraysMethod) {
            if (createMethod.isAnnotationPresent(CreateMethod.class)) {
                countOfCreateMethod++;
                int sizeOfGeneratedArray = 1000;

                for (int i = 0; i < countOfRunArray; i++) {
                    int[] array = new int[0];
                    try {
                        array = (int[]) createMethod.invoke(instanceOfCreateArray, sizeOfGeneratedArray);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    for (Method sortMethod : sortArraysMethod) {
                        if (sortMethod.isAnnotationPresent(SortMethod.class)) {
                            ContainerOfSortTime infoContainer = new ContainerOfSortTime(); //create container to save info about sort
                            long timeOfSort = 0;
                            try {
                                timeOfSort = (long) sortMethod.invoke(instanceOfSortArray, (Object) array.clone());
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            infoContainer.setNameOfSort(sortMethod.getAnnotation(SortMethod.class).info());
                            infoContainer.setTypeOfArray(createMethod.getAnnotation(CreateMethod.class).info());
                            infoContainer.setTimeOfSort(timeOfSort);
                            infoContainer.setLengthOfArray(array.length);
                            arrayInfoContainers.add(infoContainer);
                        }
                    }
                    sizeOfGeneratedArray +=1000 ;
                    System.out.print(".");
                }
            }
        }
        System.out.println();
        countOfSortMethod = arrayInfoContainers.size() / (countOfCreateMethod * countOfRunArray);
        return arrayInfoContainers;
    }

}

