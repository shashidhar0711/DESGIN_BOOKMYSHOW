package com.scaler.dbmshow.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Movie extends BaseModel implements Serializable {
    private String name;
    private Genre genre;
    private List<String> directors;
    private List<String>  actors;
}
