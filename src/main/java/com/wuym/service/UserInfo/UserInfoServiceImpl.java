package com.wuym.service.UserInfo;

import com.wuym.dao.UserInfoDao;
import com.wuym.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuym on 2017/6/14.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo searchForUser(int id) {
        return userInfoDao.selectById(id);
    }
}
