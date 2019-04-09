package org.ln.wechat.miniapp.controller.auth;

import org.ln.wechat.miniapp.bean.common.ApiResponse;
import org.ln.wechat.miniapp.bean.user.QcUserBean;
import org.ln.wechat.miniapp.biz.user.UserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired private UserBiz userBiz;

  @GetMapping("/loginByWechat")
  public ApiResponse<String> wechatLogin(@RequestParam String code) {

    return userBiz.loginByWechat(code);
  }

  @PostMapping("/signUpByWechat")
  @ResponseBody
  public ApiResponse<String> signUpByWechat(@RequestBody QcUserBean user){
    return userBiz.saveUserWithWechat(user);
  }

}
