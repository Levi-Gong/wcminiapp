package org.ln.wechat.miniapp.controller.auth;

import org.ln.wechat.miniapp.biz.user.UserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired private UserBiz userBiz;

  @PostMapping("/wechatLogin")
  public ResponseEntity<?> wechatLogin(@RequestParam String code) {

    return ResponseEntity.ok(userBiz.loginByWechat(code));
  }
}
