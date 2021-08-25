package ca.cmpt213.a4.view;

import ca.cmpt213.a4.control.TaskControl;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTaskGUI extends JFrame {
    private JTextField name;
    private JTextField note;
    private JButton submitTaskButton;
    private JPanel panel1;
    private DateTimePicker dateTimePicker;

    public AddTaskGUI(){
        super("Add task");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        submitTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText() != null && note.getText() != null && dateTimePicker.getDateTimeStrict() != null){
                    TaskControl.addTask(name.getText(),note.getText(),dateTimePicker.getDateTimeStrict());
                    dispose();
                }
                else if(name.getText() != null && dateTimePicker.getDateTimeStrict() != null){
                    TaskControl.addTask(name.getText(), "",dateTimePicker.getDateTimeStrict());
                    dispose();
                }
            }
        });
    }
}
