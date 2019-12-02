package it.lt.dao.dev;

import it.lt.entity.AppVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/27
 */
@Repository
public interface IAppVersion {
    List<AppVersion> findVersions(@Param("appId") Integer parentId);

    /**
     * 版本添加操作
     * @param appVersion
     * @return
     */
    int addVersion(AppVersion appVersion);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteVersion(@Param("id") Integer id);
    int modifyVersion(AppVersion appVersion);

    /**
     * 删除apk文件
     * @param id
     * @return
     */
    int deleteApkFile(Integer id);
}
