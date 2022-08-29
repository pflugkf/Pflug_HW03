/*
 * Assignment #: HW03
 * File Name: Pflug_HW03 --- Task.java
 * Full Name: Kristin Pflug
 */

package com.example.pflug_hw03;

import java.io.Serializable;

public class Task implements Serializable {

    String name;
    String date;
    String priority;

    public Task(String name, String date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }
}
