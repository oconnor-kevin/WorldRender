/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinoconnor
 */
public class MassEquivalent 
{
// Fields
    private String unit;
    
// Constructors
    public MassEquivalent(String un){
        unit = un;
    }
    
    public MassEquivalent(){
        unit = "";
    }
    
// Accessor Methods
    public String getUnit(){
        return unit;
    }
    
// Mutator Methods
    public void setUnit(String newUnit){
        unit = newUnit;
    }
    
    
}
