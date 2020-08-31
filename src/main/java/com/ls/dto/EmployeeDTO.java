package com.ls.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private int id;
    private String name;
    private String email;
    private Integer gender; //0 女  1男
    private String departmentName;
    private Date birth;

}
