import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader("C:/Users/Admin/Documents/GitHub/CIS255_Vehicle/src/data.csv");
        List<Car> cars = csvReader.getCars();

        Menu menu = new Menu(cars);
        menu.navigateMenu();
    }
}
