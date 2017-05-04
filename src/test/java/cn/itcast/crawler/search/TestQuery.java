package cn.itcast.crawler.search;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.hx.bat.sso.mapper.LotteryMapper;
import cn.hx.bat.sso.pojo.Lottery;
import cn.hx.bat.sso.service.HttpService;

public class TestQuery {

	private ApplicationContext applicationContext;

	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext("spring/*.xml");
	}

	/**
	 * 词条搜索
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTermQuery() throws Exception {
		HttpService http = applicationContext.getBean(HttpService.class);
		LotteryMapper mapper = applicationContext.getBean(LotteryMapper.class);
		String html = http.doGet("http://baidu.lecai.com/lottery/draw/3");
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
				//System.out.print(tds.get(j).html()+"  ");
				j++;
			}else{
				lo.setArea(los.get(i-2).getArea());
			}
			String title = tds.get(j).getElementsByTag("a").html();
			lo.setName(title);
			//System.out.print(title + " ");// 彩种
			lo.setToalPeriods(tds.get(j + 1).getElementsByTag("a").html());
			//System.out.print(tds.get(j + 1).getElementsByTag("a").html() + " ");// 总期数
			//lo.setOpenTime(tds.get(j + 2).html());
			//System.out.print(tds.get(j + 2).html() + " ");// 开奖日期
			Elements spans = tds.get(j + 3).select(".ballbg span");
			List<String> nums = new ArrayList<String>();//中奖号码集合
			if (title.equals("快乐8")) {
				nums.add(tds.get(j + 3).getElementsByTag("strong")
						.html());
				nums.add(tds.get(j + 3).getElementsByClass("fpball")
						.html());
				/*System.out.print(tds.get(j + 3).getElementsByTag("strong")
						.html()
						+ " ");
				System.out.print(tds.get(j + 3).getElementsByClass("fpball")
						.html()
						+ " ");*/
			}
			for (Element span : spans) {
				if (title.equals("快乐扑克3")) {
					nums.add(span.attr("style").split(";")[1]
							.split(":")[1]+span.getElementsByTag("em").html());
					/*System.out.println("------------"
							+ span.getElementsByTag("em").html());
					System.out.println(span.attr("style").split(";")[1]
							.split(":")[1]);*/

				} else {
					//System.out.print(span.html() + " ");// 开奖结果
					nums.add(span.html());
				}
			}
			lo.setLotteryNum(StringUtils.join(nums,","));
			lo.setDayPeriods(tds.get(j + 4).html());
			//System.out.println(tds.get(j + 4).html() + " ");// 期数/每天
			los.add(lo);
			mapper.insert(lo);
		}
		

	}
	
	@Test
	public void testQueryquangu() throws Exception {
		HttpService http = applicationContext.getBean(HttpService.class);
		String html = http.doGet("http://baidu.lecai.com/lottery/draw/1");
		Document document = Jsoup.parse(html);// 解析列表页面
		Elements lis = document.select(".kj_tab tbody tr");// 获取到商品列表的
		List<Lottery> los = new ArrayList<Lottery>();
		for (int i = 1; i < lis.size(); i++) {// tr
			Lottery lo = new Lottery();
			int j = 0;

			Element ele = lis.get(i);
			System.out.println(ele.html());
			Elements tds = ele.select("td");
			
			if (tds.size() == 11) {
				lo.setArea(tds.get(j).html());//地区
				//System.out.print(tds.get(j).html()+"  ");
				j++;
			}else{
				lo.setArea(los.get(i-2).getArea());
			}
			String title = tds.get(j).getElementsByTag("a").html();
			lo.setName(title);
			//System.out.print(title + " ");// 彩种
			lo.setToalPeriods(tds.get(j + 1).getElementsByTag("a").html());
			//System.out.print(tds.get(j + 1).getElementsByTag("a").html() + " ");// 总期数
			//lo.setOpenTime(tds.get(j + 2).html());
			//System.out.print(tds.get(j + 2).html() + " ");// 开奖日期
			Elements spans = tds.get(j + 3).select(".ballbg span");
			List<String> nums = new ArrayList<String>();//中奖号码集合
			if (title.equals("快乐8")) {
				nums.add(tds.get(j + 3).getElementsByTag("strong")
						.html());
				nums.add(tds.get(j + 3).getElementsByClass("fpball")
						.html());
				/*System.out.print(tds.get(j + 3).getElementsByTag("strong")
						.html()
						+ " ");
				System.out.print(tds.get(j + 3).getElementsByClass("fpball")
						.html()
						+ " ");*/
			}
			for (Element span : spans) {
				if (title.equals("快乐扑克3")) {
					nums.add(span.attr("style").split(";")[1]
							.split(":")[1]+span.getElementsByTag("em").html());
					/*System.out.println("------------"
							+ span.getElementsByTag("em").html());
					System.out.println(span.attr("style").split(";")[1]
							.split(":")[1]);*/

				} else {
					//System.out.print(span.html() + " ");// 开奖结果
					nums.add(span.html());
				}
			}
			lo.setLotteryNum(StringUtils.join(nums,","));
			lo.setDayPeriods(tds.get(j + 4).html());
			//System.out.println(tds.get(j + 4).html() + " ");// 期数/每天
			los.add(lo);
			//mapper.insert(lo);
		}
		

	}
	

	/**
	 * 词条搜索
	 * 
	 * @throws Exception
	 */
	@Test
	public void query() throws Exception {
		HttpService http = applicationContext.getBean(HttpService.class);
		String html = http.doGet("http://cai.163.com/zx/more.html?infoClass=news");
		Document document = Jsoup.parse(html);// 解析列表页面
		Elements lis = document.select(".zx_list_l ul li");// 获取到商品列表的
		
		for (Element el:lis) {// tr
			
			System.out.print(el.getElementsByTag("a").attr("href")+"  ");;
			System.out.print(el.getElementsByTag("a").html()+"  ");;
			System.out.print(el.getElementsByTag("em").get(0).html()+"  ");;
			System.out.print(el.getElementsByTag("em").get(1).html()+"  ");
			String content = http.doGet(el.getElementsByTag("a").attr("href"));
			Document contdocument = Jsoup.parse(content);// 解析列表页面
			Elements conele = contdocument.select("#endText");// 获取到商品列表的
			System.out.println(conele.html());
			break;
		}

	}

}
