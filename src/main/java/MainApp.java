
public class MainApp {
    public static void main(String[] args) throws Exception {
        CreateFillExcel createFillExcel = new CreateFillExcel();
        createFillExcel.setOutputFilename("Arrays");
        createFillExcel.writeValueToExcel(Reflection.TimeOfSortArray());
    }


}

