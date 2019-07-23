package com.example.demo.exception;

import com.example.demo.common.HttpResultEnum;
import com.example.demo.common.ServiceResult;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: GlobalDefaultExceptionHandler
 * @Description: 在controller里执行逻辑代码时出的异常
 * @version V1.0
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    private Logger logger = Logger.getLogger(GlobalDefaultExceptionHandler.class);
    /**
     * @Title: defaultErrorHandler
     * @Description: 统一处理某一类异常，从而能够减少代码重复率和复杂度
     * @param req
     * @param ex
     * @return ApiResult
     * @throws
     */
//	@ExceptionHandler(value = ExceptionResult.class)//指定具体要处理的异常
    @ExceptionHandler//处理所有异常
    @ResponseBody
    public ServiceResult defaultErrorHandler(HttpServletRequest req, RuntimeException  ex) {
        // 打印异常信息：
        ex.printStackTrace();
        logger.error("捕获全局异常=="+ex);
        return new ServiceResult(HttpResultEnum.ILLEGAL.getStatus(), ex.toString());

    }

    /**
     * 捕获类内所有的异常
     *
     * @param ex
     * @return
     */
//	@ExceptionHandler(value = Exception.class)
//	public ModelAndView exceptionHandelByThymeleaf(Exception ex, HttpServletRequest req) {
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("error");
//		mav.addObject("exception", ex.getMessage());
//		mav.addObject("url", req.getRequestURL());
//		return mav;
//	}

}