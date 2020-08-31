package com.ls.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PageBean {

    private int currentPage;//当前页

    private int startIndex;//开始索引

    private int pageSize = 5;//每页条数

    private int totalPage;//总页数

    private int totalCount;//总记录数

}
