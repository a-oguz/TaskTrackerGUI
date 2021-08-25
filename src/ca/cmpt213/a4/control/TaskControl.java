package ca.cmpt213.a4.control;

import ca.cmpt213.a4.model.Task;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;


public class TaskControl {

    private static ArrayList<Task> taskList;
    private static boolean running = true;

    public static boolean isRunning() {
        return running;
    }


    public static void StartProgram() {
        File dataFile = new File("data.json");
        Gson gson = new Gson();
        if(!dataFile.exists()){
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader("data.json"));
            taskList = gson.fromJson(jsonElement, new TypeToken<ArrayList<Task>>(){}.getType());
            if(taskList == null){
                taskList = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<Task> listAllTask(){
        ArrayList<Task> result = new ArrayList<>();
        if(taskList != null){
            result.addAll(taskList);
        }

        return result;
    }


    public static ArrayList<Task> listIncompleteTasks() {
        ArrayList<Task> result = new ArrayList<>();
        for (Task task : taskList) {
            if (!task.getCompletionStatus()) {
                result.add(task);
            }
        }
        return result;
    }



    public static ArrayList<Task> listOverdueTasks(){
        ArrayList<Task> result = new ArrayList<>();
        if( taskList != null) {
            for (Task task : taskList) {
                if (!task.getCompletionStatus()) {
                    GregorianCalendar currentTime = new GregorianCalendar();
                    int comparison = task.getDueDate().compareTo(currentTime);
                    if (comparison < 0) {
                        result.add(task);
                    }
                }
            }
        }
        return result;
    }

    public static ArrayList<Task> listIncompleteUpcomingTasks(){
        ArrayList<Task> result = new ArrayList<>();
        if( taskList != null) {
            for (Task task : taskList) {
                if (!task.getCompletionStatus()) {
                    GregorianCalendar currentTime = new GregorianCalendar();
                    int comparison = task.getDueDate().compareTo(currentTime);
                    if (comparison > 0) {
                        result.add(task);
                    }
                }
            }
        }
        return result;
    }



    public static void addTask(String inputName, String inputNote, LocalDateTime inputDate){
        GregorianCalendar date = GregorianCalendar.from(inputDate.atZone(ZoneId.systemDefault()));
        Task input = new Task(inputName,inputNote,date);
        boolean isIn = false;
        for(Task task : taskList){
            if (task.getName().compareTo(inputName) == 0) {
                isIn = true;
                break;
            }
        }
        if(!isIn) {
            taskList.add(input);
        }
        sortTaskList();
    }


    public static void removeTask(Task toRemove){

        taskList.removeIf(task -> task.toString().compareTo(toRemove.toString()) == 0);

    }

    public static void markComplete(Task toMarkComplete){

        for (Task task : taskList) {
            if (task.toString().compareTo( toMarkComplete.toString()) == 0) {
                task.markTask();
            }
        }
    }

    private static void sortTaskList(){
        ArrayList<Task> toSort = listAllTask();
        toSort.sort(Comparator.comparing(o -> o.getDueDate().getTime()));
        taskList = toSort;
    }


    public static void exitProgram(){
        File dataFile = new File("data.json");
        Gson gson = new Gson();
        try {
            Writer writer =new FileWriter("data.json");
            gson.toJson( taskList, writer );
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
    }


}
