package it.lt.controller.back;

import it.lt.entity.*;
import it.lt.entity.tool.PageSupport;
import it.lt.service.back.IBackUserService;
import it.lt.service.dev.IAppCategoryService;
import it.lt.service.dev.IAppInfoService;
import it.lt.service.dev.IAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/29
 */
@Controller
@RequestMapping("/back")
public class BackController {
    private Set<DataDictionary> flatFormList = new HashSet<>();//平台集合
    private  Set<AppCategory> categoryLevel1List = new HashSet<>();//一级分类集合
    /**
     * 后台登录页面跳转
     * @return
     */
    @Autowired
    private IBackUserService backUserService;
    @Autowired
    private IAppInfoService appInfoService;
    @Autowired
    private IAppCategoryService appCategoryService;
    @Autowired
    private IAppVersionService appVersionService;

    @PostMapping("/backloginrequired")
    public String backLoginrRquired(String userCode,String userPassword,Model model,HttpServletRequest request){
        BackendUser back = backUserService.findBack(userCode, userPassword);
        if (back==null) {
                model.addAttribute("error","用户名或者密码错误");
        }
        request.getSession().setAttribute("userSession",back);
        //model.addAttribute("userSession",back);
        return "back/main";
    }
    @PostMapping("/list")
    public String getAppInfo(HttpServletRequest request,@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
                             @RequestParam(value="queryCategoryLevel1",required=false) String _queryCategoryLevel1,
                             @RequestParam(value="queryCategoryLevel2",required=false) String _queryCategoryLevel2,
                             @RequestParam(value="queryCategoryLevel3",required=false) String _queryCategoryLevel3,
                             @RequestParam(value="queryFlatformId",required=false) String _queryFlatformId,
                             @RequestParam(value="pageIndex",required=false) String pageIndex){
        //List<AppInfo> appinfos = appInfoService.findAll();

        //分页
        PageSupport pageSupport = new PageSupport();
        //总页数
        pageSupport.setTotalCount(backUserService.getcount());
        //传入的页码
        if(pageIndex != null){
            try{
                pageSupport.setCurrentPageNo(Integer.valueOf(pageIndex));
            }catch (NumberFormatException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        Integer queryCategoryLevel1 = null;
        if(_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")){
            queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
        }
        Integer queryCategoryLevel2 = null;
        if(_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")){
            queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
        }
        Integer queryCategoryLevel3 = null;
        if(_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")){
            queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
        }
        Integer queryFlatformId = null;
        if(_queryFlatformId != null && !_queryFlatformId.equals("")){
            queryFlatformId = Integer.parseInt(_queryFlatformId);
        }
        List<AppInfo> appInfoList = null;
        try {
            appInfoList = backUserService.getAppInfoList(querySoftwareName,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId, (pageSupport.getCurrentPageNo()-1)*pageSupport.getPageSize(), pageSupport.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //控制首页和尾页
        if(pageSupport.getCurrentPageNo()  < 1){
            pageSupport.setCurrentPageNo(1);
        }else if(pageSupport.getCurrentPageNo() > pageSupport.getTotalPageCount()){
            pageSupport.setCurrentPageNo(pageSupport.getTotalPageCount());
        }
        request.setAttribute("appInfoList",appInfoList);
        request.setAttribute("flatFormList",flatFormList);
        request.setAttribute("categoryLevel1List",categoryLevel1List);
        request.setAttribute("pages",pageSupport);
        return "back/applist";
    }
    //跳到后台主页面
    @GetMapping("/list")
    public String  list(HttpServletRequest request){
        List<AppInfo> appinfos = backUserService.findAll();
        PageSupport pageSupport = new PageSupport();
        //总页数
        pageSupport.setTotalCount(appinfos.size());
        for (AppInfo item:appinfos) {
            //平台
            DataDictionary dataDictionary2 = new DataDictionary();
            dataDictionary2.setValueId(item.getFlatformId());
            dataDictionary2.setValueName(item.getFlatformName());
            flatFormList.add(dataDictionary2);
            //1
            AppCategory appCategory1 = new AppCategory();
            appCategory1.setId(item.getCategoryLevel1());
            appCategory1.setCategoryName(item.getCategoryLevel1Name());
            categoryLevel1List.add(appCategory1);
            //输出
            //System.out.println(item);
        }
        List<AppInfo> newList = appinfos.subList(0,3);

        request.setAttribute("appInfoList",newList);
        request.setAttribute("flatFormList",flatFormList);
        request.setAttribute("categoryLevel1List",categoryLevel1List);
        request.setAttribute("pages",pageSupport);
        return "back/applist";
    }
    //审核跳转
    @GetMapping("/appcheck/{id}-{vid}")
    public String check(@PathVariable("id") Integer id,@PathVariable("vid") Integer vid ,Model model){
        List<AppVersion> versions = appVersionService.findVersions(id);
        AppVersion versions1 = versions.get(0);
        model.addAttribute("appVersion",versions1);
        AppInfo appInfo = appInfoService.getAppInfo(id, null);
        model.addAttribute("appInfo",appInfo);
        model.addAttribute("flatFormList",flatFormList);
        return "back/appcheck";
    }


    @PostMapping(value="/checksave")
    public String checkSave(AppInfo appInfo){
        backUserService.updateSatus(appInfo.getStatus(),appInfo.getId());
        return "back/applist";
    }






    //动态加载
    @ResponseBody
    @GetMapping("/queryCategoryLevel1/{id}")
    public List<AppCategory> queryCategoryLevel1(@PathVariable Integer id) {
        //appCategoryService.getParent(id);
        List<AppCategory> parent = appCategoryService.getParent1(id);
        return parent;
    }






}
