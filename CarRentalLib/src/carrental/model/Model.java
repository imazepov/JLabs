/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model;

import carrental.misc.IdField;

/**
 *
 * @author ivan
 */
public class Model {
    @IdField
    protected int id;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == null) {
            return false;
        }
        
        if(other.getClass() != getClass()) {
            return false;
        }
        
        Model otherModel = (Model)other;
        return getId() == otherModel.getId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        return hash;
    }
}
