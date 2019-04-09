package org.ln.wechat.miniapp.bean.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("qc_role")
public class QcRoleBean implements Serializable {
  private Integer id;

  private String roleName;

  private Integer superiorRoleId;

  private Date createTime;

  private Date modifyTime;
}
