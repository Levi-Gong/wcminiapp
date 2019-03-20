package org.ln.wechat.miniapp.bean.jms;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserLevelMessage implements Serializable {

    private Integer userId;

    private String userLevel;

    private Integer referrerId;

    public static UserLevelMessage builder(Integer userId,String userLevel,Integer referrerId){
        UserLevelMessage userLevelMessage = new UserLevelMessage();
        userLevelMessage.setUserId(userId);
        userLevelMessage.setUserLevel(userLevel);
        userLevelMessage.setReferrerId(referrerId);
        return  userLevelMessage;
    }
}
