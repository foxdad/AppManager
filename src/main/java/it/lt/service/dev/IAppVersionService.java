package it.lt.service.dev;

import it.lt.entity.AppVersion;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/27
 */
public interface IAppVersionService {
    List<AppVersion> findVersions(Integer parentId);

    int addVersion(AppVersion app);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteVersion( Integer id);

    /**
     * 修改版本操作
     * @param appVersion
     * @return
     */
    int modifyVersion(AppVersion appVersion);

    /**
     * 删除apk文件
     * @param id
     * @return
     */
    int deleteApkFile(Integer id);




}
