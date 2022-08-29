/*
 * Assignment #: HW03
 * File Name: Pflug_HW03 --- ToDoListFragment.java
 * Full Name: Kristin Pflug
 */

package com.example.pflug_hw03;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TASK_LIST = "ARG_TASK_LIST";

    // TODO: Rename and change types of parameters
    private ArrayList<Task> taskArrayList;

    public ToDoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskArrayList Parameter 1.
     * @return A new instance of fragment ToDoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDoListFragment newInstance(ArrayList<Task> taskArrayList) {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_LIST, taskArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.taskArrayList = (ArrayList<Task>) getArguments().getSerializable(ARG_TASK_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list, container, false);
    }

    ToDoFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListener = (ToDoFragmentListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.main_activity_title);

        TextView taskCountText = view.findViewById(R.id.task_count_label);
        TextView taskName = view.findViewById(R.id.task_name_label);
        TextView taskDate = view.findViewById(R.id.task_date_label);
        TextView taskPriority = view.findViewById(R.id.task_priority_label);
        Button displayTaskButton = view.findViewById(R.id.view_task_button);
        Button createTaskButton = view.findViewById(R.id.create_task_button);

        taskCountText.setText("You have " + taskArrayList.size() + " tasks");

        if(taskArrayList.size() == 0){
            taskName.setText(getString(R.string.no_tasks_label));
            taskDate.setText("");
            taskPriority.setText("");
        } else {
            Collections.sort(taskArrayList, new Comparator<Task>() {
                @Override
                public int compare(Task t1, Task t2) {
                    DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        return f.parse(t1.date).compareTo(f.parse(t2.date));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });

            Task firstTask = taskArrayList.get(0);
            taskName.setText(firstTask.name);
            taskDate.setText(firstTask.date);
            taskPriority.setText(firstTask.priority);
        }

        displayTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskArrayList.size() == 0) {
                    Toast.makeText(view.getContext(), getString(R.string.no_tasks_toast), Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> taskNameList = new ArrayList<>();
                    for (Task task : taskArrayList) {
                        taskNameList.add(task.name);
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(R.string.alertdialog_title)
                            .setItems(taskNameList.toArray(new String[taskNameList.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mListener.goToDisplayFragment(taskArrayList.get(i));
                                }
                            })
                            .setNegativeButton(R.string.alertdialog_cancel_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToCreateFragment();
            }
        });
    }

    interface ToDoFragmentListener {
        void goToDisplayFragment(Task task);
        void goToCreateFragment();
    }
}