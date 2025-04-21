package com.miles.member.model;


import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2020/4/9 20:09
 */
@Data
public class SysUser implements Serializable {

    private Integer id;
    private String username;
    private String password;
}