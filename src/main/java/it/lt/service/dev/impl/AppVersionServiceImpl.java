package it.lt.service.dev.impl;

import it.lt.dao.dev.IAppVersion;
import it.lt.entity.AppVersion;
import it.lt.service.dev.IAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/27
 */
@Service
public class AppVersionServiceImpl implements IAppVersionService {
    @Autowired
    private IAppVersion IappVersionDao;
    @Override
    public List<AppVersion> findVersions(Integer parentId) {
        return IappVersionDao.findVersions(parentId);
    }

    @Override
    public int addVersion(AppVersion app) {
        return IappVersionDao.addVersion(app);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteVersion(Integer id) {
        return IappVersionDao.deleteVersion(id)==1;
    }

    /**
     * 修改版本操作
     *
     * @param appVersion
     * @return
     */
    @Override
    public int modifyVersion(AppVersion appVersion) {
        return IappVersionDao.modifyVersion(appVersion);
    }

    /**
     * 删除apk文件
     *
     * @param id
     * @return
     */
    @Override
    public int deleteApkFile(Integer id) {
        return IappVersionDao.deleteApkFile(id);
    }
}
