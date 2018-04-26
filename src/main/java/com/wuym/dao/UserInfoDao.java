package com.wuym.dao;

import com.wuym.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by wuym on 2017/6/14.
 */
public interface UserInfoDao {
    public static final String ALL_COLUMNS = "id, name, age, sex";
    @Select("select " + ALL_COLUMNS + " from userinfo where id=#{idNo}")
    public UserInfo selectById(@Param("idNo") int id);

}
