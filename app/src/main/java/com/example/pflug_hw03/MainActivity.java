/*
 * Assignment #: HW03
 * File Name: Pflug_HW03 --- MainActivity.java
 * Full Name: Kristin Pflug
 */

package com.example.pflug_hw03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ToDoListFragment.ToDoFragmentListener, CreateTaskFragment.CreateTaskFragmentListener, DisplayTaskFragment.DisplayTaskFragmentListener {

    ArrayList<Task> taskArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskArrayList = new ArrayList<>();

        getSupportFragmentManager().beginTransaction().add(R.id.containerView, ToDoListFragment.newInstance(taskArrayList)).commit();
    }

    @Override
    public void goToToDoFragmentSubmit(Task task) {
        taskArrayList.add(task);
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, ToDoListFragment.newInstance(taskArrayList)).commit();
    }

    @Override
    public void goToToDoFragmentCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToToDoFragmentDelete(Task task) {
        for (int i = 0; i < taskArrayList.size(); i++) {
            if (taskArrayList.get(i).name.equals(task.name)) {
                taskArrayList.remove(i);
            }
        }

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, ToDoListFragment.newInstance(taskArrayList)).commit();
    }

    @Override
    public void goToDisplayFragment(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, DisplayTaskFragment.newInstance(task))
                .addToBackStack(null).commit();
    }

    @Override
    public void goToCreateFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreateTaskFragment())
                .addToBackStack(null).commit();
    }
}