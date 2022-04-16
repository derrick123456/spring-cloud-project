package com.test.demo.user.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.demo.user.model.CommonRsp;
import com.test.demo.user.util.PathUtil;
import com.test.demo.user.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 文件名称: ExceptionConfig.java
 * </p>
 * <p>
 * 类型描述: [统一异常处理,日志记录]
 * </p>
 * <p>
 */
//@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class ExceptionConfig {
    
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> handleAll(Throwable t, HttpServletRequest request) {
        CommonRsp<?> errorInfo= MessageUtil.errorFile();
        String code = errorInfo.getCode();
        return new ResponseEntity<Object>(errorInfo, HttpStatus.valueOf(Integer.valueOf(code.substring(4, 7))));
    }

    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        CommonRsp<?> errorInfo= MessageUtil.errorFileIndexOfSize();
        String code = errorInfo.getCode();
        return new ResponseEntity<Object>(errorInfo, HttpStatus.valueOf(Integer.valueOf(code.substring(4, 7))));
    }
    
    /**
     * <p>
     * 功能描述: [日志处理]
     * </p>
     * 
     * @Title handleBusinessException
     * @param ex      .
     * @param request .
     * @return ResponseEntity
     */
    @ResponseBody
    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        CommonRsp<?> errorInfo = ex.getErrorInfo();
        String code = errorInfo.getCode();
        // 常规表单校验不记录日志,非400错误或者409错误才记录记录日志
        if (!("400".equals(code.substring(4, 7)) && !"409".equals(code.substring(4, 7)))) {
            log.error(errorInfo.toString(), ex);
        } else {
            // 开启debug日志时,才输出参数校验日志
            log.debug("{}", errorInfo);
        }
        return new ResponseEntity<Object>(errorInfo, HttpStatus.valueOf(Integer.valueOf(code.substring(4, 7))));
    }
    
    @ResponseBody
    @ExceptionHandler(value = {SimpleBusinessException.class})
    public ResponseEntity<Object> handleSimpleBusinessException(SimpleBusinessException ex, HttpServletRequest request) {
        CommonRsp<?> errorInfo = ex.getErrorInfo();
        String code = errorInfo.getCode();
        // 常规表单校验不记录日志,非400错误或者409错误才记录记录日志
        log.error(errorInfo.toString(), ex);
        return new ResponseEntity<Object>(errorInfo, HttpStatus.valueOf(Integer.valueOf(code.substring(4, 7))));
    }
    
    /**
     * <p>
     * 功能描述: [400参数解析失败]
     * </p>
     * 
     * @Title handle400Exception
     * @param ex      .
     * @param request .
     * @return ResponseEntity
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<Object> handle400Exception(HttpMessageNotReadableException ex, HttpServletRequest request) {
        CommonRsp<?> errorInfo = MessageUtil.error400();
        log.error(errorInfo.toString(), ex);
        return new ResponseEntity<Object>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    
    private static List<String> extractMessageByRegular(String msg) {
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
		Matcher m = p.matcher(msg);
		while (m.find()) {
			list.add(m.group().substring(1, m.group().length() - 1));
		}
		return list;
	}
    
    @ExceptionHandler(value= {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<Object> handle400ValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
    	BindingResult result = null;
        if (ex instanceof MethodArgumentNotValidException) {
        	result = ((MethodArgumentNotValidException)ex).getBindingResult();
        }
        StringBuilder validMsg = new StringBuilder();
        if (result != null && result.hasErrors()) {
			// result.getAllErrors().forEach(x -> erorMsg.append(" " +
			// x.getDefaultMessage()));
			String rowMsg = "";
			Object[] codes = result.getAllErrors().get(0).getArguments();
			if (codes != null && codes.length > 0) {
				JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(codes[0]));
				String code = jsonObject.getString("code");
				List<String> list = extractMessageByRegular(code);
				rowMsg = CollectionUtils.isEmpty(list)?"":list.get(0);
				rowMsg= StringUtils.isEmpty(rowMsg)?"":"第["+rowMsg+"]行：";
			}
			validMsg.append(rowMsg+result.getAllErrors().get(0).getDefaultMessage());
		}
        CommonRsp<?> errorInfo = MessageUtil.validMsg(validMsg.toString());
//        ThreadLocalHolder.clearLoginVO();
        return new ResponseEntity<Object>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleValidationFailure(ConstraintViolationException ex, HttpServletRequest request) {

      StringBuilder messages = new StringBuilder();
      for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
          messages.append(violation.getMessage() + "\n");
      }
      CommonRsp<?> errorInfo = MessageUtil.validMsgEcception(messages.toString());
      return new ResponseEntity<Object>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseEntity<Object> handle400ParamException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        CommonRsp<?> errorInfo = MessageUtil.error400();
        log.error(errorInfo.toString(), ex);
        return new ResponseEntity<Object>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * <p>
     * 功能描述: [404资源不存在]
     * </p>
     * 
     * @Title handle404Exception
     * @param ex      .
     * @param request .
     * @return ResponseEntity
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseBody
    public ResponseEntity<Object> handle404Exception(NoHandlerFoundException ex, HttpServletRequest request) {
        CommonRsp<?> errorInfo = MessageUtil.error404();
        String requestURI = request.getRequestURI();
        if (!"/".equals(requestURI)) {
            log.debug(errorInfo.toString(), requestURI);
        }
        return new ResponseEntity<Object>(errorInfo, HttpStatus.NOT_FOUND);
    }

    /**
     * <p>
     * 功能描述: [405请求             错误]
     * </p>
     * 
     * @Title handle405Exception
     * @param ex      .
     * @param request .
     * @return ResponseEntity
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Object> handle405Exception(HttpRequestMethodNotSupportedException ex,
                                                     HttpServletRequest request) {
        CommonRsp<?> errorInfo = MessageUtil.error405();
        String requestURI = request.getRequestURI();
        log.debug(errorInfo.toString(), requestURI);
//        ThreadLocalHolder.clearLoginVO();
        return new ResponseEntity<Object>(errorInfo, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * <p>
     * 功能描述: [415请求头错误]
     * </p>
     * 
     * @Title handle415Exception
     * @param ex      .
     * @param request .
     * @return ResponseEntity
     */
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<Object> handle415Exception(HttpMediaTypeNotSupportedException ex,
                                                     HttpServletRequest request) {
        CommonRsp<?> errorInfo = MessageUtil.error415();
        String requestURI = request.getRequestURI();
        log.debug(errorInfo.toString(), requestURI);
        return new ResponseEntity<Object>(errorInfo, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * <p>
     * 功能描述: [500系统错误]
     * </p>
     * 
     * @Title handle500Exception
     * @param ex      .
     * @param request .
     * @return ResponseEntity
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handle500Exception(Exception ex, HttpServletRequest request) {
        String tenant = "can't find hint datanode";
        String error = getTrace(ex);
        
        Map<String, String[]> parameters = request.getParameterMap();
        //RequestParameterWrapper requestWrapper = new RequestParameterWrapper(request);
        	CommonRsp<?> errorInfo  = MessageUtil.error500();
            String apiInfo =MessageFormat.format("api error info,url:{0},method:{1}\r\nParameters:\r\n{2}", PathUtil.clearParams(request.getRequestURL().toString()), request.getMethod(),JSON.toJSONString(parameters));
//                String.format("api error info,url:%s,method:%s\r\nParameters:\r\n%s,\r\nRequest Body:\r\n%s", PathUtil.clearParams(request.getRequestURL().toString()),
//                		request.getMethod(), JSON.toJSONString(parameters), requestWrapper.getBody());
            log.error(errorInfo.toString(), ex);
            log.debug(apiInfo);
            return new ResponseEntity<Object>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }



    /**
     * <p>
     * 功能描述: [将错误信息转成字符串]
     * </p>
     * 
     * @Title getTrace
     * @param t .
     * @return String .
     */
    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        return stringWriter.getBuffer().toString();
    }

}
