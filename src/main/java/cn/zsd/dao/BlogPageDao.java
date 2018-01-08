package cn.zsd.dao;

import cn.zsd.model.BlogEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by Da on 2017/7/11.
 */
@Repository
public interface BlogPageDao extends PagingAndSortingRepository<BlogEntity, Long> {
}
