// CSVReader.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }
        CSVReader(String filePath) {
        this.cars = new ArrayList<>();
        readCSV(filePath);
    }

    private void readCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Car car = new Car(data[0], data[1], data[2], Integer.parseInt(data[3]));
                cars.add(car);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getCars() {
        return cars;
    }
}
