package com.example.orm.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Note {
    @Id(autoincrement = true)
    private Long mId;
    private String mName;
    @Generated(hash = 7455174)
    public Note(Long mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getMId() {
        return this.mId;
    }
    public void setMId(Long mId) {
        this.mId = mId;
    }
    public String getMName() {
        return this.mName;
    }
    public void setMName(String mName) {
        this.mName = mName;
    }


}
