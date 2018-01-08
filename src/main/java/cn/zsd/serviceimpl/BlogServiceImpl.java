package cn.zsd.serviceimpl;

import cn.zsd.dao.BlogDao;
import cn.zsd.model.BlogEntity;
import cn.zsd.service.BlogService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Da on 2017/8/7.
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    public BlogDao blogDao;

    @Override
    public List<BlogEntity> findByCondition( String title,Date minDate, Date maxDate){
        List<BlogEntity> blog = null;
        Specification<BlogEntity> querySpecification = new Specification<BlogEntity>() {
            @Override
            public Predicate toPredicate(Root<BlogEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(null != title){
                    predicates.add(criteriaBuilder.like(root.get("title"), "%"+title+"%"));
                }
                if(null != minDate){
                    predicates.add(criteriaBuilder.greaterThan(root.get("pubDate"), minDate));
                }
                if(null != maxDate){
                    predicates.add(criteriaBuilder.lessThan(root.get("pubDate"), maxDate));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        blog = this.blogDao.findAll(querySpecification);
        return blog;
    }
}
