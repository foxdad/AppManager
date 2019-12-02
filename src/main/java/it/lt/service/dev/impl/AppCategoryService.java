package it.lt.service.dev.impl;


import it.lt.dao.dev.IAppCategory;
import it.lt.entity.AppCategory;
import it.lt.service.dev.IAppCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Service
public class AppCategoryService implements IAppCategoryService {
    @Autowired
    private IAppCategory appCategory;

    @Override
    public List<AppCategory> getParent1(Integer id) {
        return appCategory.getParent1(id);
    }

    @Override
    public List<AppCategory> findAll() {
        return appCategory.findAll();
    }
}
