package cn.hx.bat.sso.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hx.bat.sso.controller.WebLoginController;
import cn.hx.bat.sso.pojo.Lottery;
import cn.hx.bat.sso.service.HttpService;
import cn.hx.bat.sso.service.LotteryService;


public class LotterCrawler implements Runnable{
	 private static final Logger loger = LoggerFactory.getLogger(WebLoginController.class);
	
	private HttpService http;
	private LotteryService lotteryService;
	

	public LotterCrawler(HttpService http, LotteryService lotteryService) {
		this.http = http;
		this.lotteryService = lotteryService;
	}
	
	@Override
	public void run() {
		gaopinCrawler();
		quanguoCrawler();
	}
	/**
	 * 高频彩种
	 */
	private void gaopinCrawler(){
		SimpleDateFormat fromat=new SimpleDateFormat("yyyy-MM-dd");
		loger.info("开始抓取彩票");
		String html="";
		try {
			html = http.doGet("http://baidu.lecai.com/lottery/draw/3");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		Document document = Jsoup.parse(html);// 解析列表页面
		Elements lis = document.select(".kj_tab tbody tr");// 获取到商品列表的
		List<Lottery> los = new ArrayList<Lottery>();
		for (int i = 1; i < lis.size(); i++) {// tr
			Lottery lo = new Lottery();
			int j = 0;

			Element ele = lis.get(i);
			//System.out.println(ele.html());
			Elements tds = ele.select("td");
			
			if (tds.size() == 11) {
				lo.setArea(tds.get(j).html());//地区
				j++;
			}else{
				lo.setArea(los.get(i-2).getArea());
			}
			String title = tds.get(j).getElementsByTag("a").text();//彩种
			lo.setName(title.trim());
			
			lo.setToalPeriods(tds.get(j + 1).getElementsByTag("a").html().replace("期", ""));//总期数
			try {
				lo.setOpenTime(fromat.parse(tds.get(j + 2).html()));// 开奖日期
			} catch (ParseException e) {
				loger.error(e.getLocalizedMessage());
			}
			Elements spans = tds.get(j + 3).select(".ballbg span");
			List<String> nums = new ArrayList<String>();//中奖号码集合
			if (title.equals("快乐8")) {
				nums.add(tds.get(j + 3).getElementsByTag("strong")
						.html());
				nums.add(tds.get(j + 3).getElementsByClass("fpball")
						.html());
				
			}
			for (Element span : spans) {
				if (title.equals("快乐扑克3")) {
					//去掉颜色
					//nums.add(span.attr("style").split(";")[1]
						//	.split(":")[1]+span.text());
					nums.add(span.text());
				} else {
					nums.add(span.html());//开奖结果
				}
			}
			lo.setLotteryNum(StringUtils.join(nums,","));
			lo.setDayPeriods(tds.get(j + 4).html().replace("期", ""));// 期数/每天
			los.add(lo);
			
			insertDB(lo);
			
			
		}
		
	}
	/**
	 * 全国彩种
	 */
	private void quanguoCrawler(){
		SimpleDateFormat fromat=new SimpleDateFormat("yyyy-MM-dd");
		loger.info("开始抓取彩票");
		String html="";
		try {
			html = http.doGet("http://baidu.lecai.com/lottery/draw/1");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		Document document = Jsoup.parse(html);// 解析列表页面
		Elements lis = document.select(".kj_tab tbody tr");// 获取到彩票列表的
		List<Lottery> los = new ArrayList<Lottery>();
		for (int i = 2; i < lis.size(); i++) {// tr
			Lottery lo = new Lottery();

			Element ele = lis.get(i);
			//System.out.println(ele.html());
			Elements tds = ele.select("td");
			
			lo.setArea("全国");
			String title = tds.get(0).getElementsByTag("a").text();
			if(StringUtils.isEmpty(title)){
				continue;
			}
			lo.setName(StringUtils.trim(title));// 彩种
			lo.setToalPeriods(tds.get( 1).getElementsByTag("a").html().replace("期", ""));// 总期数
			try {
				lo.setOpenTime(fromat.parse(tds.get(2).html()));
			} catch (ParseException e) {
				loger.error(e.getMessage());
				e.printStackTrace();
			}
			Elements spans = tds.get(3).select(".ballbg span");
			List<String> nums = new ArrayList<String>();//中奖号码集合
			for (Element span : spans) {
					nums.add(span.html());// 开奖结果
			}
			lo.setLotteryNum(StringUtils.join(nums,","));
			lo.setPrizeCache(tds.get(4).html());// 期数/每天
			los.add(lo);
			insertDB(lo);
		}
	}

	
	
	private void insertDB(Lottery lo){
		List<Lottery> ll =  lotteryService.queryLottery(lo);
		if(ll==null||ll.size()==0){
			lotteryService.getLotteryMapper().insert(lo);
			loger.info("成功插入彩票信息"+lo.toString());
		}else{
			loger.info("彩票信息已存在"+lo.toString());

		}
	}
	
	

}
