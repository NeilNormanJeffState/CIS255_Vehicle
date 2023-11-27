import javax.swing.*;
import java.awt.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CarSelectorGUI {
    private String csvFilePath;
    private static final String EXIT = "Exit";
    public CarSelectorGUI(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    private JFrame frame;
    private List<List<String>> carData;
    private List<String> previousSelections;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            CarSelectorGUI carSelectorGUI = new CarSelectorGUI("");
            carSelectorGUI.initialize();
        });
        
    }

    public void initialize() {
        frame = new JFrame("Car Selector");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        carData = readCSV();
        if (carData == null) {
            JOptionPane.showMessageDialog(frame, "Failed to read CSV file. Exiting.");
            System.exit(0);
        }

        previousSelections = new ArrayList<>();

        createMenuBar();

        frame.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu selectMenu = new JMenu("Select Vehicles");
        JMenuItem saveToFileItem = new JMenuItem("Save To File");
        JMenuItem exitItem = new JMenuItem(EXIT);

        menuBar.add(selectMenu);
        menuBar.add(createShowVehiclesMenuItem());
        menuBar.add(saveToFileItem);
        menuBar.add(exitItem);

        JMenuItem[] selectMenuItems = createSelectMenuItems();
        selectMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                clearPreviousSelections();
                showVehicleSelectionDialog();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        for (JMenuItem item : selectMenuItems) {
            selectMenu.add(item);
        }

        saveToFileItem.addActionListener(e -> saveToFile());
        exitItem.addActionListener(e -> exitProgram());

        frame.setJMenuBar(menuBar);
    }

    private JMenuItem createShowVehiclesMenuItem() {
        JMenuItem showVehiclesItem = new JMenuItem("Show Vehicles");
        showVehiclesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showVehicles();
            }
        });
        return showVehiclesItem;
    }

    private JMenuItem[] createSelectMenuItems() {
        List<JMenuItem> items = new ArrayList<>();

        for (String category : carData.get(0)) {
            JMenuItem item = new JMenuItem(category);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String category = ((JMenuItem) e.getSource()).getText();
                    previousSelections.add(category);
                    showVehicleSelectionDialog();
                }
            });
            items.add(item);
        }

        return items.toArray(new JMenuItem[0]);
    }

    private void showVehicleSelectionDialog() {
        JMenu currentMenu = new JMenu("Select " + carData.get(0).get(previousSelections.size()));
        currentMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                createSubMenu(currentMenu);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        JMenuBar menuBar = frame.getJMenuBar();
        menuBar.remove(0); // Remove the old "Select Vehicles" menu
        menuBar.add(currentMenu, 0); // Add the new menu
        frame.setJMenuBar(menuBar);
    }

    private void createSubMenu(JMenu currentMenu) {
        currentMenu.removeAll();

        int columnIndex = previousSelections.size();
        Set<String> filteredValues = getFilteredValues(carData, columnIndex, previousSelections);

        for (String value : filteredValues) {
            JMenuItem item = new JMenuItem(value);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String value = ((JMenuItem) e.getSource()).getText();
                    previousSelections.add(value);
                    if (previousSelections.size() < carData.get(0).size()) {
                        showVehicleSelectionDialog();
                    } else {
                        displayCarInformation();
                        saveToFile();
                        clearPreviousSelections();
                    }
                }
            });
            currentMenu.add(item);
        }
    }

    private Set<String> getFilteredValues(List<List<String>> data, int columnIndex, List<String> previousSelections) {
        Set<String> values = new HashSet<>();
        for (int i = 1; i < data.size(); i++) {
            boolean isValid = true;
            for (int j = 0; j < previousSelections.size(); j++) {
                if (!data.get(i).get(j).equalsIgnoreCase(previousSelections.get(j))) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                values.add(data.get(i).get(columnIndex));
            }
        }
        return values;
    }

    private void displayCarInformation() {
        StringBuilder info = new StringBuilder("Selected Car Information:\n");
        int index = -1;
        for (int i = 1; i < carData.size(); i++) {
            if (matchesSelections(carData.get(i))) {
                index = i;
                break;
            }
        }
        //For all possible car combinations for 
        if (index != -1) {
            for (int i = 0; i < carData.get(0).size(); i++) {
                info.append(carData.get(0).get(i)).append(": ").append(carData.get(index).get(i)).append("\n");
            }
        }
        
        JTextArea textArea = new JTextArea(info.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(frame, scrollPane, "Car Options", JOptionPane.PLAIN_MESSAGE);
    }

    private boolean matchesSelections(List<String> selections) {
        for (int j = 0; j < previousSelections.size(); j++) {
            if (!selections.get(j).equalsIgnoreCase(previousSelections.get(j))) {
                return false;
            }
        }
        return true;
    }

    private void showVehicles() {
        StringBuilder info = new StringBuilder("Vehicle Information:\n");

        for (List<String> row : carData) {
            for (int i = 0; i < carData.get(0).size(); i++) {
                info.append(carData.get(0).get(i)).append(": ").append(row.get(i)).append("\n");
            }
            info.append("\n");
        }

        JTextArea textArea = new JTextArea(info.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(frame, scrollPane, "Car Options", JOptionPane.PLAIN_MESSAGE);
    }

    private void saveToFile() {
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        try (FileWriter writer = new FileWriter("Vehicle_Selection_" + dateTime + ".csv")) {
            // Write the header
            writer.write(String.join(",", carData.get(0)) + "\n");

            // Write the user's selections
            writer.write(String.join(",", previousSelections) + "\n");
            JOptionPane.showMessageDialog(frame, "Selected car information saved to 'Vehicle_Selection_" + dateTime + ".csv'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitProgram() {
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        if (!previousSelections.isEmpty()) {
            saveToFile();
        }
        JOptionPane.showMessageDialog(frame, "Exiting program.");
        System.exit(0);
    }

    private List<List<String>> readCSV() {
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> row = Arrays.asList(line.split(","));
                data.add(row);
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void clearPreviousSelections() {
        previousSelections.clear();
    }
}