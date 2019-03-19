package org.ln.wechat.miniapp.system.security;

import com.alibaba.fastjson.JSONObject;
import org.ln.wechat.miniapp.bean.common.ApiConstant;
import org.ln.wechat.miniapp.bean.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

  @Override
  public void commence(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e)
      throws IOException {
    logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
    httpServletResponse.setContentType("application/json;charset=UTF-8");
    httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
    ApiResponse<Object> apiResponse = new ApiResponse<>();
    apiResponse.setSuccess(ApiConstant.FALSE);
    apiResponse.setMessage("登录信息失效！");
    apiResponse.setCode(HttpStatus.UNAUTHORIZED.value() + "");
    httpServletResponse.getWriter().write(JSONObject.toJSONString(apiResponse));
  }
}
