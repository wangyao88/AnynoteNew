package com.sxkl.attendence.action;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxkl.attendence.model.Holidy;
import com.sxkl.attendence.service.AttendenceTimeRecordService;
import com.sxkl.attendence.service.AttendenceTimeService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("/attendenceTime")
@Api(value="attendenceTime-api",description="operations for attendenceTime and holidy", position=1)
public class AttendenceTImeController {
	
	@Autowired
	@Qualifier("attendenceTimeServiceImpl")
	private AttendenceTimeService attendenceTImeServiceImpl;
	
	@Autowired
	@Qualifier("attendenceTimeRecordServiceImpl")
	private AttendenceTimeRecordService attendenceTimeRecordServiceImpl;
	
	@ResponseBody
	@RequestMapping(value="/getStanderAttendenceTimeInfo.do",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ApiOperation(value="获取标准考勤记录信息",httpMethod="GET",response=String.class,responseContainer="String")
	public String getStanderAttendenceTimeInfo(HttpServletRequest request){
		JSONObject standerAttendenceTimeInfo = attendenceTImeServiceImpl.getStanderAttendenceTimeInfo();
		String atId = "";
		if(standerAttendenceTimeInfo != null && standerAttendenceTimeInfo.containsKey("atId")){
			atId = standerAttendenceTimeInfo.getString("atId");
		}
		request.getSession().setAttribute("atId", atId);
		return standerAttendenceTimeInfo.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/saveHolidyInfo.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="保存节假日信息",httpMethod="POST",response=String.class,notes="返回操作结果",responseContainer="String")
	public String saveHolidyInfo(@ApiParam(required=true,name="holidy",value="节假日对象信息") @RequestParam(value="holidy") String holidy,@RequestParam(value="atId")String atId){
		String result = attendenceTImeServiceImpl.saveHolidyInfo(holidy,atId);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getStanderAttendenceTimeId.do", method=RequestMethod.POST, produces="application/json; charset=utf-8")
	@ApiOperation(value="获取考勤信息ID",httpMethod="POST",response=String.class,notes="返回考勤信息ID，并将ID存入session",responseContainer="String")
	public String getStanderAttendenceTimeId(HttpServletRequest request){
		JSONObject standerAttendenceTimeInfo = attendenceTImeServiceImpl.getStanderAttendenceTimeInfo();
		String atId = "";
		if(standerAttendenceTimeInfo != null){
			atId = standerAttendenceTimeInfo.getString("atId");
		}
		request.getSession().setAttribute("atId", atId);
		return atId;
	}
	
	@ResponseBody
	@RequestMapping(value="/getStanderAttendenceTimeHolidyInfoByhId.do", method=RequestMethod.POST, produces="application/json; charset=utf-8")
    @ApiOperation(value="根据节假日对象ID获取具体信息",httpMethod="POST", response=Holidy.class,notes="节假日对象信息以json格式返回",responseContainer = "String")
    @ApiResponses(value = {
            @ApiResponse(code=404,message="节假日对象未找到")
    })
	public String getStanderAttendenceTimeHolidyInfoByhId(@ApiParam(required=true,name="hId",value="节假日ID") @RequestParam(value="hId")String hId){
		String holidyInfo = this.attendenceTImeServiceImpl.getStanderAttendenceTimeHolidyInfoByhId(hId);
		return holidyInfo;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteHolidyInfoByIds.do",method=RequestMethod.DELETE,produces="application/json;charset=utf-8")
	@ApiOperation(value="根据节假日ID删除对象",httpMethod="DELETE",response=String.class,notes="返回操作结果",responseContainer="String")
	public String deleteHolidyInfoByIds(@ApiParam(required=true,name="hIds",value="多个节假日ID") @RequestParam(value="hIds")String hIds){
		String result = this.attendenceTImeServiceImpl.deleteHolidyInfoByIds(hIds);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getStanderAttendenceTimeHolidyInfo.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="分页获取节假日信息",httpMethod="POST",response=String.class,responseContainer="String")
	public String getStanderAttendenceTimeHolidyInfo(int start,int limit,String atId,HttpServletRequest request){
		//TODO
		if(atId == null || atId.equals("") || atId.equals("undefined")){
			if(request.getSession().getAttribute("atId") != null){
				atId = request.getSession().getAttribute("atId").toString();
			}
		}
		String standerAttendenceTimeHolidyInfo = attendenceTImeServiceImpl.getStanderAttendenceTimeHolidyInfo(start,limit,atId);
		return standerAttendenceTimeHolidyInfo;
	}
	
	@ResponseBody
	@RequestMapping(value="/getAttendenceTimeRecords.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="分页获取打卡记录",httpMethod="POST",response=String.class,notes="打卡记录以json格式返回",responseContainer="String")
	public String getAttendenceTimeRecords(int start,int limit,String userId){
		//TODO
		String sttendenceTimeRecords= attendenceTimeRecordServiceImpl.getAttendenceTimeRecordsPage(start,limit,userId);
		return sttendenceTimeRecords;
	}
	
	@ResponseBody
	@RequestMapping(value="/addAttendenceTimeRecord.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="添加打卡记录",httpMethod="POST",response=String.class,notes="返回操作结果",responseContainer="String")
	public String addAttendenceTimeRecord(HttpServletRequest request,@RequestParam(value="userId")String userId,@RequestParam(value="attendenceTimeRecordType")String attendenceTimeRecordType){
		String result = attendenceTimeRecordServiceImpl.addAttendenceTimeRecord(request,userId,attendenceTimeRecordType);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/addDeficiencyLeaveRecord.do",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ApiOperation(value="添加缺失打卡记录",httpMethod="POST",response=String.class,notes="返回操作结果",responseContainer="String")
	public String addDeficiencyAttendenceTimeRecord(@ApiParam(required=true,name="userId",value="当前登录人ID") @RequestParam(value="userId")String userId){
		String result = attendenceTimeRecordServiceImpl.addDeficiencyAttendenceTimeRecord(userId);
		return result;
	}
	
}
