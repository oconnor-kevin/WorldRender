/* 
    Author: Kevin O'Connor 
    Email: worldrenderengine@gmail.com
    Summer 2015
*/


import javax.swing.*;
import java.awt.*;

public class ObjectSpaceScreen extends JFrame {
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;
    
    
    public ObjectSpaceScreen(){
        setTitle("WorldRender");
        setResizable(true);
        setBounds((int) (screenWidth*0.1), (int) (screenHeight*0.1), (int) (screenWidth*0.8), (int) (screenHeight*0.8));
    }
}
