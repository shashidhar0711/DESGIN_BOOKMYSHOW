package com.scaler.dbmshow.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class City extends BaseModel{
    private String name;
    @OneToMany(mappedBy = "city")
    // Do not crate mapping table,
    // rather create city col in the theater table
    private List<Theatre> theaters;
}
