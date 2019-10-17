package com.example.demo.config;

import com.example.demo.common.HttpResultEnum;
import com.example.demo.common.ServiceResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangli
 * @version V1.0
 * @ClassName: FinalExceptionHandler
 * @Description: 在进入Controller之前，譬如请求一个不存在的地址，404错误。
 */
@RestController
public class FinalExceptionHandler implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    @ResponseBody
    public ServiceResult error(HttpServletResponse resp, HttpServletRequest req) {
        // 错误处理逻辑
        return new ServiceResult(HttpResultEnum.NO_URL);
    }

}