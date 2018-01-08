package cn.zsd.repository;

import cn.zsd.model.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Da on 2017/2/19.
 */
@Repository
public interface TypeRepository  extends JpaRepository<TypeEntity, Integer> {
    // 修改博文操作
    @Modifying  // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update TypeEntity type set type.name=:qName, type.description=:qDescription where type.id=:qId")
    void updateType(@Param("qName") String name, @Param("qDescription") String description, @Param("qId") int id);
}
