package com.example.stoychopetrov.fitness.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Stoycho Petrov on 28.3.2018 г..
 */

@Entity(tableName = "day")
public class Day {

    @PrimaryKey(autoGenerate = true)
    private int dayId;

    @ColumnInfo(name = "day_title")
    private String dayTitle;

    public Day(String dayTitle) {
        this.dayTitle = dayTitle;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }
}
