package cn.zsd.repository;

import cn.zsd.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by Da on 2017/2/20.
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    // 修改评论操作
    @Modifying
    @Transactional
    @Query("update CommentEntity comment set comment.blogByBlogId.id=:qBlogId," +
            " comment.content=:qContent, comment.pubDate=:qPubDate where comment.id=:qId")
    void updateComment(@Param("qBlogId") int blogId, @Param("qContent") String content,
                    @Param("qPubDate") Date pubDate, @Param("qId") int id);

    //一对多查询方法。我们要注意这个接口方法语句的拼接，很重要。
    //comment.blogByBlogId.id 特别是这里，指的是blog的id不是别的id
    @Query("select comment from CommentEntity comment where comment.blogByBlogId.id = ?1")
    List<CommentEntity> findByBlogByBlogId(int blogId);


}
