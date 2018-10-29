package com.example.nitin.sqlitedemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtemployename, edtsalary;
    private Spinner spindepartment;
    private Button btnaddnewemployee;
    private TextView tvviewemployee;
    private String name, salary, department, joiningDate;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHelper = new DBHandler(MainActivity.this);
        edtemployename = (EditText) findViewById(R.id.edt_employeename);
        spindepartment = (Spinner) findViewById(R.id.spin_department);
        edtsalary = (EditText) findViewById(R.id.edt_salary);
        btnaddnewemployee = (Button) findViewById(R.id.btn_addnewemployee);
        tvviewemployee = (TextView) findViewById(R.id.tv_viewemployee);
        findViewById(R.id.btn_addnewemployee).setOnClickListener(this);
        tvviewemployee.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addnewemployee:
                addEmployee();
                break;
            case R.id.tv_viewemployee:
                startActivity(new Intent(this, EmployeeActivity.class));
                break;
        }
    }

    private void addEmployee() {

        db = openHelper.getWritableDatabase();
        name = edtemployename.getText().toString();
        salary = edtsalary.getText().toString();
        department = spindepartment.getSelectedItem().toString();
        insertdata(name, salary, department);
        Toast.makeText(this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();
    }

    private void insertdata(String name, String salary, String department) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHandler.COL_2, name);
        contentValues.put(DBHandler.COL_3, department);
        contentValues.put(DBHandler.COL_4, salary);
        long id = db.insert(DBHandler.TABLE_NAME, null, contentValues);
    }
}
