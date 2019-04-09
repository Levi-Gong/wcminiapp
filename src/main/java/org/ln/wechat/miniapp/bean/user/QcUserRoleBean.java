package org.ln.wechat.miniapp.bean.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("qc_user_role")
public class QcUserRoleBean {
  private Integer id;

  private Integer userId;

  private Integer roleId;

  private Date createTime;

  private Date modityTime;
}
