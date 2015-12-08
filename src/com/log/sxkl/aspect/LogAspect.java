package com.log.sxkl.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.log.sxkl.model.Log;
import com.log.sxkl.service.LogService;
import com.sxkl.common.utils.IDUtils;

@Aspect
@Component
public class LogAspect {

	@Autowired
	@Qualifier("logServiceImpl")
	private LogService logServiceImpl;// 日志记录Service
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static long COSTTIME = 0;
    
    private static final String OPERATION_SUCCESS = "操作成功";
    private static final String OPERATION_FAILURE = "操作失败";
    private static final int OUTTIME = 1000*60*2;

	/**
	 * 添加业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.sxkl.*.service.*.add*(..))")
	public void addServiceCall() {
	}

	/**
	 * 修改业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.sxkl.*.service.*.update*(..))")
	public void updateServiceCall() {
	}

	/**
	 * 删除业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.sxkl.*.service.*.delete*(..))")
	public void deleteServiceCall() {
	}

	/**
	 * 查询业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.sxkl.*.service.*.get*(..))")
	//execution(* com.sxkl.*.service.*.get*(..))
	public void findServiceCall() {
	}
	
	/**
	 * 任意业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.sxkl.*.service.*.*(..))")
	public void anyServiceCall() {
	}
	
	@Before(value = "anyServiceCall()", argNames = "rtv")
	public void addServiceCallBeforeCalls(JoinPoint joinPoint){
		String adminUserId = logServiceImpl.getUserId();
		String methodName = joinPoint.getSignature().getName();
		logger.info(adminUserId + "即将进行" + methodName + "操作'");
		COSTTIME = new Date().getTime();
	}

	/**
	 * 管理员添加操作日志(后置通知)
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@After(value = "addServiceCall()", argNames = "rtv")
	public void addServiceCallCalls(JoinPoint joinPoint)
			throws Throwable {
		long time = new Date().getTime() - COSTTIME;
		// 获取登录管理员id
		String adminUserId = logServiceImpl.getUserId();
		if (adminUserId == null) {// 没有管理员登录
			return;
		}
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容
		String opContent = adminOptionContent(joinPoint,joinPoint.getArgs(), methodName);
		// 创建日志对象
		Log log = new Log();
		log.setlId(IDUtils.getUUID());
		log.setlContent(opContent);
		log.setlDate(new Date());
		log.setlOperate("添加");
		log.setlUserName(adminUserId);
		log.setlClassName(getClassName(joinPoint));
		if(time > OUTTIME){
			log.setlCostTime(-1);
		}else{
			log.setlCostTime(Integer.valueOf(time + ""));
		}
		log.setlOperateName(methodName);
		log.setlResult(OPERATION_SUCCESS);
		try {
//			boolean flag1 = AopUtils.isAopProxy(logServiceImpl);//是否是代理对象；
//			boolean flag2 = AopUtils.isCglibProxy(logServiceImpl);// 是否是CGLIB方式的代理对象；
//			boolean flag3 = AopUtils.isJdkDynamicProxy(logServiceImpl);//是否是JDK动态代理方式的代理对象；
			logServiceImpl.addLog(log);
			logger.info(adminUserId+"进行了添加操作。执行时间为："+time+"ms。操作内容是:"+opContent);
			COSTTIME = 0;
		} catch (Exception e) {
			logger.error(adminUserId+"进行了添加操作.操作失败!:{}",e.getLocalizedMessage());
		}
		
	}


	private String getClassName(JoinPoint joinPoint) {
		String content = joinPoint.toLongString();
		content = content.replaceAll("execution", "");
		return content;
	}

	/**
	 * 管理员删除操作日志(后置通知)
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@After(value = "deleteServiceCall()", argNames = "rtv")
	public void deleteServiceCallCalls(JoinPoint joinPoint)
			throws Throwable {
		long time = new Date().getTime() - COSTTIME;
		// 获取登录管理员id
		String adminUserId = logServiceImpl.getUserId();
		if (adminUserId == null) {// 没有管理员登录
			return;
		}
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容
		String opContent = adminOptionContent(joinPoint,joinPoint.getArgs(), methodName);
		// 创建日志对象
		Log log = new Log();
		log.setlId(IDUtils.getUUID());
		log.setlContent(opContent);
		log.setlDate(new Date());
		log.setlOperate("删除");
		log.setlUserName(adminUserId);
		log.setlClassName(getClassName(joinPoint));
		if(time > OUTTIME){
			log.setlCostTime(-1);
		}else{
			log.setlCostTime(Integer.valueOf(time + ""));
		}
		log.setlOperateName(methodName);
		log.setlResult(OPERATION_SUCCESS);
		try {
			logServiceImpl.addLog(log);
			logger.info(adminUserId+"进行了删除操作。执行时间为："+time+"ms。操作内容是:"+opContent);
			COSTTIME = 0;
		} catch (Exception e) {
			logger.error(adminUserId+"进行了删除操作.操作失败!:{}",e.getLocalizedMessage());
		}
	}
	
	/**
	 * 管理员修改操作日志(后置通知)
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@After(value = "updateServiceCall()", argNames = "rtv")
	public void updateServiceCallCalls(JoinPoint joinPoint)
			throws Throwable {
		long time = new Date().getTime() - COSTTIME;
		// 获取登录管理员id
		String adminUserId = logServiceImpl.getUserId();
		if (adminUserId == null) {// 没有管理员登录
			return;
		}
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容
		String opContent = adminOptionContent(joinPoint,joinPoint.getArgs(), methodName);
		// 创建日志对象
		Log log = new Log();
		log.setlId(IDUtils.getUUID());
		log.setlContent(opContent);
		log.setlDate(new Date());
		log.setlOperate("修改");
		log.setlUserName(adminUserId);
		log.setlClassName(getClassName(joinPoint));
		if(time > OUTTIME){
			log.setlCostTime(-1);
		}else{
			log.setlCostTime(Integer.valueOf(time + ""));
		}
		log.setlOperateName(methodName);
		log.setlResult(OPERATION_SUCCESS);
		try {
			logServiceImpl.addLog(log);
			logger.info(adminUserId+"进行了修改操作。执行时间为："+time+"ms。操作内容是:"+opContent);
			COSTTIME = 0;
		} catch (Exception e) {
			logger.error(adminUserId+"进行了修改操作.操作失败!:{}",e.getLocalizedMessage());
		}
	}

	/**
	 * 管理员查询操作日志(后置通知)
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@After(value = "findServiceCall()", argNames = "rtv")
	public void getServiceCallCalls(JoinPoint joinPoint)throws Throwable {
		long time = new Date().getTime() - COSTTIME;
		// 获取登录管理员id
		String adminUserId = logServiceImpl.getUserId();
		if (adminUserId == null) {// 没有管理员登录
			return;
		}
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容 	
		String opContent = adminOptionContent(joinPoint,joinPoint.getArgs(), methodName);
		// 创建日志对象
		Log log = new Log();
		log.setlId(IDUtils.getUUID());
		log.setlContent(opContent);
		log.setlDate(new Date());
		log.setlOperate("查询");
		log.setlUserName(adminUserId);
		log.setlClassName(getClassName(joinPoint));
		if(time > OUTTIME){
			log.setlCostTime(-1);
		}else{
			log.setlCostTime(Integer.valueOf(time + ""));
		}
		log.setlOperateName(methodName);
		log.setlResult(OPERATION_SUCCESS);
		try {
			logServiceImpl.addLog(log);
			logger.info(adminUserId+"进行了查询操作。执行时间为："+time+"ms。操作内容是:"+opContent);
			COSTTIME = 0;
		} catch (Exception e) {
			logger.error(adminUserId+"进行了查询操作.操作失败!:{}",e.getLocalizedMessage());
		}
	}
	
	//@Around("anyServiceCall()")
	public void around(JoinPoint joinPoint){
		long start = System.currentTimeMillis();
		try {
			((ProceedingJoinPoint) joinPoint).proceed();
			long end = System.currentTimeMillis();
			logger.info("around " + joinPoint + "\t执行时间 : " + (end - start) + " ms!");
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			logger.info("around " + joinPoint + "\t执行时间 : " + (end - start) + " ms with exception : " + e.getMessage());
		}
	}
	
	//配置抛出异常后通知,使用在方法anyServiceCall()上注册的切入点
	@AfterThrowing(pointcut="anyServiceCall()", throwing="e")
	public void afterThrow(JoinPoint joinPoint, Exception e){
		long time = new Date().getTime() - COSTTIME;
		// 获取登录管理员id
		String adminUserId = logServiceImpl.getUserId();
		if (adminUserId == null) {// 没有管理员登录
			return;
		}
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容 	
		String opContent = adminOptionContent(joinPoint,joinPoint.getArgs(), methodName);
		Log log = new Log();
		log.setlId(IDUtils.getUUID());
		log.setlContent(opContent);
		log.setlDate(new Date());
		log.setlOperate("查询");
		log.setlUserName(adminUserId);
		log.setlClassName(getClassName(joinPoint));
		if(time > OUTTIME){
			log.setlCostTime(-1);
		}else{
			log.setlCostTime(Integer.valueOf(time + ""));
		}
		log.setlOperateName(methodName);
		log.setlResult(OPERATION_FAILURE);
		logServiceImpl.addLog(log);
		COSTTIME = 0;
		logger.info(adminUserId+"进行了"+methodName+"操作。执行时间为："+time+"ms。操作内容是:"+opContent);
		logger.info("afterThrow " + joinPoint + "\t" + e.getMessage());
	}
	

	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
	 * @param joinPoint 
	 */
	public String adminOptionContent( Object[] args, String mName)throws Exception {
		if (args == null) {
			return null;
		}
		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {
			if(info == null || info.equals("null")){
				continue;
			}
			// 获取对象类型
			logger.info("---------" + info);
			className = info.getClass().getName(); 
//			className = className.substring(className.lastIndexOf(".") + 1);
			rs.append("[参数" + index + "，类型：" + className + "，值：");
			// 获取对象的所有方法
			Method[] methods = info.getClass().getDeclaredMethods();
			// 遍历方法，判断get方法
			for (Method method : methods) {
				String methodName = method.getName();
				// 判断是不是get方法
				if (methodName.indexOf("get") == -1) {// 不是get方法
					continue;// 不处理
				}
				Object rsValue = null;
				try {
					// 调用get方法，获取返回值
					rsValue = method.invoke(info);
					if (rsValue == null) {// 没有返回值
						continue;
					}
				} catch (Exception e) {
					continue;
				}
				// 将值加入内容中
				rs.append("(" + methodName + " : " + rsValue + ")");
			}
			rs.append("]");
			index++;
		}
		return rs.toString();
	}
	
	public String adminOptionContent(JoinPoint joinPoint, Object[] args, String mName){
//		{方法:addLogin,参数:[参数1:12,参数2:fsd]}
		String content = joinPoint.toLongString();
		content = content.replaceAll("execution", "");
		StringBuffer result = new StringBuffer();
		result.append("{方法:")
		      .append(content)
		      .append(",参数:");
		if(args == null || args.length == 0){
			result.append("无")
			      .append("}");
			return result.toString();
		}
		for(int i = 0; i < args.length; i++){
			if(i == 0){
				result.append("[参数" + (i+1) + ":" + args[i]);
			}else{
				result.append(",参数" + (i+1) + ":"  + args[i]);
			}
		}
		result.append("]}");
		return result.toString();
	}

}
