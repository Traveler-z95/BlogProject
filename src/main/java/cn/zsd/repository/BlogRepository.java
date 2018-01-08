package cn.zsd.repository;

import cn.zsd.model.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by Da on 2017/2/19.
 */
@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
    // 修改博文操作
    @Modifying
    @Transactional
    @Query("update BlogEntity blog set blog.title=:qTitle, blog.userByUserId.id=:qUserId, blog.typeByTypeId.id=:qTypeId," +
            " blog.content=:qContent, blog.pubDate=:qPubDate where blog.id=:qId")
    void updateBlog(@Param("qTitle") String title, @Param("qUserId") int userId, @Param("qTypeId") int typeId,@Param("qContent") String content,
                    @Param("qPubDate") Date pubDate, @Param("qId") int id);


}

