package com.yuye.example.common.entity;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author: xgf
 * @date: 2023-08-14 10:23
 */
@Data
public class YuyeParams<T> implements Serializable {

    private String name;

    private Integer age;

    private List<YuyeParams> params;

    private T data;
}
