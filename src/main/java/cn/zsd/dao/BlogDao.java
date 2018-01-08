package cn.zsd.dao;

import cn.zsd.model.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by Da on 2017/8/7.
 */
@Repository
public interface BlogDao extends JpaRepository<BlogEntity, Long>,
        JpaSpecificationExecutor<BlogEntity> {
  //  @Query(value = "select blog from BlogEntity blog where blog.title =?1")
  //  List<BlogEntity> findByCondition( String title,Date minDate, Date maxDate);


}
