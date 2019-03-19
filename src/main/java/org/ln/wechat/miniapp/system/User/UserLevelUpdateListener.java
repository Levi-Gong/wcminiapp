package org.ln.wechat.miniapp.system.User;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class UserLevelUpdateListener {
@JmsListener(destination = "inbound.user.level.update")
    public void receiveMessage(final Message jsonMessage){
        System.out.println("Received message " + jsonMessage);
    }
}
