package org.ln.wechat.miniapp.biz.impl.user;

import org.ln.wechat.miniapp.biz.user.UserBiz;
import org.ln.wechat.miniapp.utils.EmptyUtils;
import org.springframework.stereotype.Service;

@Service
public class UserBizImpl implements UserBiz {
  /**
   * 微信授权登录
   *
   * @param code
   * @return
   */
  @Override
  public Object loginByWechat(String code) {

//      判断当前code是否为空
      if(EmptyUtils.isEmpty(code)){
          return "None_User";
      }
//      请求微信服务器，获取openid 和 sessionKey
      
    return null;
  }
}
