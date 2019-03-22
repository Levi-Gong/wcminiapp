package org.ln.wechat.miniapp.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ln.wechat.miniapp.bean.user.QcUserBean;
import org.ln.wechat.miniapp.mapper.UserMapper;
import org.ln.wechat.miniapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServieImpl implements UserService {
  @Autowired private UserMapper userMapper;

  @Override
  public QcUserBean findById(Integer userId) {
    return userMapper.selectById(userId);
  }

  @Override
  public QcUserBean findUserByNickName(String nickName) {
    QueryWrapper<QcUserBean> queryWrapper = new QueryWrapper<>();
    QcUserBean qcUserBean = new QcUserBean();
    qcUserBean.setNickName(nickName);
    queryWrapper.setEntity(qcUserBean);
    return userMapper.selectOne(queryWrapper);
  }

  @Override
  public int updateUserSelectiveById(QcUserBean qcUserBean) {
    return userMapper.updateById(qcUserBean);
  }

  /**
   * 根据微信 open_id查找用户
   *
   * @param openId
   * @return
   */
  @Override
  public QcUserBean findUserByOpenId(String openId) {
    QueryWrapper<QcUserBean> queryWrapper = new QueryWrapper<>();
    QcUserBean qcUserBean = new QcUserBean();
    qcUserBean.setOpenId(openId);
    queryWrapper.setEntity(qcUserBean);
    return userMapper.selectOne(queryWrapper);
  }

  @Override
  public int saveUser(QcUserBean userBean) {
    return userMapper.insert(userBean);
  }
}
