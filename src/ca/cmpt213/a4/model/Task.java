package ca.cmpt213.a4.model;


import java.util.GregorianCalendar;

public class Task {
    private String name;
    private String notes;
    private GregorianCalendar dueDate;
    private boolean status;

    public Task(String name, String notes, GregorianCalendar dueDate) {
        this.name = name;
        this.notes = notes;
        this.dueDate = dueDate;
        this.status = false;
    }


    public String toString(){
        String result = ("Task: " + this.name +
                "\nNotes: " + this.notes +
                "\nDue Date: " + this.dueDate.getTime() +
                "\nCompletion Status: " + String.valueOf(this.status) +
                "\n");
        return result;
    }

    public String getName(){
        return this.name;
    }

    public String getNotes(){
        return this.notes;
    }

    public boolean getCompletionStatus(){
        return this.status;
    }

    public void markTask(){
        this.status = !this.status;
    }

    public GregorianCalendar getDueDate(){
        return this.dueDate;
    }


}
