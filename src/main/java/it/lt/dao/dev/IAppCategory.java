package it.lt.dao.dev;

import it.lt.entity.AppCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Repository
public interface IAppCategory {
    List<AppCategory> getParent1(@Param("id") Integer id);
    List<AppCategory> findAll();//动态加载一级分类

}
