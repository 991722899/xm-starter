package com.xm.starter.base.model;

import lombok.Data;
 /** 用户对象信息
* @description：
* @author：陈超超
* @time：2024/10/25 16:18
*/
@Data
public class UserInfo<T> {
    private String userId;
    private String userName;
    private String userRealName;
    private T ext;
}
