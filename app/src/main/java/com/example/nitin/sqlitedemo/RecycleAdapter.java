package com.example.nitin.sqlitedemo;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyHolder> {
    List<DataModel> dataModelArrayList;
    Context context;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    private String updatename, updatesalary, updatedepartment;
    private AlertDialog dialog;


    public RecycleAdapter(Context context, List<DataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdata_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyHolder holder, int position) {

        final DataModel dataModel = dataModelArrayList.get(position);
        holder.name.setText(dataModel.getName());
        holder.salary.setText(dataModel.getSalary());
        holder.department.setText(dataModel.getDepartment());
        holder.edtemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateemployee(dataModel);
            }
        });
        holder.deleteemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openHelper = new DBHandler(context);
                        db = openHelper.getWritableDatabase();
                        String whereClause = "id=?";
                        String whereArgs[] = {dataModel.getID()};
                        db.delete(DBHandler.TABLE_NAME, whereClause, whereArgs);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void updateemployee(final DataModel dataModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit_item, null);
        builder.setView(view);
        final EditText edtname, edtsalary;
        final Spinner spinnerdepartment;
        final Button btnupdate;
        edtname = (EditText) view.findViewById(R.id.edit_TextName);
        edtsalary = (EditText) view.findViewById(R.id.edit_TextSalary);
        spinnerdepartment = (Spinner) view.findViewById(R.id.spinner_Department);
        btnupdate = (Button) view.findViewById(R.id.button_UpdateEmployee);
        edtname.setText(dataModel.getName());
        edtsalary.setText(dataModel.getSalary());
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHelper = new DBHandler(context);
                db = openHelper.getWritableDatabase();
                updatename = edtname.getText().toString();
                updatesalary = edtsalary.getText().toString();
                updatedepartment = spinnerdepartment.getSelectedItem().toString();
                ContentValues values = new ContentValues();
                values.put(DBHandler.COL_2, updatename);
                values.put(DBHandler.COL_3, updatedepartment);
                values.put(DBHandler.COL_4, updatesalary);
                db.update(DBHandler.TABLE_NAME, values, DBHandler.COL_1 + "=" + dataModel.getID(), null);
                Toast.makeText(context, "Update Employee", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name, salary, department;
        Button edtemployee, deleteemployee;

        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            salary = (TextView) itemView.findViewById(R.id.tv_salary);
            department = (TextView) itemView.findViewById(R.id.tv_department);
            edtemployee = (Button) itemView.findViewById(R.id.btn_editemployee);
            deleteemployee = (Button) itemView.findViewById(R.id.btn_deleteemployee);
        }
    }
}
