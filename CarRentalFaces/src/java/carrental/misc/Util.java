/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.misc;

import carrental.model.Model;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ivan
 */
public final class Util {
    private Util() { }
    
    public static SelectItem[] getSelectItems(List<? extends Model> objects) {
        SelectItem[] items = new SelectItem[objects.size()];
        for(int i=0; i<objects.size(); i++) {
            items[i] = new SelectItem(objects.get(i), objects.get(i).toString());
        }
        
        return items;
    }
}
