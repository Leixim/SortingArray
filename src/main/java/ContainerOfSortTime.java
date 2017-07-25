public class ContainerOfSortTime {

    private String typeOfArray;
    private String nameOfSort;
    private long timeOfSort;
    private int lengthOfArray;


    public String getNameOfSort() {
        return nameOfSort;
    }

    public void setNameOfSort(String nameOfSort) {
        this.nameOfSort = nameOfSort;
    }

    public String getTypeOfArray() {
        return typeOfArray;
    }

    public void setTypeOfArray(String typeOfArray) {
        this.typeOfArray = typeOfArray;
    }

    public long getTimeOfSort() {
        return timeOfSort;
    }

    public void setTimeOfSort(long timeOfSort) {
        this.timeOfSort = timeOfSort;
    }

    public int getLengthOfArray() {
        return lengthOfArray;
    }

    public void setLengthOfArray(int lengthOfArray) {
        this.lengthOfArray = lengthOfArray;
    }

    @Override
    public String toString() {
        return "ContainerOfSortTime{" +
                "typeOfArray='" + typeOfArray + '\'' +
                ", nameOfSort='" + nameOfSort + '\'' +
                ", timeOfSort=" + timeOfSort +
                ", lengthOfArray=" + lengthOfArray +
                '}';
    }
}
