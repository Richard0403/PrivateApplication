package com.richard.stepcount.entity.home;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by richard on 2017/4/5.
 */
@Table("DaySteps")
public class DayStepEntity {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long id;
    @Column("stepCount")
    private int stepCount;
    @Column("date")
    private long date;
    @Column("isUpload")
    private boolean isUpload;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }
}
