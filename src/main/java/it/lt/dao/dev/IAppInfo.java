package it.lt.dao.dev;


import it.lt.entity.AppInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Repository
public interface IAppInfo {
     List<AppInfo> findAll();
     List<AppInfo> getAppInfoList(@Param(value="softwareName")String querySoftwareName,
                                  @Param(value="categoryLevel1")Integer queryCategoryLevel1,
                                  @Param(value="categoryLevel2")Integer queryCategoryLevel2,
                                  @Param(value="categoryLevel3")Integer queryCategoryLevel3,
                                  @Param(value="flatformId")Integer queryFlatformId,
                                  @Param(value="from")Integer currentPageNo,
                                  @Param(value="pageSize")Integer pageSize)throws Exception;

     AppInfo getApkName(@Param("id") Integer id);
     int addInfo(AppInfo appInfo);
     AppInfo getApkName2(@Param("ApkName") String ApkName);

     /**
      * 查询页面跳转
      * @param id
      * @param APKName
      * @return
      */
     AppInfo getAppInfo(@Param("id") Integer id,@Param("APKName") String APKName);
     void updateInfo(@Param("id") Integer id);

     /**
      * 删除
      * @param id
      * @return
      */
     int deleteInfo(@Param("id") Integer id);

     /**
      * 获取所有数量
      * @return
      */
     int getcount();
     /**
      * 修改操作
      */
     int modifyInfo(AppInfo appInfo);

     /**
      * 修改logo文件路径为null
      * @param id
      * @return
      */
     int deleteAppLogo(Integer id);
}
