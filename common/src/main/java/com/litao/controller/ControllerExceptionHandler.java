package com.litao.controller;

import com.litao.exception.BusinessException;
import com.litao.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG= LoggerFactory.getLogger(ControllerExceptionHandler.class);


    /**
     * 所有异常统一处理
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(Exception e) throws Exception{
        CommonResp<?> commonResp =new CommonResp<>();
        LOG.error("系统异常：",e);
        commonResp.setSuccess(false);
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getMessage());
        return commonResp;
    }


    /**
     * 业务异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(BusinessException e){
        CommonResp<?> commonResp=new CommonResp<>();
        LOG.error("业务异常：{}",e.getE().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getE().getDesc());
        return  commonResp;
    }
}