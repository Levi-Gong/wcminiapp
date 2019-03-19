package org.ln.wechat.miniapp.system.security;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ln.wechat.miniapp.bean.common.ApiConstant;
import org.ln.wechat.miniapp.bean.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiAccessDeniedHandler implements AccessDeniedHandler {

  protected static final Log logger = LogFactory.getLog(ApiAccessDeniedHandler.class);
  private String errorPage;

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {

    if (!response.isCommitted()) {
      if (this.errorPage != null) {
        request.setAttribute("SPRING_SECURITY_403_EXCEPTION", accessDeniedException);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        RequestDispatcher dispatcher = request.getRequestDispatcher(this.errorPage);
        dispatcher.forward(request, response);
      } else {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(ApiConstant.FALSE);
        apiResponse.setMessage("对不起，您无该接口访问权限！");
        apiResponse.setCode(HttpStatus.FORBIDDEN.value() + "");
        apiResponse.setData(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(JSONObject.toJSONString(apiResponse));
      }
    }
  }

  public void setErrorPage(String errorPage) {
    if (errorPage != null && !errorPage.startsWith("/")) {
      throw new IllegalArgumentException("errorPage must begin with '/'");
    } else {
      this.errorPage = errorPage;
    }
  }
}
