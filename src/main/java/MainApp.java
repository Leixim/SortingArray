import java.util.ArrayList;

public class MainApp {
    public static void main(String[] args) throws Exception {
        ArrayList<ContainerOfSortTime> containerOfSortTimes = Reflection.TimeOfSortArray();
        for (ContainerOfSortTime containerOfSortTime : containerOfSortTimes) {
            System.out.println(containerOfSortTime);
        }
        CreateFillExcel.writeValueToExcel(containerOfSortTimes);
    }


}

