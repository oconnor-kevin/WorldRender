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
import java.util.HashMap;

public class WorldRenderTest {
    
    public static void main(String[] args){
        
// Testing the collisionCheck method
/*        
        Particle part1 = new Particle(1, new Vector(new double[]{0.0,0.0,0.0}), new Vector(new double[]{0.0,0.0,1.0}));
        Particle part2 = new Particle(2, new Vector(new double[]{0.0,5.0,5.0}), new Vector(new double[]{0.0,0.0,0.0}));
        
        ArrayList<Particle> particles1 = new ArrayList<>();
        ArrayList<Particle> particles2 = new ArrayList<>();
        
        particles1.add(part1);
        particles2.add(part2);
        
        Matter matt1 = new Matter(particles1);
        Matter matt2 = new Matter(particles2);
        
        ObjectSpace os = new ObjectSpace();
        os.addMatter(matt1);
        os.addMatter(matt2);
        
        for (int i = 0; i<10; i++){
            System.out.println("Collision will occur? " + os.collisionCheck(1.0)); 
            os.incrementTime(1.0);        
            System.out.println(ObjectSpace.printObjectSpace(os));      
        }
*/
        
// Testing the ComplexNumber class
        ComplexNumber a = new ComplexNumber(5.0, 6.0);
        ComplexNumber b = new ComplexNumber(10.0, 3.0);
        
        System.out.println(ComplexNumber.printComplexNumber(ComplexNumber.toPower(a,2)));
        

        
        
    }
}
