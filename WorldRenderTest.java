/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinoconnor
 */
import linearalgebra.*;
import java.util.ArrayList;

public class WorldRenderTest {
    
    public static void main(String[] args){
        
        Particle b = new Particle(1, new Vector(3), new Vector(new double[]{0.0, 2.0, 0.0}));
        Particle c = new Particle(5, new Vector(new double[]{0.0, 4.0, 0.0}), new Vector(3));
        
        ArrayList<Particle> a = new ArrayList<Particle>();
        a.add(b);
        a.add(c);
        
        Matter d = new Matter(a);
        ArrayList<Matter> e = new ArrayList<Matter>();
        e.add(d);
            
        
        ObjectSpace f = new ObjectSpace(e);
        
        System.out.println(ObjectSpace.printObjectSpace(f));
        
        f.incrementTime(10.0);
        
        System.out.println(ObjectSpace.printObjectSpace(f));
    }
}
