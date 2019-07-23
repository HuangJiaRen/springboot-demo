package com.example.demo.exception;

import com.example.demo.common.HttpResultEnum;
import com.example.demo.common.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 在执行@RequestMapping时，进入逻辑处理阶段前。譬如传的参数类型错误
 * @author huangli
 * @version V1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(new ServiceResult(HttpResultEnum.ILLEGAL.getStatus(),HttpResultEnum.ILLEGAL.getMessage()), status);

    }

}