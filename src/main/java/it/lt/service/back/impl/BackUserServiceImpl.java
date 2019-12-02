package it.lt.service.back.impl;

import it.lt.dao.back.IBackUserDao;
import it.lt.entity.AppInfo;
import it.lt.entity.BackendUser;
import it.lt.service.back.IBackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/29
 */
@Service
public class BackUserServiceImpl implements IBackUserService {
    /**
     * 后台登录验证
     *
     * @param username
     * @param password
     * @return
     */
    //@Qualifier("IBackUserDao")
    @Autowired
    private IBackUserDao backUserDao;
    @Override
    public BackendUser findBack(String username, String password) {
        return backUserDao.findBack(username,password);
    }

    @Override
    public List<AppInfo> findAll() {
        return backUserDao.findAll();
    }

    @Override
    public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer currentPageNo, Integer pageSize) throws Exception {
        return backUserDao.getAppInfoList(querySoftwareName,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId,currentPageNo,pageSize);
    }

    @Override
    public int getcount() {
        return backUserDao.getcount();
    }

    @Override
    public boolean updateSatus(Integer status, Integer id) {
        return backUserDao.updateSatus(status,id)==1;
    }
}
