package org.ln.wechat.miniapp.controller.user;

import org.ln.wechat.miniapp.bean.common.ApiResponse;
import org.ln.wechat.miniapp.bean.user.QcUserBean;
import org.ln.wechat.miniapp.biz.user.UserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired private UserBiz userBiz;

  @GetMapping("/getUser")
  @ResponseBody
  public ApiResponse<QcUserBean> getUser() {

    return userBiz.getUserDetail();
  }


}
