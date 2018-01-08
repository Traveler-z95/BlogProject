package cn.zsd.repository;

import cn.zsd.model.UserEntity;
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
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update UserEntity us set us.username=:qUsername, us.password=:qPassword, us.firstName=:qFirstName, us.lastName=:qLastName, us.sex=:qSex, us.eMail=:qeMail, us.interest=:qInterest where us.id=:qId")
    public void updateUser(@Param("qUsername") String username, @Param("qPassword") String password,
                           @Param("qFirstName") String firstName, @Param("qLastName") String qLastName,
                           @Param("qSex") String sex, @Param("qeMail") String eMail, @Param("qInterest") String interest,
                           @Param("qId") Integer id);
}
