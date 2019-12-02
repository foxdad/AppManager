package it.lt.controller.dev;

import com.alibaba.fastjson.JSONArray;
import it.lt.entity.*;
import it.lt.entity.tool.Constants;
import it.lt.entity.tool.PageSupport;
import it.lt.service.dev.IAppCategoryService;
import it.lt.service.dev.IAppInfoService;
import it.lt.service.dev.IAppVersionService;
import it.lt.service.dev.impl.AppInfoServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Controller
@RequestMapping("/app")
public class AppInfoController {
    private  Set<DataDictionary> statusList =new HashSet<>(); //状态集合
    private  Set<DataDictionary> flatFormList = new HashSet<>();//平台集合
    private  Set<AppCategory> categoryLevel1List = new HashSet<>();//一级分类集合
    private  Set<AppCategory> categoryLevel2List = new HashSet<>();//二级分类集合
    private  Set<AppCategory> categoryLevel3List = new HashSet<>();//三级分类集合
    @Autowired
    private IAppCategoryService appCategoryService;
    @Autowired
    private IAppInfoService appInfoService;
    @Autowired
    private IAppVersionService appVersionService;
    @PostMapping("/list")
    public String getAppInfo(HttpServletRequest request,@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
                             @RequestParam(value="queryCategoryLevel1",required=false) String _queryCategoryLevel1,
                             @RequestParam(value="queryCategoryLevel2",required=false) String _queryCategoryLevel2,
                             @RequestParam(value="queryCategoryLevel3",required=false) String _queryCategoryLevel3,
                             @RequestParam(value="queryFlatformId",required=false) String _queryFlatformId,
                             @RequestParam(value="pageIndex",required=false) String pageIndex){
        List<AppInfo> appinfos = appInfoService.findAll();

        //分页
        PageSupport pageSupport = new PageSupport();
        //总页数
        pageSupport.setTotalCount(appInfoService.getcount());
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
           appInfoList = appInfoService.getAppInfoList(querySoftwareName,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,queryFlatformId, (pageSupport.getCurrentPageNo()-1)*pageSupport.getPageSize(), pageSupport.getPageSize());
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
        request.setAttribute("statusList",statusList);
        request.setAttribute("flatFormList",flatFormList);
        request.setAttribute("categoryLevel1List",categoryLevel1List);
        request.setAttribute("pages",pageSupport);
        return "main/appinfolist";
    }
    @GetMapping("/list")
    public String  list(HttpServletRequest request){
        List<AppInfo> appinfos = appInfoService.findAll();

        //分页
        PageSupport pageSupport = new PageSupport();
        //总页数
        pageSupport.setTotalCount(appinfos.size());

        //AppCategory.xml appCategory = new AppCategory.xml();
        //DataDictionary dataDictionary = new DataDictionary();
        //System.out.println(appinfos);
        for (AppInfo item:appinfos) {
            //状态
            DataDictionary dataDictionary = new DataDictionary();
            dataDictionary.setValueId(item.getStatus());
            dataDictionary.setValueName(item.getStatusName());
            statusList.add(dataDictionary);
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
        //sublist返回的是原集合的
        List<AppInfo> newList = appinfos.subList(0,3);

        request.setAttribute("appInfoList",newList);
        request.setAttribute("statusList",statusList);
        request.setAttribute("flatFormList",flatFormList);
        request.setAttribute("categoryLevel1List",categoryLevel1List);
        request.setAttribute("pages",pageSupport);
        return "main/appinfolist";
    }
    //跳转的时候需要传入的数据
    @GetMapping("/addlocal")
    public String addLocal(HttpServletRequest request){
        //request.setAttribute("statusList",statusList);
        request.setAttribute("flatFormList",flatFormList);
        //request.setAttribute("categoryLevel1List",categoryLevel1List);
        //request.setAttribute("categoryLevel2List",categoryLevel2List);
        //request.setAttribute("categoryLevel3List",categoryLevel3List);
        return "main/appinfoadd";
    }
    //版本号跳转
    @GetMapping("appversionAdd/{id}")
    public String appversionAdd(@PathVariable Integer id,Model model){
        List<AppVersion> versions = appVersionService.findVersions(id);

        model.addAttribute("appVersionList",versions);

        model.addAttribute("appVersionID",id);

        return "main/appversionadd";
    }

    //版本号修改跳转
    @GetMapping("appversionmodify/{id}")
    public String appversionmodify(@PathVariable Integer id,Model model){
        List<AppVersion> versions = appVersionService.findVersions(id);
        AppVersion versions1 = versions.get(0);
        model.addAttribute("appVersions",versions1);

        model.addAttribute("appVersionList",versions);

        //model.addAttribute("appVersionID",id);

        return "main/appversionmodify";
    }
    //版本添加
    @PostMapping("/addversionsave")
    public String addversionsave(AppVersion appVersion, HttpSession session, HttpServletRequest request,
                                 @RequestParam(value="a_downloadLink",required= false) MultipartFile attach ){
        //System.out.println("springmvc文件上传...");
        ////上传的位置
        //String path = request.getSession().getServletContext().getRealPath("/uploadfiles/");
        //System.out.println(path);
        ////判断该路径是否存在
        //File file = new File(path);
        //if (!file.exists()) {
        //    file.mkdirs();
        //}
        ////上传文件项
        //String filename = upload.getOriginalFilename();
        //System.out.println(filename);
        //try {
        //    upload.transferTo(new File(path, filename));
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        String downloadLink =  null;
        String apkLocPath = null;
        String apkFileName = null;
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            if(prefix.equalsIgnoreCase("jpg")){//apk文件命名：apk名称+版本号+.apk
                String apkName = null;
                try {
                    //获取apk的名称
                    apkName = appInfoService.getApkName(appVersion.getAppId()).getAPKName();
                    System.out.println(apkName);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if(apkName == null || "".equals(apkName)){
                    return "redirect:/app/appversionadd?id="+appVersion.getAppId()+"&error=error1";
                }
                apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
                File targetFile = new File(path,apkFileName);
                //没有此文件夹则创建
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                try {
                    //添加到目标文件
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "redirect:/app/appversionadd?id="+appVersion.getAppId()
                            +"&error=error2";
                }
                downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
                apkLocPath = path+File.separator+apkFileName;
            }else{
                return "redirect:/app/appversionadd/id="+appVersion.getAppId()
                        +"&error=error3";
            }
        }
        appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appVersion.setCreationDate(new Date());
        appVersion.setDownloadLink(downloadLink);
        appVersion.setApkLocPath(apkLocPath);
        appVersion.setApkFileName(apkFileName);
        try {
            if(appVersionService.addVersion(appVersion)==1){
                //并修改最新版本
                appInfoService.updateInfo(appVersion.getAppId());
                return "redirect:/app/list";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/app/appversionadd?id="+appVersion.getAppId();
        //return "";
    }
    //info保存操作
    @PostMapping("/appinfoaddsave")
    public String addSave(AppInfo appInfo,HttpSession session,HttpServletRequest request,
                          @RequestParam(value="a_logoPicPath",required= false) MultipartFile attach){
        String logoPicPath =  null;
        String logoLocPath =  null;
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            int filesize = 500000;
            if(attach.getSize() > filesize){//上传大小不得超过 50MB
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
                return "/app/addlocal";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                    ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
                String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
                File targetFile = new File(path,fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
                    return "developer/addlocal";
                }
                logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                logoLocPath = path+File.separator+fileName;
            }else{
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
                return "developer/addlocal";
            }
        }
        appInfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setCreationDate(new Date());
        appInfo.setLogoPicPath(logoPicPath);
        appInfo.setLogoLocPath(logoLocPath);
        appInfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setStatus(1);
        try {
            if(appInfoService.addInfo(appInfo)==1){
                return "redirect:/app/list";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/app/addlocal";
    }
    //动态加载
    @ResponseBody
    @GetMapping("/queryCategoryLevel1/{id}")
    public List<AppCategory> queryCategoryLevel1(@PathVariable Integer id) {
        //appCategoryService.getParent(id);
        List<AppCategory> parent = appCategoryService.getParent1(id);

        return parent;
    }
    //修改info跳转
    @GetMapping("/modify/{id}")
    public String  midify (@PathVariable Integer id,Model model) {
        AppInfo appInfo = appInfoService.getAppInfo(id, null);
        model.addAttribute("appInfo",appInfo);
        model.addAttribute("flatFormList",flatFormList);
        return "main/appinfomodify";
    }
    //动态加载一级分类
    @ResponseBody
    @GetMapping("/queryCategoryLevel3")
    public List<AppCategory> queryCategoryLevel3() {
        //appCategoryService.getParent(id);
        List<AppCategory> parent = appCategoryService.findAll();
        return parent;
    }
    //根据apkname查询
    @GetMapping("/getApkName2")
    @ResponseBody
    public AppInfo getApkName2(String APKName){
        AppInfo apkName2 = appInfoService.getApkName2(APKName);
        return apkName2;
    }
    //查看跳转
    @GetMapping("/viewapp/{id}")
    public ModelAndView appVersionView(@PathVariable("id") Integer id, Model model){
        ModelAndView modelAndView = new ModelAndView("main/appinfoview");
        AppInfo appInfo = appInfoService.getAppInfo(id,null);
        List<AppVersion> versions = appVersionService.findVersions(id);
        model.addAttribute("appInfo",appInfo);
        model.addAttribute("appVersionList",versions);
        return modelAndView;
    }
    //修改保存操作
    @GetMapping("/appmodifyInfosave")
    public String modifyAppInfo(AppInfo appInfo,HttpSession session,HttpServletRequest request,
                                @RequestParam(value="a_logoPicPath",required= false) MultipartFile attach){
        String logoPicPath =  null;
        String logoLocPath =  null;
        String APKName = appInfo.getAPKName();
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            int filesize = 500000;
            if(attach.getSize() > filesize){//上传大小不得超过 50k
                return "redirect:/app/modify/"+appInfo.getId()
                        +"&error=error4";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                    ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
                String fileName = APKName + ".jpg";//上传LOGO图片命名:apk名称.apk
                File targetFile = new File(path,fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "redirect:/app/modify/"+appInfo.getId()
                            +"&error=error2";
                }
                logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                logoLocPath = path+File.separator+fileName;
            }else{
                return "redirect:/app/modify/"+appInfo.getId()
                        +"&error=error3";
            }
        }
        appInfo.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setModifyDate(new Date());
        appInfo.setLogoLocPath(logoLocPath);
        appInfo.setLogoPicPath(logoPicPath);
        try {
            if(appInfoService.modifyInfo(appInfo)==1){
                return "redirect:/app/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/app/modify";
    }

    /**
     * 修改最新版本操作
     * @param appVersion
     * @param session
     * @param request
     * @param attach
     * @return
     */
    @PostMapping("/appversionmodifysave")
    public String modifyAppVersionSave(AppVersion appVersion,HttpSession session,HttpServletRequest request,
                                       @RequestParam(value="attach",required= false) MultipartFile attach){

        String downloadLink =  null;
        String apkLocPath = null;
        String apkFileName = null;
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            if(prefix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
                String apkName = null;
                try {
                    apkName = appInfoService.getAppInfo(appVersion.getAppId(),null).getAPKName();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if(apkName == null || "".equals(apkName)){
                    return "redirect:/app/appversionmodify?vid="+appVersion.getId()
                            +"&aid="+appVersion.getAppId()
                            +"&error=error1";
                }
                apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
                File targetFile = new File(path,apkFileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "redirect:/app/appversionmodify?vid="+appVersion.getId()
                            +"&aid="+appVersion.getAppId()
                            +"&error=error2";
                }
                downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
                apkLocPath = path+File.separator+apkFileName;
            }else{
                return "redirect:/app/appversionmodify?vid="+appVersion.getId()
                        +"&aid="+appVersion.getAppId()
                        +"&error=error3";
            }
        }
        appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appVersion.setModifyDate(new Date());
        appVersion.setDownloadLink(downloadLink);
        appVersion.setApkLocPath(apkLocPath);
        appVersion.setApkFileName(apkFileName);
        try {
            if(appVersionService.modifyVersion(appVersion)==1){
                return "redirect:/app/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/app/appversionmodify";
    }

    /**
     * 删除版本操作
     * @param id
     * @return
     */
    @GetMapping("/deleteAll/{id}")
    @ResponseBody
    public boolean  deleteAll(@PathVariable("id") Integer id){
        //boolean versionBool = appVersionService.deleteVersion(id);
        boolean InfoBool = appInfoService.deleteInfo(id);

        return InfoBool;
    }

    /**
     * 删除文件操作
     * @param flag
     * @param id
     * @return
     */
    @GetMapping("/delfile/{id}-{flag}")
    @ResponseBody
    public Object delFile(@PathVariable("flag") String flag,
                          @PathVariable("id") String id){
        HashMap<String, String> resultMap = new HashMap();//返回结果
        String fileLocPath = null;
        if(flag == null || flag.equals("") ||
                id == null || id.equals("")){
            resultMap.put("result", "failed");
        }else if(flag.equals("logo")){//删除logo图片（操作app_info）
            try {
                fileLocPath = (appInfoService.getAppInfo(Integer.parseInt(id), null)).getLogoLocPath();
                File file = new File(fileLocPath);
                if(file.exists())
                    if(file.delete()){//删除服务器存储的物理文件
                        if(appInfoService.deleteAppLogo(Integer.parseInt(id))==1){//更新表
                            resultMap.put("result", "success");
                        }
                    }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else if(flag.equals("apk")){//删除apk文件（操作app_version）
            try {
                fileLocPath = (appVersionService.findVersions(Integer.parseInt(id)).get(0)).getApkLocPath();
                File file = new File(fileLocPath);
                if(file.exists())
                    if(file.delete()){//删除服务器存储的物理文件
                        if(appVersionService.deleteApkFile(Integer.parseInt(id))==1){//更新表
                            resultMap.put("result", "success");
                        }
                    }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    return JSONArray.toJSONString(resultMap);

    }

    ////下架操作
    //@RequestMapping(value="/{appid}/sale",method=RequestMethod.PUT)
    //@ResponseBody
    //public Object sale(@PathVariable String appid,HttpSession session){
    //    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    //    Integer appIdInteger = 0;
    //    try{
    //        appIdInteger = Integer.parseInt(appid);
    //    }catch(Exception e){
    //        appIdInteger = 0;
    //    }
    //    resultMap.put("errorCode", "0");
    //    resultMap.put("appId", appid);
    //    if(appIdInteger>0){
    //        try {
    //            DevUser devUser = (DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
    //            AppInfo appInfo = new AppInfo();
    //            appInfo.setId(appIdInteger);
    //            appInfo.setModifyBy(devUser.getId());
    //            if(appInfoService.updateSatus(appInfo)){
    //                resultMap.put("resultMsg", "success");
    //            }else{
    //                resultMap.put("resultMsg", "success");
    //            }
    //        } catch (Exception e) {
    //            resultMap.put("errorCode", "exception000001");
    //        }
    //    }else{
    //        //errorCode:0为正常
    //        resultMap.put("errorCode", "param000001");
    //    }
    //    return resultMap;
    //}

}
