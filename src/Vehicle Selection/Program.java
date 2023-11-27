import javax.swing.JFileChooser;
import javax.swing.JDialog;
import java.awt.Window;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class Program {
   public static void main(String[] args){
      File fl = Init();
      //System.out.println(fl.getName()); // DEBUG: REMOVE before Submission
      String csvFilePath = fl.getAbsolutePath();
      // Instantiate your Application Class and start Processing the data (@fl)
      if (fl !=null){    
      CarSelectorGUI carSelectorGUI = new CarSelectorGUI(csvFilePath);
            carSelectorGUI.initialize();
      }
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
         
         return fl;
      }
   }
}