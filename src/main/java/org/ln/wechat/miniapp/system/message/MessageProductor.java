package org.ln.wechat.miniapp.system.message;

import org.ln.wechat.miniapp.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProductor {

  private static final Logger log = LoggerFactory.getLogger(MessageProductor.class);

  @Autowired private JmsTemplate jmsTemplate;

  public void sendMessage(String queenName, Object object) {

    if (EmptyUtils.isEmpty(queenName) || EmptyUtils.isEmpty(object)) {
      log.warn("---------队列名为空或消息为空，不执行消息推送！--------");
      return;
    }

    jmsTemplate.convertAndSend(queenName, object);
  }
}
