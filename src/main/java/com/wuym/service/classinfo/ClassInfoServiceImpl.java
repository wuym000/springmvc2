package com.wuym.service.classinfo;

import com.wuym.dao.ClassInfoDao;
import com.wuym.model.Classinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuym on 2017/6/14.
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService{
    @Autowired
    private ClassInfoDao classInfoDao;

    @Override
    public Classinfo searchForClass(int id) {
        return classInfoDao.selectById(id);
    }
}
