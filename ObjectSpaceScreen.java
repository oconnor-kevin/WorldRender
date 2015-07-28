/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinoconnor
 */

import javax.swing.*;
import java.awt.*;

public class ObjectSpaceScreen extends JFrame {
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    
    
    public ObjectSpaceScreen(){
        setSize(300, 200);
        setTitle("WorldRender");
        setResizable(true);
        setBounds((int) (screenWidth*0.1), (int) (screenHeight*0.1), (int) (screenWidth*0.8), (int) (screenHeight*0.8));
    }
}
