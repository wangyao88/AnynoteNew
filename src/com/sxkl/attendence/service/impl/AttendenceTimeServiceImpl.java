package com.sxkl.attendence.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sxkl.attendence.dao.AttendenceTimeDao;
import com.sxkl.attendence.model.AttendenceTime;
import com.sxkl.attendence.model.Holidy;
import com.sxkl.attendence.service.AttendenceTimeService;
import com.sxkl.common.dao.BaseDAO;
import com.sxkl.common.utils.DateJsonValueProcessImpl;
import com.sxkl.common.utils.DateUtils;
import com.sxkl.common.utils.IDUtils;
import com.sxkl.common.utils.JsonConfigUtils;

/**
 * 考试时间服务实现类
 * @author wangyao
 * @date 2015-10-30
 */
@Service("attendenceTimeServiceImpl")
public class AttendenceTimeServiceImpl implements AttendenceTimeService{
	
	@Autowired
	@Qualifier("baseDaoImpl")
	private BaseDAO<AttendenceTime> baseDao;
	
	@Autowired
	@Qualifier("attendenceTimeDaoImpl")
	private AttendenceTimeDao attendenceTimeDaoImpl;

	public JSONObject getStanderAttendenceTimeInfo() {
		String hql = "from AttendenceTime";
		List<AttendenceTime> list = this.baseDao.find(hql);
		JSONObject json = new JSONObject();
		if(list != null && !list.isEmpty()){
			AttendenceTime attendenceTime = list.get(0);
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[]{"holidys"});
			config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessImpl("yyyy-MM-dd HH:mm:ss"));
			json = JSONObject.fromObject(attendenceTime,config);
		}
		return json;
	}

	public String getStanderAttendenceTimeHolidyInfo(int start, int limit,String atId) {
		String hql = "from Holidy h where h.attendenceTime.atId = ?   order by h.hStart asc";
		Object[] params = new Object[]{atId};
		List<Holidy> holidys = attendenceTimeDaoImpl.find(hql,params,start,limit);
		String hqlNum = "select new Holidy(hId) from Holidy";
		List<Holidy> holidySize = attendenceTimeDaoImpl.find(hqlNum);
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"attendenceTime"});
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessImpl("yyyy-MM-dd HH:mm:ss"));
		JSONObject json = new JSONObject();
		JSONArray data = JSONArray.fromObject(holidys,config);
		json.put("datas", data);
		json.put("results", holidySize.size());
		json.put("start", start);
		json.put("limit", limit);
		return json.toString();
	}

	@SuppressWarnings("static-access")
	public String saveHolidyInfo(String holidy, String atId){
		StringBuffer result = new StringBuffer();
		try {
			JSONObject data = JSONObject.fromObject(holidy,JsonConfigUtils.getDateJsonConfig("yyyy-MM-dd HH:mm:ss"));
			JSONObject json = new JSONObject();
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss"}));
			Holidy hody = (Holidy) json.toBean(data, Holidy.class);
			configurateHolidyDate(hody);
			AttendenceTime attendenceTime = this.attendenceTimeDaoImpl.load(AttendenceTime.class,atId);
			hody.setAttendenceTime(attendenceTime);
			if(hody.gethId() != null && !hody.gethId().equals("")){
				this.attendenceTimeDaoImpl.updateHolidy(hody);
			}else{
				hody.sethId(IDUtils.getUUID().substring(0,32));
				this.attendenceTimeDaoImpl.saveHolidy(hody);
			}
			result.append("yes");
		} catch (Exception e) {
            e.printStackTrace();
			result.append(e.getLocalizedMessage());
		}
		return result.toString();
	}

	private void configurateHolidyDate(Holidy hody) {
		Date startTemp = hody.gethStart();
		String startStr = DateUtils.formatDate2Str(startTemp, "yyyy-MM-dd");
		startStr += " 00:00:00";
		Date start = DateUtils.formatStr2Date(startStr);
		hody.sethStart(start);
		
		Date endTemp = hody.gethEnd();
		String endStr = DateUtils.formatDate2Str(endTemp, "yyyy-MM-dd");
		endStr += " 23:59:59";
		Date end = DateUtils.formatStr2Date(endStr);
		hody.sethEnd(end);
	}

	public String getStanderAttendenceTimeHolidyInfoByhId(String hId) {
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"attendenceTime"});
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessImpl("yyyy-MM-dd HH:mm:ss"));
		Holidy holidy =  this.attendenceTimeDaoImpl.findHolidyById(hId);
		JSONObject data = JSONObject.fromObject(holidy,config);
		return data.toString();
	}

	public String deleteHolidyInfoByIds(String hIds) {
		try {
			int rows = this.attendenceTimeDaoImpl.deleteHolidyInfoByIds(hIds);
			return "成功删除"+rows+"条记录";
		} catch (Exception e) {
			return "删除失败["+e.getLocalizedMessage()+"]";
		}
	}

}
