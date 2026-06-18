package com.example.petshop.controller; // 声明全局异常处理器所在包。 
import org.springframework.http.HttpStatus; // 引入 HTTP 状态码。 
import org.springframework.web.bind.MethodArgumentNotValidException; // 引入参数校验异常类型。 
import org.springframework.web.bind.annotation.ExceptionHandler; // 引入异常处理注解。 
import org.springframework.web.bind.annotation.ResponseStatus; // 引入响应状态注解。 
import org.springframework.web.bind.annotation.RestControllerAdvice; // 引入 REST 全局增强注解。 
import java.util.LinkedHashMap; // 引入有序 Map。 
import java.util.Map; // 引入 Map 接口。 
@RestControllerAdvice // 声明该类统一处理 REST 控制器异常。 
public class GlobalExceptionHandler { // 定义全局异常处理器。 
    @ExceptionHandler(MethodArgumentNotValidException.class) // 处理参数校验异常。 
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 返回 400 状态码。 
    public Map<String, Object> validation(MethodArgumentNotValidException exception) { // 定义校验异常响应方法。 
        String message = exception.getBindingResult().getFieldErrors().isEmpty() ? "参数校验失败" : exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage(); // 取出第一条校验错误消息。 
        return fail(message); // 返回统一失败响应。 
    } // 结束校验异常处理方法。 
    @ExceptionHandler(Exception.class) // 处理其他所有异常。 
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 返回 400 状态码便于前端展示。 
    public Map<String, Object> error(Exception exception) { // 定义普通异常响应方法。 
        return fail(exception.getMessage()); // 返回异常消息。 
    } // 结束普通异常处理方法。 
    private Map<String, Object> fail(String message) { // 定义统一失败响应工具方法。 
        Map<String, Object> body = new LinkedHashMap<>(); // 创建有序响应 Map。 
        body.put("success", false); // 写入失败标记。 
        body.put("message", message); // 写入失败消息。 
        return body; // 返回失败响应。 
    } // 结束失败响应工具方法。 
} // 结束全局异常处理器。 
