package com.sxkl.attendence.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxkl.attendence.service.LeaveRecordService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/leaveRecord")
@Api(value="leaveRecord-api",description="operations for leave record", position=3)
public class LeaveRecordController {
	
	@Autowired
	@Qualifier("leaveRecordServiceImpl")
	private LeaveRecordService leaveRecordServiceImpl;
	
	@ResponseBody
	@RequestMapping(value="/addLeaveRecord.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="添加请假记录",httpMethod="POST",response=String.class,notes="返回操作结果",responseContainer="String")
	public String addLeaveRecord(String leaveRecordData, String userId){
		String result = leaveRecordServiceImpl.addLeaveRecord(leaveRecordData,userId);
		return result;
	}
	
}
