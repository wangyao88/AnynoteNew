package com.sxkl.loginHistory.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.sxkl.loginHistory.service.LoginHistoryService;

@Controller
public class LoginHistoryQuartzJob {
	
	@Autowired(required=true)
    @Qualifier("loginHistoryServiceImpl")
	private LoginHistoryService loginHistoryServiceImpl;
	
	public void setLoginOutTime(){
		if(loginHistoryServiceImpl == null){
			System.out.println("null");
			return;
		}
		System.out.println("not-null");
//		loginHistoryServiceImpl.setLoginOutTime();
//		System.out.println(new Date().getTime());
	}

}
