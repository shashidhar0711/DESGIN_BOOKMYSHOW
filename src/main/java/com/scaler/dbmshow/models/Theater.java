package com.scaler.dbmshow.models;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class Theater extends BaseModel{
    private String name;
    private String address;
    @OneToMany
    private List<Screen> screens;
    @ManyToOne
    private City city;
}
