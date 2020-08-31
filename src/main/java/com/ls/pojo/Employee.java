package com.ls.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private int eid;
    private String name;
    private String email;
    private Integer gender; //0 女  1男
    private int did;
    private Date birth;

}
