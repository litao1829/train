package com.litao.train.business.mapper;

import com.litao.train.business.domain.App;
import com.litao.train.business.domain.AppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppMapper {
    long countByExample(AppExample example);

    int deleteByExample(AppExample example);

    int deleteByPrimaryKey(Long id);

    int insert(App row);

    int insertSelective(App row);

    List<App> selectByExample(AppExample example);

    App selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") App row, @Param("example") AppExample example);

    int updateByExample(@Param("row") App row, @Param("example") AppExample example);

    int updateByPrimaryKeySelective(App row);

    int updateByPrimaryKey(App row);
}