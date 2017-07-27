import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Reflection {
    static int countOfRunArray = 15;//count of sort runs for measuring different array sizes
    static int countOfSortMethod = 0;
    static int countOfCreateMethod = 0;

    public static ArrayList<ContainerOfSortTime> TimeOfSortArray() throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        Class createArraysClass = CreateArrays.class;
        Class sortArraysClass = SortArrays.class;

        ArrayList<ContainerOfSortTime> arrayInfoContainers = new ArrayList<>();

        Method[] sortArraysMethod = sortArraysClass.getDeclaredMethods();
        Object instanceOfSortArray = sortArraysClass.newInstance();
        Method[] createArraysMethod = createArraysClass.getDeclaredMethods();
        Object instanceOfCreateArray = createArraysClass.newInstance();

        for (Method createMethod : createArraysMethod) {
            if (createMethod.isAnnotationPresent(CreateMethod.class)) {
                countOfCreateMethod++;
                int sizeOfGeneratedArray = 1000;
                for (int i = 0; i < countOfRunArray; i++) {
                    int[] array = (int[]) createMethod.invoke(instanceOfCreateArray, sizeOfGeneratedArray);
                    for (Method sortMethod : sortArraysMethod) {
                        if (sortMethod.isAnnotationPresent(SortMethod.class)) {

                            ContainerOfSortTime infoContainer = new ContainerOfSortTime(); //create container to save info about sort
                            long timeOfSort = (long) sortMethod.invoke(instanceOfSortArray, (Object) array.clone());
                            infoContainer.setNameOfSort(sortMethod.getAnnotation(SortMethod.class).info());
                            infoContainer.setTypeOfArray(createMethod.getAnnotation(CreateMethod.class).info());
                            infoContainer.setTimeOfSort(timeOfSort);
                            infoContainer.setLengthOfArray(array.length);
                            arrayInfoContainers.add(infoContainer);
                        }
                    }
                    sizeOfGeneratedArray +=1000 ;
                }
                System.out.println();
            }
        }
        countOfSortMethod = arrayInfoContainers.size() / (countOfCreateMethod * countOfRunArray);
        return arrayInfoContainers;
    }

}

