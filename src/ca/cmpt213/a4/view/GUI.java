package ca.cmpt213.a4.view;

import ca.cmpt213.a4.control.TaskControl;
import ca.cmpt213.a4.model.Task;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GUI extends JFrame{

    private AddTaskGUI addTaskWindow;

    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton addTaskButton;
    private JTextField name;
    private JCheckBox completionStatus;
    private JTextField note;
    private JTextField dueDate;
    private JList allList;
    private JList overdueList;
    private JList upcomingList;
    private JButton removeTask;
    private DefaultListModel allListModel;
    private DefaultListModel overdueListModel;
    private DefaultListModel upcomingListModel;


    private int currentListTab= -1; // allList: 0, overdueList: 1, upcomingList: 2, undef:-1
    private int currentSelectedIndex= -1; // not selected: -1


    public GUI(){
        super("Task Tracker");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();

        this.allListModel= new DefaultListModel();
        this.overdueListModel= new DefaultListModel();
        this.upcomingListModel= new DefaultListModel();

        this.allList.setModel(allListModel);
        this.overdueList.setModel(overdueListModel);
        this.upcomingList.setModel(upcomingListModel);

        refreshLists();

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addTaskWindow == null) {
                    addTaskWindow = new AddTaskGUI();

                    addTaskWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            refreshLists();
                        }
                    });

                } else if (!addTaskWindow.isDisplayable()) {
                    addTaskWindow = new AddTaskGUI();

                    addTaskWindow.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            refreshLists();
                        }
                    });
                }
            }
        });
        allList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                currentListTab = 0;
                if (allList.getSelectedIndex() != -1) {
                    currentSelectedIndex = allList.getSelectedIndex();
                    name.setText(TaskControl.listAllTask().get(currentSelectedIndex).getName());
                    note.setText(TaskControl.listAllTask().get(currentSelectedIndex).getNotes());
                    dueDate.setText(TaskControl.listAllTask().get(currentSelectedIndex).getDueDate().getTime().toLocaleString());
                    completionStatus.setSelected(TaskControl.listAllTask().get(currentSelectedIndex).getCompletionStatus());
                }
            }
        });
        overdueList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                currentListTab = 1;
                if (overdueList.getSelectedIndex() != -1) {
                    currentSelectedIndex = overdueList.getSelectedIndex();
                    name.setText(TaskControl.listOverdueTasks().get(currentSelectedIndex).getName());
                    note.setText(TaskControl.listOverdueTasks().get(currentSelectedIndex).getNotes());
                    dueDate.setText(TaskControl.listOverdueTasks().get(currentSelectedIndex).getDueDate().getTime().toLocaleString());
                    completionStatus.setSelected(TaskControl.listOverdueTasks().get(currentSelectedIndex).getCompletionStatus());
                }
            }
        });
        upcomingList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                currentListTab = 2;
                if (upcomingList.getSelectedIndex() != -1) {
                    currentSelectedIndex = upcomingList.getSelectedIndex();
                    name.setText(TaskControl.listIncompleteUpcomingTasks().get(currentSelectedIndex).getName());
                    note.setText(TaskControl.listIncompleteUpcomingTasks().get(currentSelectedIndex).getNotes());
                    dueDate.setText(TaskControl.listIncompleteUpcomingTasks().get(currentSelectedIndex).getDueDate().getTime().toLocaleString());
                    completionStatus.setSelected(TaskControl.listIncompleteUpcomingTasks().get(currentSelectedIndex).getCompletionStatus());
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TaskControl.exitProgram();
                System.exit(0);
            }
        });
        completionStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentListTab != -1 && currentSelectedIndex != -1) {
                    if (currentListTab == 0) {
                        TaskControl.markComplete(TaskControl.listAllTask().get(currentSelectedIndex));
                        refreshLists();
                    }
                    else if (currentListTab == 1) {
                        TaskControl.markComplete(TaskControl.listOverdueTasks().get(currentSelectedIndex));
                        refreshLists();
                    }
                    else if (currentListTab == 2) {
                        TaskControl.markComplete(TaskControl.listIncompleteUpcomingTasks().get(currentSelectedIndex));
                        refreshLists();
                    }
                }
            }
        });
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                refreshLists();
                name.setText("");
                note.setText("");
                dueDate.setText("");
                completionStatus.setSelected(false);


            }
        });
        removeTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentListTab != -1 && currentSelectedIndex != -1) {
                    if (currentListTab == 0) {
                        TaskControl.removeTask(TaskControl.listAllTask().get(currentSelectedIndex));
                        refreshLists();
                    }
                    else if (currentListTab == 1) {
                        TaskControl.removeTask(TaskControl.listOverdueTasks().get(currentSelectedIndex));
                        refreshLists();
                    }
                    else if (currentListTab == 2) {
                        TaskControl.removeTask(TaskControl.listIncompleteUpcomingTasks().get(currentSelectedIndex));
                        refreshLists();
                    }
                }
            }
        });
    }

    private void refreshLists(){

        allListModel.removeAllElements();
        overdueListModel.removeAllElements();
        upcomingListModel.removeAllElements();

        currentSelectedIndex = -1;
        currentListTab = -1;
        for (Task task : TaskControl.listAllTask()) {
            allListModel.addElement(task.getName());
        }
        currentSelectedIndex = -1;
        currentListTab = -1;

        for (Task task : TaskControl.listOverdueTasks()) {
            overdueListModel.addElement(task.getName());
        }
        currentSelectedIndex = -1;
        currentListTab = -1;

        for (Task task : TaskControl.listIncompleteUpcomingTasks()) {
            upcomingListModel.addElement(task.getName());
        }
    }

}
