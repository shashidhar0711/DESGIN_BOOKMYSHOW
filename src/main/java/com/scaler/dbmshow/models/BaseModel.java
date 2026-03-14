package com.scaler.dbmshow.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue()
    private int id;
    private Date createdAt;
    private Date updatedAt;
}
