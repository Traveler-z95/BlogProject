package cn.zsd.service;

import cn.zsd.model.BlogEntity;

import java.sql.Date;
import java.util.List;

/**
 * Created by Da on 2017/8/7.
 */
public interface BlogService {
    List<BlogEntity> findByCondition(String title,Date minDate, Date maxDate);
}
