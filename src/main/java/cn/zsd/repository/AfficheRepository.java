package cn.zsd.repository;

import cn.zsd.model.AfficheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

/**
 * Created by Da on 2017/2/20.
 */
@Repository
public interface AfficheRepository extends JpaRepository<AfficheEntity, Integer> {

    // 修改公告操作
    @Modifying  // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update AfficheEntity affiche set affiche.title=:qTitle, affiche.content=:qContent, " +
            "affiche.pubDate=:qPubDate where affiche.id=:qId")
    void updateAffiche(@Param("qTitle") String title, @Param("qContent") String content,
                    @Param("qPubDate") Date pubDate, @Param("qId") int id);

}
