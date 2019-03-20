package org.ln.wechat.miniapp.system.message;

import com.alibaba.fastjson.JSON;
import org.ln.wechat.miniapp.bean.jms.UserLevelMessage;
import org.ln.wechat.miniapp.bean.user.QcUserBean;
import org.ln.wechat.miniapp.biz.user.UserBiz;
import org.ln.wechat.miniapp.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Date;

@Component
public class UserLevelUpdateListener {

  private static final Logger log = LoggerFactory.getLogger(UserLevelUpdateListener.class);
  @Autowired private UserBiz userBiz;
  @Autowired private MessageProductor messageProductor;

  @JmsListener(destination = "INBOUND.USER.LEVEL.UPDATE")
  public void receiveMessage(final Message jsonMessage) {

    TextMessage receiveMsg = null;
    if (jsonMessage instanceof TextMessage) {
      receiveMsg = (TextMessage) jsonMessage;
    }
    try {
      if (EmptyUtils.isEmpty(receiveMsg) || EmptyUtils.isEmpty(receiveMsg.getText())) {
        log.warn("接收到的MQ消息为空，不进行下一步处理");
        return;
      }
      UserLevelMessage userLevelMessage =
          JSON.parseObject(receiveMsg.getText(), UserLevelMessage.class);
      log.debug(userLevelMessage.toString());
      QcUserBean pUser = userBiz.getUserById(userLevelMessage.getReferrerId());
      if (EmptyUtils.isEmpty(pUser)) {
        return;
      }
      // 如果是平级，则更新推荐人的等级 往上更新一级
      if (userLevelMessage.getUserLevel().equals(pUser.getLevel())) {
        int pLevel = Integer.parseInt(userLevelMessage.getUserLevel()) - 1;
        pUser.setLevel(String.valueOf(pLevel));
        QcUserBean qcUser = new QcUserBean();
        qcUser.setId(pUser.getId());
        qcUser.setLevel(String.valueOf(pLevel));
        qcUser.setModifyTime(new Date());
        updateNext(userBiz.updateUser(qcUser), pUser);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error("转换MQ消息异常，请排查并手动更新会员等级记录");

    } finally {
      try {
        if (receiveMsg != null) {
          receiveMsg.acknowledge();
        }
      } catch (JMSException je) {
        log.error("MQ提醒反馈异常");
      }
    }
  }

  private void updateNext(int updateUser, QcUserBean userBean) {

    if (updateUser <= 0) {
      log.warn("更新上级代理等级失败，更新过程结束！");
      return;
    }
    if (EmptyUtils.isEmpty(userBean.getReferrer())) {
      log.info("当前用户无推荐人，代理等级链更新完成！");
      return;
    }
    UserLevelMessage userLevelMessage =
        UserLevelMessage.builder(userBean.getId(), userBean.getLevel(), userBean.getReferrer());
    messageProductor.sendMessage("INBOUND.USER.LEVEL.UPDATE", userLevelMessage);
  }
}
