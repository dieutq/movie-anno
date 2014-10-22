/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieannot.BasicModel;

/**
 *
 * @author dieutq
 */
import javax.swing.*;
import java.awt.event.*;

public class PopUpMenu {

    JPopupMenu Pmenu;
    JMenuItem menuItem;

    public PopUpMenu(JProgressBar jp) {
        //JFrame frame = new JFrame("Creating a Popup Menu");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Pmenu = new JPopupMenu();
        menuItem = new JMenuItem("Cut");
        Pmenu.add(menuItem);
        menuItem = new JMenuItem("Copy");
        Pmenu.add(menuItem);
        menuItem = new JMenuItem("Paste");
        Pmenu.add(menuItem);
        menuItem = new JMenuItem("Delete");
        Pmenu.add(menuItem);
        menuItem = new JMenuItem("Undo");
        Pmenu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        jp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent Me) {
                if (Me.isPopupTrigger()) {
                    Pmenu.show(Me.getComponent(), Me.getX(), Me.getY());
                }
            }
        });
        //frame.setSize(400, 400);
        //frame.setVisible(true);
    }
}
