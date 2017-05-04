package com.slim0926.todolist.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.slim0926.todolist.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sue on 12/29/16.
 */

public class DetailsFragment extends Fragment {

    private static final String ITEM_ID = "item_id";
    public static final String DATE_TO_BE_COMPARED = "1/1/3016";

    @BindView(R.id.checkBox) CheckBox mDetailsCheckBox;
    @BindView(R.id.titleEditText) EditText mDetailsTitleEdit;
    @BindView(R.id.notesMultiText) EditText mNotesEdit;
    @BindView(R.id.duedateEdit) EditText mDueDateEdit;
    @BindView(R.id.addressEditText) EditText mAddressEdit;
    @BindView(R.id.prioritySpinner) Spinner mPrioritySpinner;
    @BindView(R.id.saveButton) Button mSaveButton;

    public interface OnToDoListItemSaveInterface {
        void onToDoListItemSaveButtonClicked();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);

        String[] priorityArray = new String[] {"None", "High", "Medium", "Low"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                priorityArray);
        mPrioritySpinner.setAdapter(adapter);

        final OnToDoListItemSaveInterface mainActivity = (OnToDoListItemSaveInterface) getActivity();
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onToDoListItemSaveButtonClicked();
            }
        });

        if (getArguments() != null) {
            int id = getArguments().getInt(ITEM_ID);
            int pos = 0;
            for (int i = 0; i < ((MainActivity) getActivity()).mItems.getSize(); i++) {
                if (((MainActivity) getActivity()).mItems.getItem(i).getID() == id) {
                    pos = i;
                }
            }
            populateDetailsPage(pos);
        }
        return view;
    }

    private void populateDetailsPage(int pos) {
        mDetailsTitleEdit.setText(((MainActivity) getActivity()).mItems.getItem(pos).getTitle());
        mNotesEdit.setText(((MainActivity) getActivity()).mItems.getItem(pos).getNotes());
        populateDueDateEditText(pos);
        mAddressEdit.setText(((MainActivity) getActivity()).mItems.getItem(pos).getLocation());
        String priority = ((MainActivity) getActivity()).mItems.getItem(pos).getPriority() + "";
        int priorityPos = 0;
        switch (priority) {
            case "None":
                priorityPos = 0;
                break;
            case "High":
                priorityPos = 1;
                break;
            case "Medium":
                priorityPos = 2;
                break;
            case "Low":
                priorityPos = 3;
                break;
        }
        mPrioritySpinner.setSelection(priorityPos);
        mDetailsCheckBox.setChecked(((MainActivity) getActivity()).mItems.getItem(pos).isChecked());

        mDetailsTitleEdit.setScaleX(.78f);
        mDetailsTitleEdit.setScaleY(.78f);
        mDetailsTitleEdit.animate().scaleX(1.0f).scaleY(1.0f).start();
    }

    private void populateDueDateEditText(int pos) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
            Date date = formatter.parse(DATE_TO_BE_COMPARED);
            if (date.compareTo(((MainActivity)getActivity()).mItems.getItem(pos).getDueDateInDateFormat())<0) {
                mDueDateEdit.setText("");
            } else {
                mDueDateEdit.setText(((MainActivity) getActivity()).mItems.getItem(pos).getDueDate());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
