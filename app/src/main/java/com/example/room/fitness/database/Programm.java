package com.example.room.fitness.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Entity(tableName = "programm")
public class Programm {

    @PrimaryKey(autoGenerate = true)
    private int programmId;

    @ColumnInfo(name = "programm_title")
    private String programmTitle;

    public Programm(String programmTitle) {
        this.programmTitle = programmTitle;
    }

    public int getProgrammId() {
        return programmId;
    }

    public void setProgrammId(int programmId) {
        this.programmId = programmId;
    }

    public String getProgrammTitle() {
        return programmTitle;
    }

    public void setProgrammTitle(String programmTitle) {
        this.programmTitle = programmTitle;
    }
}
