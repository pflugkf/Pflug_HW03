/*
 * Assignment #: HW03
 * File Name: Pflug_HW03 --- CreateTaskFragment.java
 * Full Name: Kristin Pflug
 */

package com.example.pflug_hw03;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTaskFragment newInstance(String param1, String param2) {
        CreateTaskFragment fragment = new CreateTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }

    CreateTaskFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListener = (CreateTaskFragmentListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.create_task_activity_title);

        EditText nameEntered = view.findViewById(R.id.create_task_name_textbox);
        TextView displayDate = view.findViewById(R.id.create_task_date_text);;
        RadioGroup priorityList = view.findViewById(R.id.create_task_priority_radiogroup);;

        view.findViewById(R.id.create_task_set_date_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                displayDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);

                dpd.show();

            }
        });

        view.findViewById(R.id.create_task_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToToDoFragmentCancel();
            }
        });

        view.findViewById(R.id.create_task_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = "";
                String taskDate = "";
                String taskPriority = "";

                if(nameEntered.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), getString(R.string.no_name_entered_toast), Toast.LENGTH_SHORT).show();
                } else {
                    if(displayDate.getText().toString().equals("")) {
                        Toast.makeText(view.getContext(), getString(R.string.no_date_picked_toast), Toast.LENGTH_SHORT).show();

                    } else {
                        int chosenPriority = priorityList.getCheckedRadioButtonId();

                        if(chosenPriority != -1) {
                            if (chosenPriority == R.id.create_task_priority_high_radiobutton) {
                                taskPriority = "High";
                            } else if (chosenPriority == R.id.create_task_priority_medium_radiobutton) {
                                taskPriority = "Medium";
                            } else if (chosenPriority == R.id.create_task_priority_low_radiobutton) {
                                taskPriority = "Low";
                            }

                            taskName = nameEntered.getText().toString();
                            taskDate = displayDate.getText().toString();
                            Task task = new Task(taskName, taskDate, taskPriority);

                            mListener.goToToDoFragmentSubmit(task);
                        } else {
                            Toast.makeText(view.getContext(), getString(R.string.no_priority_chosen_toast), Toast.LENGTH_SHORT).show();
                        }


                    }


                }

            }
        });
    }

    interface CreateTaskFragmentListener {
        void goToToDoFragmentSubmit(Task task);
        void goToToDoFragmentCancel();
    }
}