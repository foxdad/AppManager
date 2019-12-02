package it.lt.service.back;

import it.lt.entity.AppInfo;
import it.lt.entity.BackendUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/29
 */
public interface IBackUserService {
    /**
     * 后台登录验证
     * @param username
     * @param password
     * @return
     */
    BackendUser findBack(String username,String password);
    List<AppInfo> findAll();
    List<AppInfo> getAppInfoList(String querySoftwareName,
                                 Integer queryCategoryLevel1,
                                 Integer queryCategoryLevel2,
                                 Integer queryCategoryLevel3,
                                 Integer queryFlatformId,
                                 Integer currentPageNo,
                                 Integer pageSize)throws Exception;
    int getcount();
    boolean updateSatus(Integer status,Integer id);


}
