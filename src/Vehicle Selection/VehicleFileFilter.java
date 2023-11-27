import java.io.File;
import javax.swing.filechooser.FileFilter;

public class VehicleFileFilter extends FileFilter {
   private String _ext; // File Extension; used to filter the folder view.
   private String _descr;  // Description of File Extension; seen visually in drop-down

   public VehicleFileFilter(){
      this(".csv", "Comma Seperated File");
   }
   public VehicleFileFilter(String ext, String descr){
      super();
      this._ext = ext;
      this._descr = descr;
   }
   
   public boolean accept(File fl, String nm) {
      boolean res = false;
      
      if(nm.lastIndexOf(this._ext) > 0) {
         res = true;
      } 
      
      return res;
   }
   public boolean accept(File fl){
      boolean res = false;
      
      if(fl != null){
         if(fl.isDirectory()) {
            res |= true;
         }
         
         res |= this.accept(fl, fl.getName());
      }
      
      return res;
   }
   
   public String getDescription(){
      return String.format("%s (*%s)", this._descr, this._ext);
   }
}