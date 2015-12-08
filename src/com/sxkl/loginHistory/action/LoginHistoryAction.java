package com.sxkl.loginHistory.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxkl.loginHistory.service.LoginHistoryService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/loginHistory")
@Api(value="loginHistory-api",description="operations from login's history",position=2)
public class LoginHistoryAction {
	
	@Autowired
    @Qualifier("loginHistoryServiceImpl")
	private LoginHistoryService loginHistoryServiceImpl;
	
	@ResponseBody
	@RequestMapping(value="/addLoginHistory.do",method=RequestMethod.POST,produces="application/json;charset=utf-8") 
	@ApiOperation(value="添加登陆信息",notes="返回登陆信息ID",response=String.class,responseContainer="String",httpMethod="POST")
	public String addLoginHistory(@ApiParam(required=true,name="userId",value="当前登陆人ID") @RequestParam(value="userId")String userId){
		String loginHistoryId = loginHistoryServiceImpl.addLoginHistory(userId);
		return loginHistoryId;
	}
	
	@ResponseBody
	@RequestMapping(value="/getLoginHistory.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="分页查询当前登陆人登陆记录",notes="查询结果已json形式返回",response=String.class,responseContainer="String",httpMethod="POST")
	public String getLoginHistory(@RequestParam(value="start")int start,@RequestParam(value="limit")int limit){
		String data = loginHistoryServiceImpl.getLoginHistory(start,limit);
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value="/setLoginOutTime.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="设置登出时间",notes="用户登出时,设置登陆记录的登出时间",response=String.class,responseContainer="String",httpMethod="POST")
	public void setLoginOutTime(@ApiParam(required=true,name="loginHistoryId",value="当前登录人登陆记录ID") @RequestParam(value="loginHistoryId")String loginHistoryId){
		loginHistoryServiceImpl.updateLoginOutTime(loginHistoryId);
	}
	
	@ResponseBody
	@RequestMapping(value="/getLoginHistoryInfos.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="获取当前登录人登陆历史记录信息",notes="查结果已json格式返回",response=String.class,responseContainer="String",httpMethod="POST")
	public String getLoginHistoryInfos(@ApiParam(required=true,name="userId",value="当前登录人ID") @RequestParam(value="userId")String userId){
		String loginHistoryInfos = loginHistoryServiceImpl.getLoginHistoryInfos(userId);
		return loginHistoryInfos;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCurrentMonthLoginHistory.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="获取当月扥了信息",notes="拼接当月登录人登陆信息曲线图字符串",response=String.class,responseContainer="String",httpMethod="POST")
	public String getCurrentMonthLoginHistory(@ApiParam(required=true,name="userId",value="当前登录人ID") @RequestParam(value="userId")String userId){
		String result = loginHistoryServiceImpl.getCurrentMonthLoginHistory(userId);
		return result;
	}

}
