package cn.hx.bat.sso.crawler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hx.bat.sso.controller.WebLoginController;
import cn.hx.bat.sso.pojo.Article;
import cn.hx.bat.sso.service.ArticleService;
import cn.hx.bat.sso.service.HttpService;


public class ArticleCrawler implements Runnable{
	 private static final Logger loger = LoggerFactory.getLogger(WebLoginController.class);
	
	private HttpService http;
	private ArticleService articleService;
	private String infoUrl;
	private String calUrl;
	
	


	public ArticleCrawler(HttpService http, ArticleService articleService,
			String infoUrl, String calUrl) {
		super();
		this.http = http;
		this.articleService = articleService;
		this.infoUrl = infoUrl;
		this.calUrl = calUrl;
	}

	@Override
	public void run() {
		crawlerArticle(infoUrl, (byte)1);
		crawlerArticle(calUrl, (byte)2);
		

		
	}
	
	private void crawlerArticle(String url ,Byte type){
		SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
		loger.info("开始抓取资讯");
		
		String html="";
		try {
			html = http.doGet(url);
		} catch (Exception e) {
			e.printStackTrace();
			loger.error(e.getMessage());
		}
		Document document = Jsoup.parse(html);// 解析列表页面
		Elements lis = document.select(".zx_list_l ul li");// 获取到商品列表的
		try {
			for (Element el:lis) {// tr
				Article ar = new Article();
				ar.setType(type);
				//System.out.print(el.getElementsByTag("a").html()+"  ");//标题
				ar.setTitle(el.getElementsByTag("a").html());
				//System.out.print(el.getElementsByTag("em").get(0).html()+"  ");//时间
				//System.out.print(el.getElementsByTag("em").get(1).html()+"  ");//来源
				ar.setPublishTime(format.parse(el.getElementsByTag("em").get(0).html()));
				Calendar c = Calendar.getInstance();
				c.setTime(ar.getPublishTime());
				c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
				ar.setPublishTime(c.getTime());
				String content = http.doGet(el.getElementsByTag("a").attr("href"));//详情链接
				Document contdocument = Jsoup.parse(content);// 解析详情
				Elements conele = contdocument.select("#endText");// 获取详情html
				//System.out.println(conele.html());
				for(Element ce : conele.select(".ep-source")){
					ce.remove();
				}
				Elements conPele = contdocument.select("#endText p");//
				for(Element p:conPele){
					String desc = ar.getDescription();
					if(desc==null || desc.length()<60){
						if(desc == null){
							desc = "";
						}
						ar.setDescription(desc+StringUtils.removePattern(p.html(), "<[^>]+>"));
					}
					desc = ar.getDescription();
					if(desc != null && desc.length()>=60){
						break;
					}
						
					
				}
				ar.setDescription(ar.getDescription().substring(0,60));
				ar.setInfo(StringUtils.removePattern(conele.html().replace("<!-- 此样式作用于  彩票文章页正文前seo链接列表  及 彩票文章页正文后seo链接列表, 若单独引用后者需要复制此样式表 -->", ""),
						"<a\\s+(?:(?!</a>).)*?>|</a>"));
				loger.info("成功抓取到一条资讯或预测:"+ar.getType()+ar.getTitle()+ar.getPublishTime());
				
				List<Article> ars = articleService.queryArticle(ar.getTitle(), ar.getType());
				if(ars == null || ars.size()==0){
					articleService.getArticleMapper().insertSelective(ar);
					loger.info("成功插入一条资讯或预测:"+ar.getType()+ar.getTitle()+ar.getPublishTime());
				}
			}
		} catch (Exception e) {
			loger.error(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.removePattern("<a href=http://caipiao.163.com/ >体育彩票</a>可谓是“大众情人”，","<a\\s+(?:(?!</a>).)*?>|</a>"));
	}

}
