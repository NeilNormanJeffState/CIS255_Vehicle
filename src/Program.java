import javax.swing.JFileChooser;
import javax.swing.JDialog;
import java.awt.Window;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Program {
   public static void main(String[] args){
      File fl = Init();
      //System.out.println(fl.getName()); // DEBUG: REMOVE before Submission
     
      // Instantiate your Application Class and start Processing the data (@fl)    
           
   }
   
   private static File Init() {
      JFileChooser chooser = new JFileChooser();
      chooser.setAcceptAllFileFilterUsed(false);   // Disable "All Files" filter
      chooser.setMultiSelectionEnabled(false); // Single File selection
      // Set the File Filter(s)
      VehicleFileFilters fltrs = new VehicleFileFilters();      
      for (FileFilter fltr : fltrs.Items()){
         chooser.setFileFilter(fltr);
      }
      JDialog dlg = new JDialog((Window) null);
      dlg.setVisible(true);
      // Show File Chooser and capture the Action Result.
      int res = chooser.showOpenDialog(dlg);
      
      // Check User choice scenarios
      if(res != JFileChooser.APPROVE_OPTION){
         // Close, Cancel or Error selected
         dlg.dispose();
         return null;
      } else {
         // File Was Chosen
         File fl = chooser.getSelectedFile(); // The File Selected
         dlg.dispose();
         String line = null;
         try{
         BufferedReader br = new BufferedReader(new FileReader(fl));
         while((line = br.readLine()) != null) {
         final String UTF8_BOM = "\uFEFF";
         // Removes the question mark symbol form the output
         if (line.startsWith(UTF8_BOM)) {
             line = line.substring(1);
         }
            String[] values = line.split(",");
            // Prints all values line by line and separates them with spaces
            
            System.out.println(values[0] + " " + values[1] + " " + values[2] + " " + values[3] + " " + values[4] + " " + values[5] + " " + values[6] + " " + values[7] );
            }
}
         catch (FileNotFoundException e) {
         e.printStackTrace();
         }
         catch (IOException e) {
         e.printStackTrace();
         }
         //return fl;
         return null;
      }
   }
}