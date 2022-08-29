/*
 * Assignment #: HW03
 * File Name: Pflug_HW03 --- DisplayTaskFragment.java
 * Full Name: Kristin Pflug
 */

package com.example.pflug_hw03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DISPLAY_TASK = "ARG_DISPLAY_TASK";

    // TODO: Rename and change types of parameters
    private Task task;

    public DisplayTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param task Parameter 1.
     * @return A new instance of fragment DisplayTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayTaskFragment newInstance(Task task) {
        DisplayTaskFragment fragment = new DisplayTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DISPLAY_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.task = (Task) getArguments().getSerializable(ARG_DISPLAY_TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_task, container, false);
    }

    DisplayTaskFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListener = (DisplayTaskFragmentListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.display_task_label);

        TextView displayName = view.findViewById(R.id.display_task_name_label);
        TextView displayDate = view.findViewById(R.id.display_task_date_label);
        TextView displayPriority = view.findViewById(R.id.display_task_priority_label);

        displayName.setText(task.name);
        displayDate.setText(task.date);
        displayPriority.setText(task.priority);

        view.findViewById(R.id.display_task_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToToDoFragmentCancel();
            }
        });

        view.findViewById(R.id.display_task_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToToDoFragmentDelete(task);
            }
        });
    }

    interface DisplayTaskFragmentListener {
        void goToToDoFragmentCancel();
        void goToToDoFragmentDelete(Task task);
    }
}