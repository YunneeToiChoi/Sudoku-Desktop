
package com.mycompany.sudokuswing;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FunctionButton extends JButton{

    public FunctionButton(ImageIcon icon) {
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder());
        setIcon(icon);
    }
    
    
}
