package cn.hx.bat.sso.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	private final static ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
	private   static ScheduledExecutorService lotteryService ;
	private  static ScheduledExecutorService lotteryInfoService ;
	/**
	 * 加入到线程池中执行
	 * 
	 * @param runnable
	 */
	public static void runInThread(Runnable runnable,Integer delay) {
		 // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
	     service.scheduleAtFixedRate(runnable, 1, delay, TimeUnit.MINUTES); 
	}
	
	public static synchronized ScheduledExecutorService getlotteryServiceInstance(){
		if(lotteryService==null){
			lotteryService = Executors.newScheduledThreadPool(5);
		}
		return lotteryService;
	}
	
	public static synchronized ScheduledExecutorService getlotteryInfoServiceInstance(){
		if(lotteryInfoService==null){
			lotteryInfoService= Executors.newScheduledThreadPool(5);
		}
		return lotteryInfoService;
	}
	
	public static synchronized void shutdownLotteryPool(){
		lotteryService.shutdown();
		lotteryService = null;
		
	}
	
	public static synchronized void shutdownLotteryInfoPool(){
		lotteryInfoService.shutdown();
		lotteryInfoService = null;
		
	}

}
