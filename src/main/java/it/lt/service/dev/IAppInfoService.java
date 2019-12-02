package it.lt.service.dev;

import it.lt.entity.AppInfo;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
public interface IAppInfoService {
    List<AppInfo> findAll();
    List<AppInfo> getAppInfoList(String querySoftwareName,
                                 Integer queryCategoryLevel1,
                                 Integer queryCategoryLevel2,
                                 Integer queryCategoryLevel3,
                                Integer queryFlatformId,
                                Integer currentPageNo,
                                Integer pageSize)throws Exception;

    AppInfo getApkName(Integer id);

    /**
     * appinfo添加
     * @param appInfo
     * @return
     */
    int addInfo(AppInfo appInfo);

    /**
     * 根据名字查询
     * @param ApkName
     * @return
     */
    AppInfo getApkName2(String ApkName);

    /**
     * 修改页面的跳转
     * @param id
     * @param APKName
     * @return
     */
    AppInfo getAppInfo(Integer id,String APKName);

    void updateInfo(Integer id);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteInfo(Integer id);
    int getcount();
    /**
     * 修改操作
     * @param appInfo
     * @return
     */
    int modifyInfo(AppInfo appInfo);
    /**
     * 修改logo文件路径为null
     * @param id
     * @return
     */
    int deleteAppLogo(Integer id);






}
