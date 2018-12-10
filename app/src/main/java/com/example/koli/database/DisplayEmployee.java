package com.example.koli.database;

/**
 * Created by Koli on 11/11/2018.
 */

public class DisplayEmployee {

    private String name;
    private String salary;


    public DisplayEmployee(String name, String salary ) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }


    public String getSalary() {
        return salary;
    }

}
