package com.sxkl.workManagement.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxkl.common.utils.MessageUtils;
import com.sxkl.common.utils.ServletHelp;
import com.sxkl.workManagement.service.WorkManageService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 工作管理控制层
 * 
 * @author wangyao
 * @date 2015-11-17
 */

@Controller
@RequestMapping("/workManage")
@Api(value = "workManage-api", description = "operations for workManage", position = 4)
public class WorkManageController {

	private final static String UPLOAD_SUCCESS = "{success:true,mess:'文件上传成功!'}";  
	private final static String UPLOAD_FAILURE = "{success:false,mess:'文件上传失败!'}";  
	private final static String FILE_NO = "{success:false,mess:'文件不存在!'}";  
	private final static String FILE_YES = "{success:true,mess:'文件存在!'}";  
	private final static String CONTENT_TYPE = "text/html;charset=utf-8";  
	private final static String APPLICATION = "application/octet-stream"; 
	
	@Autowired
	@Qualifier("workManageServiceImpl")
	private WorkManageService workManageServiceImpl;

	@ResponseBody
	@RequestMapping(value = "/getWorkMainPage.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ApiOperation(value = "分页获取工作日志信息", httpMethod = "POST", response = String.class, responseContainer = "String")
	public String getWorkMainPage(int start, int limit, String userId) {
		String result = this.workManageServiceImpl.getWorkMainPage(start,limit, userId);
		return result;
	}

	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value = "/addWorkMainByExcel.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ApiOperation(value = "excel导入工作日志信息", httpMethod = "POST", response = String.class, responseContainer = "String")
	public void addWorkMainByExcel(String userId, HttpServletRequest request,HttpServletResponse response) {
		String savePath = ServletHelp.getRealPath(
				request, MessageUtils.setParamMessage("/websrc/file/{0}/excel/",
						new String[] { userId }));
		File temp = new File(savePath);
		if(!temp.exists() && !temp.isDirectory()){
			temp.mkdir();
		}
		String realPath = "";
		String title = "";
		response.setContentType(CONTENT_TYPE);  
	    PrintWriter out = null;
	    String name = null;  
	    try {  
	    	out = response.getWriter();  
	    	if (!ServletFileUpload.isMultipartContent(request)){  
		        out.println(UPLOAD_FAILURE);  
		    }  
	        DiskFileItemFactory factory = new DiskFileItemFactory();  
	        factory.setSizeThreshold(4096);  
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	        upload.setSizeMax(4 * 1024 * 1024);  
	        upload.setHeaderEncoding("utf-8");
	        Iterator<?> iter = upload.parseRequest(request).iterator();  
	        while (iter.hasNext()) {  
	            FileItem item = (FileItem) iter.next();  
	            if (item.isFormField()) {  
	                name = item.getFieldName();  
	                out.println(UPLOAD_FAILURE);  
	            } else {  
	                name = item.getName();  
	                if(savePath==null || savePath==""){  
	                    out.println(UPLOAD_FAILURE);  
	                }else{
	                	realPath = savePath+ "\\" + name.substring(name
								.lastIndexOf(File.separator) + 1, name
								.length());
	                    item.write(new File(realPath)); //保存上传文件  
	                    title = name.substring(0, name.lastIndexOf("."));
	                    out.println(UPLOAD_SUCCESS);  
	                }  
	            }  
	        }  
	        this.workManageServiceImpl.addWorkMainByExcel(userId,title,realPath);
	        this.workManageServiceImpl.deleteExcel(realPath);
	        out.close();  
	     } catch (Exception e) {  
	    	 e.printStackTrace();
	        out.println(UPLOAD_FAILURE);  
	        out.close();  
	     }
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteExcel.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ApiOperation(value = "删除excel工作日志信息", httpMethod = "POST")
	public void deleteExcel(@ApiParam(required=true,name="path",value="excel路径") @RequestParam(value="path")String path){
		this.workManageServiceImpl.deleteExcel(path);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getWorkMainById.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ApiOperation(value = "根据主键获取工作日志信息", httpMethod = "POST")
	public String getWorkMainById(@ApiParam(required=true,name="wmId",value="工作日志主键") @RequestParam(value="wmId")String wmId){
        String result = this.workManageServiceImpl.getWorkMainById(wmId);
        return result;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/getWorkItemsByWmId.do", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ApiOperation(value = "根据主键获取工作日志项信息", httpMethod = "POST")
	public String getWorkItemsByWmId(@ApiParam(required=true,name="wmId",value="工作日志主键") @RequestParam(value="wmId")String wmId){
        String result = this.workManageServiceImpl.getWorkItemsById(wmId);
        return result;
	}

}
