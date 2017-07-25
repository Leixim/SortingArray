import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Reflection {

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
//            if (createMethod.getParameterCount() == 1 && createMethod.getParameters()[0].toString().equals("int arg0")) {
                int sizeOfGeneratedArray = 500;
                for (int i = 0; i < 8; i++) {
                    int[] array = (int[]) createMethod.invoke(instanceOfCreateArray, sizeOfGeneratedArray);
                    for (Method sortMethod : sortArraysMethod) {
                        if (sortMethod.isAnnotationPresent(SortMethod.class)) {
//                    if (sortMethod != null && sortMethod.getParameterCount() == 1 && sortMethod.getParameters()[0].toString().equals("int[] arg0")) {
                            ContainerOfSortTime infoContainer = new ContainerOfSortTime(); //create container to save info about sort
                            long timeOfSort = (long) sortMethod.invoke(instanceOfSortArray, (Object) array.clone());
                            infoContainer.setNameOfSort(sortMethod.getAnnotation(SortMethod.class).info());
                            infoContainer.setTypeOfArray(createMethod.getAnnotation(CreateMethod.class).info());
                            infoContainer.setTimeOfSort(timeOfSort);
                            infoContainer.setLengthOfArray(array.length);
                            arrayInfoContainers.add(infoContainer);
                        }
                    }
                    sizeOfGeneratedArray += 1000;
                }
                System.out.println();
            }
        }

        return arrayInfoContainers;
    }

}

