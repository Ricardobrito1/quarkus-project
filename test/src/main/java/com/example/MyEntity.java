package com.example;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;



@Entity
public class MyEntity extends PanacheEntity {
    public String field;
}
