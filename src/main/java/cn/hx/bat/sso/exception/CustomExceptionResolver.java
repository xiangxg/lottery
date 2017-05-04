package cn.hx.bat.sso.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.hx.common.bean.JsonResult;
import cn.hx.common.service.RedisService;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * <p>
 * Title: CustomExceptionResolver
 * </p>
 * <p>
 * Description: 统一异常处理，自定义异常处理器
 * </p>
 * 
 * @version 1.0
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Autowired
    private RedisService redisService;

    // 前端控制器DispatcherServlet在进行HandlerMapping、调用HandlerAdapter执行Handler过程中，如果遇到异常就会执行此方法
    // handler最终要执行的Handler，它的真实身份是HandlerMethod
    // Exception ex就是接收到异常信息
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {
       

        // 统一异常处理代码
        // 针对系统自定义的CustomException异常，就可以直接从异常类中获取异常信息，将异常处理在错误页面展示
        // 异常信息
      
        CustomException customException = null;
        // 如果ex是系统 自定义的异常，直接取出异常信息
        if (ex instanceof CustomException) {
            customException = (CustomException) ex;
        } else {
            // 针对非CustomException异常，对这类重新构造成一个CustomException，异常信息为“未知错误”
            customException = new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(),"系统错误");
            // 输出异常
            ex.printStackTrace();
        }

        // 封装错误 信息
        JsonResult result = new JsonResult();
        result.setMessage(customException.getMessage());
        result.setCode(customException.getCode());
        result.setData(customException.getData());
        // 返回json
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(MAPPER.writeValueAsString(result));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

        return new ModelAndView();
    }

}
