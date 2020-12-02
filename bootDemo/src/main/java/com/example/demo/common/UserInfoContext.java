package com.example.demo.common;

import java.util.List;

public class UserInfoContext {

    private static ThreadLocal<UserInfo> LOCAL = new ThreadLocal<>();

    public static UserInfo get() {
        return LOCAL.get();
    }

    public static void set(UserInfo userInfo) {
        LOCAL.set(userInfo);
    }

    public static void remove() {
        LOCAL.remove();
    }

    /**
     * 获取用户id
     */
    public static Long getUaId() {
        return get().getUaId();
    }

    /**
     * 获取学校列表
     */
    public static List<String> getSchoolIdLt() {
        return get().getSchoolIdLt();
    }

    /**
     * 是否是超级管理员
     */
    public static boolean isSuperAdmin() {
        Integer uaRoleId = get().getUaRoleId();
        return uaRoleId != null && uaRoleId == 1;
    }
}
