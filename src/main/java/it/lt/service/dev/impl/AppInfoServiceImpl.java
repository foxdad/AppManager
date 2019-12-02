package it.lt.service.dev.impl;
import it.lt.dao.dev.IAppInfo;
import it.lt.entity.AppInfo;
import it.lt.service.dev.IAppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Service
public class AppInfoServiceImpl implements IAppInfoService {
    @Autowired
    private IAppInfo appInfo;
    @Override
    public List<AppInfo> findAll() {
        return appInfo.findAll();
    }

    @Override
    public List<AppInfo> getAppInfoList(String querySoftwareName,Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer currentPageNo, Integer pageSize) throws Exception {
        return appInfo.getAppInfoList(querySoftwareName,queryCategoryLevel1,
                                        queryCategoryLevel2,queryCategoryLevel3,queryFlatformId
                                            ,currentPageNo,pageSize);
    }

    @Override
    public AppInfo getApkName(Integer id) {
        return appInfo.getApkName(id);
    }

    /**
     * appinfo添加
     *
     * @param app
     * @return
     */
    @Override
    public int addInfo(AppInfo app) {
        return appInfo.addInfo(app);
    }

    /**
     * 根据名字查询
     *
     * @param ApkName
     * @return
     */
    @Override
    public AppInfo getApkName2(String ApkName) {
        return appInfo.getApkName2(ApkName);
    }

    /**
     * 修改页面的跳转
     *
     * @param id
     * @param APKName
     * @return
     */
    @Override
    public AppInfo getAppInfo(Integer id, String APKName) {
        return appInfo.getAppInfo(id,APKName);
    }

    @Override
    public void updateInfo(Integer id) {
        appInfo.updateInfo(id);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteInfo(Integer id) {
        return appInfo.deleteInfo(id)==1 ;
    }

    @Override
    public int getcount() {
        return appInfo.getcount();
    }

    /**
     * 修改操作
     *
     * @param app
     * @return
     */
    @Override
    public int modifyInfo(AppInfo app) {
        return appInfo.modifyInfo(app) ;
    }

    /**
     * 修改logo文件路径为null
     *
     * @param id
     * @return
     */
    @Override
    public int deleteAppLogo(Integer id) {
        return appInfo.deleteAppLogo(id);
    }

}
