package it.lt.service.dev;



import it.lt.entity.AppCategory;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
public interface IAppCategoryService {
    List<AppCategory> getParent1(Integer id);
    List<AppCategory> findAll();

}
