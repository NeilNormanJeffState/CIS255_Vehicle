import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class VehicleFileFilters {
   public VehicleFileFilters(){
      _fltrs.add(new VehicleFileFilter());
   }
   public VehicleFileFilters(VehicleFileFilter... fltrs){
      // Use Arrays object to convert @fltrs parameter into List
      this._fltrs = Arrays.asList(fltrs);
   }
   
   private List<VehicleFileFilter> _fltrs = new ArrayList<VehicleFileFilter>(); // File Filters Collection
   
   public List<VehicleFileFilter> Items () {
      return _fltrs;
   }
}