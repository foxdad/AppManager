package it.lt.dao.dev;

import it.lt.entity.DevUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Repository
public interface IDevUser {
      DevUser findByName(@Param("username") String username,@Param("password") String password);
}
