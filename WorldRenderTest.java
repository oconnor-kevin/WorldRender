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

public class WorldRenderTest {
    
    public static void main(String[] args){
        ObjectSpace a = new ObjectSpace(1, 50, 50);
        
        Particle b = new Particle(1, new Vector(3), new Vector(new double[]{0.0, 2.0, 0.0}));
        Particle c = new Particle(5, new Vector(new double[]{0.0, 4.0, 0.0}), new Vector(3));
        
        a.addParticle(b);
        a.addParticle(c);
        
        System.out.println(a.print2D(0));
        
        System.out.println(a.getActiveParticles().size());
        System.out.println(a.getPastActiveParticles().size());
        
        a.incrementTime();
        
        System.out.println(a.getActiveParticles().size());
        System.out.println(a.getPastActiveParticles().size());
        
        a.updateObjectSpace();
        
    
        
        System.out.println(a.print2D(0));
       
    }
}
