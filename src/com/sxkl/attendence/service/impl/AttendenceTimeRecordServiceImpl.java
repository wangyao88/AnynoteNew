package com.sxkl.attendence.service.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sxkl.attendence.dao.AttendenceTimeDao;
import com.sxkl.attendence.dao.AttendenceTimeRecordDao;
import com.sxkl.attendence.dao.LeaveRecordDao;
import com.sxkl.attendence.model.AttendenceTime;
import com.sxkl.attendence.model.AttendenceTimeRecord;
import com.sxkl.attendence.model.Holidy;
import com.sxkl.attendence.model.LeaveRecord;
import com.sxkl.attendence.service.AttendenceTimeRecordService;
import com.sxkl.common.constant.GlobalConstants;
import com.sxkl.common.utils.DateUtils;
import com.sxkl.common.utils.IDUtils;
import com.sxkl.common.utils.JsonConfigUtils;

@Service("attendenceTimeRecordServiceImpl")
public class AttendenceTimeRecordServiceImpl implements AttendenceTimeRecordService{
	
	@Autowired
	@Qualifier("attendenceTimeRecordDaoImpl")
	private AttendenceTimeRecordDao attendenceTimeRecordDaoImpl;
	
	@Autowired
	@Qualifier("attendenceTimeDaoImpl")
	private AttendenceTimeDao attendenceTimeDaoImpl;
	
	@Autowired
	@Qualifier("leaveRecordDaoImpl")
	private LeaveRecordDao leaveRecordDaoImpl;
	
	public String getAttendenceTimeRecordsPage(int start, int limit,String userId) {
		JSONObject json = new JSONObject();
		try {
			List<AttendenceTimeRecord> attendenceTimeRecords = attendenceTimeRecordDaoImpl.getAttendenceTimeRecordsPage(start,limit,userId);
			int attendenceTimeRecordNum = attendenceTimeRecordDaoImpl.getAttendenceTimeRecordNum(userId);
			JSONArray data = JSONArray.fromObject(attendenceTimeRecords,JsonConfigUtils.getDateJsonConfig("yyyy-MM-dd HH:mm:ss"));
			json.put("datas", data);
			json.put("results", attendenceTimeRecordNum);
			json.put("start", start);
			json.put("limit", limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@SuppressWarnings("unchecked")
	public String addAttendenceTimeRecord(HttpServletRequest request,String userId,String attendenceTimeRecordType) {
		StringBuffer result = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		int num = calendar.get(Calendar.DAY_OF_WEEK);
		//TODO
		if(num == 1 || num == 7){
			result.append("week");
			return result.toString();
		}
		try {
			//获取标准打卡时间
			AttendenceTime attendenceTime = attendenceTimeDaoImpl.findAttendenceTime();
			Class clazz = Class.forName("com.sxkl.attendence.model.AttendenceTime");
			String fieldName = GlobalConstants.ATTENDENCETIMERECORDTYPE.get(attendenceTimeRecordType);
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method rM = pd.getReadMethod(); 
			Date now = new Date();
			StringBuffer date = new StringBuffer();
			StringBuffer standerTime = new StringBuffer();
			date.append(calendar.get(Calendar.YEAR))
	            .append("-")
	            .append(calendar.get(Calendar.MONTH)+1)
	            .append("-")
	            .append(calendar.get(Calendar.DAY_OF_MONTH));
			standerTime.append(date.toString())
			           .append(" ")
	                   .append(rM.invoke(attendenceTime).toString());
			//当前标准打卡时间
			Date standarDate = DateUtils.formatStr2Date(standerTime.toString(),"yyyy-MM-dd HH:mm:ss");
			
			//获取今天打卡记录
			AttendenceTimeRecord queryBean = new AttendenceTimeRecord();
			queryBean.setUserId(userId);
			queryBean.setAtrDate(date.toString());
			AttendenceTimeRecord attendenceTimeRecord = attendenceTimeRecordDaoImpl.findAttendenceTimeRecordByBean(queryBean);
			
			Class clazzz = Class.forName("com.sxkl.attendence.model.AttendenceTimeRecord");
			PropertyDescriptor pdc = new PropertyDescriptor(fieldName, clazzz);
			Method rMc = pdc.getReadMethod(); 
			if(attendenceTimeRecord != null && rMc.invoke(attendenceTimeRecord) != null){
				result.append("repeat");
				return result.toString();
			}
			
			if(attendenceTimeRecord == null){
				attendenceTimeRecord = new AttendenceTimeRecord();
				attendenceTimeRecord.setAtrId(IDUtils.getUUID());
				 //设置打卡记录属性		
				congutareBean(standarDate,userId, fieldName, now, date, attendenceTimeRecord,result);
				//持久化打卡记录
				attendenceTimeRecordDaoImpl.addAttendenceTimeRecord(attendenceTimeRecord);
			}else{
				 //设置打卡记录属性		
				congutareBean(standarDate,userId, fieldName, now, date, attendenceTimeRecord,result);
				//更新打卡记录
				attendenceTimeRecordDaoImpl.updateAttendenceTimeRecord(attendenceTimeRecord);
			}
			
		} catch (Exception e) {
			result.append(e.getLocalizedMessage());
		}
		return result.toString();
	}

	@SuppressWarnings("unchecked")
	private void congutareBean(Date standarDate,String userId, String fieldName, Date now,StringBuffer date, AttendenceTimeRecord attendenceTimeRecord,StringBuffer result)
			throws ClassNotFoundException, IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		attendenceTimeRecord.setAtrDate(date.toString());
		attendenceTimeRecord.setUserId(userId);
		String hitTime = DateUtils.formatDate2Str(now, "HH:mm:ss");
		Class clazzRecord = Class.forName("com.sxkl.attendence.model.AttendenceTimeRecord");
		PropertyDescriptor pdRecord = new PropertyDescriptor(fieldName, clazzRecord);
		Method wM = pdRecord.getWriteMethod();
		wM.invoke(attendenceTimeRecord, hitTime);
		
		//判读迟到 早退 正常打卡
		if(fieldName.equals(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_AM_ON)){
			if(now.before(standarDate)){
				result.append("success");
				attendenceTimeRecord.setAtrRemark("正常");
				attendenceTimeRecord.setAtrAmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_3);
			}else if(now.after(standarDate)){
				result.append("late");
				attendenceTimeRecord.setAtrRemark("异常");
				attendenceTimeRecord.setAtrAmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_1);
			}
		}else if(fieldName.equals(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_PM_ON)){
			if(now.before(standarDate)){
				result.append("success");
				attendenceTimeRecord.setAtrRemark("正常");
				attendenceTimeRecord.setAtrPmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_3);
			}else if(now.after(standarDate)){
				result.append("late");
				attendenceTimeRecord.setAtrRemark("异常");
				attendenceTimeRecord.setAtrPmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_1);
			}
		}else if(fieldName.equals(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_AM_OFF)){
			if(now.before(standarDate)){
				result.append("early");
				attendenceTimeRecord.setAtrRemark("异常");
				attendenceTimeRecord.setAtrAmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_2);
			}else if(now.after(standarDate)){
				result.append("success");
				attendenceTimeRecord.setAtrRemark("正常");
				attendenceTimeRecord.setAtrAmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_3);
			}
		}else if(fieldName.equals(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_PM_OFF)){
			if(now.before(standarDate)){
				result.append("early");
				attendenceTimeRecord.setAtrRemark("异常");
				attendenceTimeRecord.setAtrPmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_2);
			}else if(now.after(standarDate)){
				result.append("success");
				attendenceTimeRecord.setAtrRemark("正常");
				attendenceTimeRecord.setAtrPmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_3);
			}
		}
	}

	public String addDeficiencyAttendenceTimeRecord(String userId) {
		//轮询GlobalConstants.LEAVE_RECORD_DEFICIENCY_RANGE时间段的打卡记录,未打卡按缺勤算
		String date = DateUtils.getStrDateBeforeDay(GlobalConstants.LEAVE_RECORD_DEFICIENCY_RANGE);
		String hql = "from AttendenceTimeRecord a where a.userId=? and a.atrDate > ?";
		List<AttendenceTimeRecord> attendenceReccords = this.attendenceTimeRecordDaoImpl.findAttendenceTimeRecordsByBean(hql,userId,date);
		int size = attendenceReccords.size();
		//请假记录
		List<LeaveRecord> leaveRecords = leaveRecordDaoImpl.findAllLeaveRecord(userId);
		int leaveRecordSize = leaveRecords.size();
		//节假日记录
		List<Holidy> holidys = attendenceTimeDaoImpl.findAllHolidys();
		int holidySize = holidys.size();
		List<String> dateList = DateUtils.getDayRange();
		for(String str : dateList){
			AttendenceTimeRecord attendenceTimeRecord = null;
			boolean flag = false;
			for(int i = 0; i < size; i++){
				if(attendenceReccords.get(i).getAtrDate().equals(str)){
					attendenceTimeRecord = attendenceReccords.get(i);
					flag = true;
					break;
				}
			}
			//包含,检查四次有无漏缺
			if(flag){
				if(attendenceTimeRecord.getAmOnTime() == null){
					attendenceTimeRecord.setAmOnTime(GlobalConstants.ATTENDENCE_TIME_RECORD_VALUE_1);
					attendenceTimeRecord.setAtrAmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_7);
				}
				if(attendenceTimeRecord.getAmOffTime() == null){
					attendenceTimeRecord.setAmOffTime(GlobalConstants.ATTENDENCE_TIME_RECORD_VALUE_1);
					attendenceTimeRecord.setAtrAmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_7);
				}
				if(attendenceTimeRecord.getPmOnTime() == null){
					attendenceTimeRecord.setPmOnTime(GlobalConstants.ATTENDENCE_TIME_RECORD_VALUE_1);
					attendenceTimeRecord.setAtrPmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_7);
				}
				if(attendenceTimeRecord.getPmOffTime() == null){
					attendenceTimeRecord.setPmOffTime(GlobalConstants.ATTENDENCE_TIME_RECORD_VALUE_1);
					attendenceTimeRecord.setAtrPmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_7);
				}
				this.attendenceTimeRecordDaoImpl.updateAttendenceTimeRecord(attendenceTimeRecord);
			}
			//不包含
			if(!flag){
				//周末
				if(DateUtils.isWeekend(str)){
					attendenceTimeRecord = new AttendenceTimeRecord();
					attendenceTimeRecord.setAtrId(IDUtils.getUUID());
					attendenceTimeRecord.setUserId(userId);
					attendenceTimeRecord.setAtrDate(str);
					attendenceTimeRecord.setAtrRemark("正常");
					attendenceTimeRecord.setAtrAmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_4);
					attendenceTimeRecord.setAtrAmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_4);
					attendenceTimeRecord.setAtrPmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_4);
					attendenceTimeRecord.setAtrPmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_4);
					try {
						this.attendenceTimeRecordDaoImpl.addAttendenceTimeRecord(attendenceTimeRecord);
					} catch (Exception e) {
						System.out.println(e.getLocalizedMessage());
					}
				}else{
					//TODO
					//非周末
					//是否请假
					for(int j = 0; j < leaveRecordSize; j++){
						LeaveRecord leaveRecord = leaveRecords.get(j);
					    boolean isLeave = false;
					    isLeave = DateUtils.isBetween(DateUtils.formatStr2Date(str, "yyyy-MM-dd"),leaveRecord.getLrStartTime(),leaveRecord.getLrEndTime());
					    if(isLeave){
					    	attendenceTimeRecord = new AttendenceTimeRecord();
							attendenceTimeRecord.setAtrId(IDUtils.getUUID());
							attendenceTimeRecord.setUserId(userId);
							attendenceTimeRecord.setAtrDate(str);
							attendenceTimeRecord.setAtrRemark("正常");
							attendenceTimeRecord.setAtrAmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_6);
							attendenceTimeRecord.setAtrAmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_6);
							attendenceTimeRecord.setAtrPmOnTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_6);
							attendenceTimeRecord.setAtrPmOffTimeType(GlobalConstants.ATTENDENCE_TIME_RECORD_TYPE_6);
							try {
								this.attendenceTimeRecordDaoImpl.addAttendenceTimeRecord(attendenceTimeRecord);
							} catch (Exception e) {
								System.out.println(e.getLocalizedMessage());
							}
							break;
					    }
					}
					//是否节假日
					//是否缺勤
					
				}
			}
		}
		
		return null;
	}
	
}
