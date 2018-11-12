package com.example.koli.database;

/**
 * Created by Koli on 11/10/2018.
 */

import android.provider.BaseColumns;

/**
 * Created by Koli on 11/10/2018.
 */

public class Contract implements BaseColumns {

    public static final String TABLE_NAME = "Employee";

    public static final String _ID = BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SALARY = "salary";

    private int id;
    private String name;
    private String salary;

    public Contract() {
    }

    public Contract(int id, String name, String salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }


}