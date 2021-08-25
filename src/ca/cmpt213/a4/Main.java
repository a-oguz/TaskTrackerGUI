package ca.cmpt213.a4;

import ca.cmpt213.a4.control.TaskControl;
import ca.cmpt213.a4.view.GUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TaskControl.StartProgram();
                GUI app = new GUI();
                app.setVisible(true);
            }
        });
    }
}
