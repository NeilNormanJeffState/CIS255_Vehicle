data.csv - The basic data for vehicle Type, Make, Model, Year, EngineType, Color, Doors, and Transmission in comma-separated value format.

HW_Vehicle.gpj - A JGrasp Project file including all of the Java files in this project

Program.java - Uses the subsequent Java files to find the correct CSV file. It disallows other types of files and has exceptions for if the UI is closed or an error is selected. It invokes the CarSelectorGUI once a CSV is selected.

CarSelectorGUI - Creates a GUI based on the data.csv about vehicles. It allows the user to view all vehicle possibilities and lets them select options on their own. The user can save their options at any point, or it will automatically save when they reach the last option. The exit option exits the program.

VehicleFileFilter.java - Crreates the User Interface to select the CSV file manually.

VehicleFileFilters.java - Creates a file filter for CSV files.

java version "1.8.0_391"
Java(TM) SE Runtime Environment (build 1.8.0_391-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.391-b13, mixed mode)

IDE to edit code - JGrasp