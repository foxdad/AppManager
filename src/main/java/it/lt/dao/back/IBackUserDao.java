package it.lt.dao.back;

import it.lt.entity.AppInfo;
import it.lt.entity.BackendUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/29
 */
@Repository
public interface IBackUserDao {
    /**
     * 后台登录验证
     * @param username
     * @param password
     * @return
     */
    BackendUser findBack(@Param("username") String username, @Param("password") String password);


    List<AppInfo> findAll();
    List<AppInfo> getAppInfoList(@Param(value="softwareName")String querySoftwareName,
                                 @Param(value="categoryLevel1")Integer queryCategoryLevel1,
                                 @Param(value="categoryLevel2")Integer queryCategoryLevel2,
                                 @Param(value="categoryLevel3")Integer queryCategoryLevel3,
                                 @Param(value="flatformId")Integer queryFlatformId,
                                 @Param(value="from")Integer currentPageNo,
                                 @Param(value="pageSize")Integer pageSize)throws Exception;
    int getcount();


    int updateSatus(@Param(value="status")Integer status,@Param(value="id")Integer id);


}
