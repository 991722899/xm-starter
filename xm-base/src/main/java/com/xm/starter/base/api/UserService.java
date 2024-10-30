package com.xm.starter.base.api;

import com.xm.starter.base.model.UserInfo;
/**
* @description：全局用户接口，由集成者实现
* @author：陈超超
* @time：2024/10/25 16:18
*/
public interface UserService<T> {
    String userId();
    String userName();
    String userRealName();
    UserInfo<T> user();
}
