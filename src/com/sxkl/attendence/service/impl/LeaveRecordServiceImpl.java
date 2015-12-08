package com.sxkl.attendence.service.impl;

import java.util.Date;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sxkl.attendence.dao.LeaveRecordDao;
import com.sxkl.attendence.model.Holidy;
import com.sxkl.attendence.model.LeaveRecord;
import com.sxkl.attendence.service.LeaveRecordService;
import com.sxkl.common.utils.DateUtils;
import com.sxkl.common.utils.IDUtils;
import com.sxkl.common.utils.JsonConfigUtils;

@Service("leaveRecordServiceImpl")
public class LeaveRecordServiceImpl implements LeaveRecordService{
	
	@Autowired
	@Qualifier("leaveRecordDaoImpl")
	private LeaveRecordDao leaveRecordDaoImpl;

	@SuppressWarnings("static-access")
	public String addLeaveRecord(String leaveRecordData, String userId) {
		StringBuffer result = new StringBuffer();
		try {
			JSONObject data = JSONObject.fromObject(leaveRecordData,JsonConfigUtils.getDateJsonConfig("yyyy-MM-dd HH:mm:ss"));
			JSONObject json = new JSONObject();
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"}));
			LeaveRecord leaveRecord = (LeaveRecord) json.toBean(data, LeaveRecord.class);
			configurateLeaveRecordDate(leaveRecord,userId);
			leaveRecordDaoImpl.addLeaveRecord(leaveRecord);
			result.append("success");
		} catch (Exception e) {
			result.append(e.getLocalizedMessage());
		}
		return result.toString();
	}

	private void configurateLeaveRecordDate(LeaveRecord leaveRecord,String userId) throws Exception{
		leaveRecord.setLrId(IDUtils.getUUID());
		leaveRecord.setUserId(userId);
		Date startTemp = leaveRecord.getLrStartTime();
		String startStr = DateUtils.formatDate2Str(startTemp, "yyyy-MM-dd");
		startStr += " 00:00:00";
		Date start = DateUtils.formatStr2Date(startStr);
		leaveRecord.setLrStartTime(start);
		
		Date endTemp = leaveRecord.getLrEndTime();
		String endStr = DateUtils.formatDate2Str(endTemp, "yyyy-MM-dd");
		endStr += " 23:59:59";
		Date end = DateUtils.formatStr2Date(endStr);
		leaveRecord.setLrEndTime(end);
	}

}
