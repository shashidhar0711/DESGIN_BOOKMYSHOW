package com.scaler.dbmshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class City extends BaseModel{
    private String name;
    @OneToMany(mappedBy = "city")
    // Do not crate mapping table,
    // rather create city col in the theater table
    private List<Theatre> theaters;
}
