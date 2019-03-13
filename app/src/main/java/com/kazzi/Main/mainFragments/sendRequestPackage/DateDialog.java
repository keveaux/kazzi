package com.kazzi.Main.mainFragments.sendRequestPackage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtDate;
    int year,month,day;
    public void DateDialog2(View view){
        txtDate=(EditText)view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c=Calendar.getInstance();

        if (getArguments() != null) {
            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            day = getArguments().getInt("day");
            c.set(year, month, day);

        }else {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }



        DatePickerDialog picker = new DatePickerDialog(getActivity(),
                this, year, month, day);
        picker.getDatePicker().setMinDate(c.getTime().getTime());

        return picker;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String date=dayOfMonth+"-"+(month+1)+"-"+year;
        txtDate.setText(date);

    }
}
