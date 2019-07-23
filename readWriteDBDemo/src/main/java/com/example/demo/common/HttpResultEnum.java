package com.example.demo.common;

public enum HttpResultEnum {
    EXIT(-100, "退出"),
    SUCCESS(100, "成功"),
    FAIL(102, "失败"),
    EXEPTION(103, "网络异常"),
    ILLEGAL(104, "非法请求"),
    HTTPREQ_NO_ACCOUNTID(10104, "缺少账号"),
    ACCOUNT_NOT_REGISTERED(11100, "未找到注册信息，请先注册"),
    ACCOUNT_CHECKPASSWORD_SUCCESS(11101, "验证通过"),
    ACCOUNT_CHECKPASSWORD_FAIL(11102, "登录验证出错，请联系管理员"),
    ACCOUNT_NO_ACCOUNT(11103, "缺少用户名或者密码信息"),
    ACCOUNT_NO_PARAMS(11104, "参数不全"),
    ACCOUNT_UNLOGIN(11110, "未登录"),
    ACCOUNT_RELOGIN(11105, "未找到登录信息，请重新登录"),
    ACCOUNT_NOT_BIND(11106, "帐户未绑定"),
    ACCOUNT_ALREADY_BIND(11107, "帐户己绑定"),
    ACCOUNT_STUDENTCODE_INVALID(11108, "学号无效"),
    ACCOUNT_MULTIPLE_STUDENT_FOUND(11109, "找到多条学生数据"),
    ACCOUNT_CHECKCODE_ERROR(11110, "验证码不正确"),
    ACCOUNT_CHECKCODE_NOTNULL(11111, "验证码不能为空"),
    ACCOUNT_CHECKCODE_EXPIRED(11112, "请更换验证码"),
    ACCOUNT_PASSWORD_ERROR(11113, "用户名或者密码错误"),
    ACCOUNT_NO_AUTHORITY(11114, "无访问权限"),
    ACCOUNT_TIMEOUT(11115, "账号过期，请重新登录"),
    ACCOUNT_CHECKCODE_NEEDED(11116, "访问失败次数过频，请输入验证码"),
    COURSE_STUDENT_LIVE_NOT_STARTED(12001, "课程未开始"),
    FILE_SIZE_LIMIT(55554, "文件大小超限"),
    CLIENT_VERSION_ERROR(10000, "版本号格式不对"),
    CLIENT_NAME_ERROR(10001, "文件名称不正确"),
    CLIENT_TYPE_ERROR(200002, "文件类型错误"),
    CLIENT_UNABLE_OBTAIN(200001, "无法获取上传文件"),
    CLIENT_ROLE_TYPE(200003, "权限类型不正确"),
    DUPLICATE_VIDEO_NAME(30001, "视频文件名称重复"),
    WRONG_VIDEO_INFO(30002, "找不到视频文件信息"),
    UPLOAD_VIDEOE_REPEATED(30003, "视频重复上传"),
    WRONG_VIDEO_TYPE(30004, "视频格式错误"),
    UNMATCHED_VIDEO_OFFSET(30005, "视频大小不匹配"),
    NO_URL(404, "url不存在");

    private int status;
    private String message;

    private HttpResultEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
