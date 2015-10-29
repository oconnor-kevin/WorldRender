/*
 * The MIT License
 *
 * Copyright 2015 kevinoconnor.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 *
 * @author kevinoconnor
 */

import java.io.PrintWriter;
import java.io.IOException;
import linearalgebra.*;

public class WorldRender {

// Fields:
    String outputFile;
    String inputFile;
    boolean saving;
    ObjectSpace os;
    PrintWriter write;
    
// Constructors:
    public WorldRender(String outFile, boolean sav){
        outputFile = outFile;
        inputFile = "";
        saving = sav;
        os = new ObjectSpace();
        try {
            write = new PrintWriter(outFile);
        } catch (IOException e){}
    }
    
    public WorldRender(String inFile, boolean sav, String outFile){
        outputFile = outFile;
        inputFile = inFile;
        saving = sav;
        os = new ObjectSpace();
//      os.parseFile(inputFile);
        try {
            write = new PrintWriter(outFile);
        } catch (IOException e){}
    }
    
    public WorldRender(ObjectSpace obsp){
        outputFile = "";
        inputFile = "";
        saving = false;
        os = obsp;
    }
    
// Accessor Methods:
    public String getOutputFile(){
        return outputFile;
    }
    
    public String getInputFile(){
        return inputFile;
    }
    
    public boolean getSaving(){
        return saving;
    }
    
    public ObjectSpace getObjectSpace(){
        return os;
    }
    
    public PrintWriter getWrite(){
        return write;
    }
    
// Mutator Methods:
    public void setOutputFile(String newFile){
        outputFile = newFile;
    }
    
    public void setInputFile(String newFile){
        inputFile = newFile;
    }
    
    public void setSaving(boolean save){
        saving = save;
    }
    
    public void setObjectSpace(ObjectSpace ob){
        os = ob;
    }
    
    public void setWrite(PrintWriter newWrite){
        write = newWrite;
    }
  
// Methods:
    public void writeToFile(){
        write.println(os.printObjectSpaceState());
    }
    
    public void writePropertiesToFile(){
        write.println(os.printObjectSpaceProperties());
    }
    
    
    
    
    
// Testing:
    public static void main(String[] args){
        Particle part1 = new Particle(new Vector(-1.0, 0.0, 0.0), new Vector(3));
        Particle part2 = new Particle(new Vector(1.0, 0.0, 0.0), new Vector(3));
        part1.addMassEquivalentValue("Mass", 1.0);
        part2.addMassEquivalentValue("Mass", 1.0);
        part1.addMassEquivalentValue("Charge", 2.0);
        part2.addMassEquivalentValue("Charge", -2.0);
        
        Matter mat1 = new Matter();
        mat1.addParticle(part1);
        mat1.addParticle(part2);
        mat1.setFix(false);
        mat1.setPosition(new Vector(3));
        mat1.setVelocity(new Vector(0.0, 1.0, 1.0));
        mat1.setRotation(new Vector(0.0, 0.0, 0.0));
        mat1.fillCenters();
        mat1.fillME();
        mat1.calcMomIn();
        mat1.setRotationOrigin(mat1.getCenter("Mass"));
        
        ObjectSpace os = new ObjectSpace();
        os.addMatter(mat1);
        os.setTimeInc(0.01);
        os.addForce(new LinearForce(100.0, "Mass", new Vector(1.0, 0.0, 0.0)));
        os.addForce(new LinearForce(-400.0, "Charge", new Vector(0.0, 1.0, 0.0)));
        
        WorldRender world = new WorldRender(os);
        world.setSaving(true);
        world.setOutputFile("testoutput.txt");
        try{
        PrintWriter writer = new PrintWriter(world.outputFile);
        world.setWrite(writer);
        } catch (IOException e){}
        world.writePropertiesToFile();
        world.writeToFile();
        for (int i = 0; i < 500; i++){
            world.os.timeStep();
            world.writeToFile();
        }
        world.write.flush();
        world.write.close();
        
    }
    
}
