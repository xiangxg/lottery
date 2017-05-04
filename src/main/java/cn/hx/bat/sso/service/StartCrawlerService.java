package cn.hx.bat.sso.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.hx.bat.sso.crawler.ArticleCrawler;
import cn.hx.bat.sso.crawler.LotterCrawler;
import cn.hx.bat.sso.pojo.Lottery;
import cn.hx.bat.sso.util.ThreadPool;

@Service
public class StartCrawlerService {
	
	@Value("${lotteryCrawlerStart}")
    public Integer lotteryCrawlerStart;
	@Value("${lotteryCrawlerTime}")
    public Integer lotteryCrawlerTime;
	@Value("${dataKeepAmount}")
    public Integer dataKeepAmount;
	
	@Value("${dataCleanPeriod}")
    public Integer dataCleanPeriod;
	@Autowired
	private HttpService http;
	@Autowired
	 private LotteryService lotteryService;
	@Autowired
	private ArticleService articleService;
	
	@Value("${lotteryInfoStart}")
    public Integer lotteryInfoStart;
	
	@Value("${lotteryInfoTime}")
    public Integer lotteryInfoTime;
	
	@Value("${lotteryInfoUrl}")
    public String lotteryInfoUrl;//资讯抓取地址
	
	@Value("${lotteryCalInfoUrl}")
    public String lotteryCalInfoUrl;//预测抓取地址
	
	public   static int lotteryIsStart=0;
	public   static int lotteryInfoIsStart=0;
	
	
	@PostConstruct
	public void start() {
		//启动彩票抓取
		/*if(lotteryCrawlerStart==1){
			ThreadPool.runInThread(new LotterCrawler(http,lotteryService), lotteryCrawlerTime);
		}*/
		startLotteryCrawler();
		lotteryIsStart =1;
		//启动资讯、预测抓取
		/*if(lotteryInfoStart==1){
			ThreadPool.runInThread(new ArticleCrawler(http, articleService, lotteryInfoUrl, lotteryCalInfoUrl), lotteryInfoTime);
		}*/
		startLotteryInfoCrawler();
		lotteryInfoIsStart=1;
		//彩票清理线程
		ThreadPool.runInThread(new ClearLoterryThread(lotteryService,dataKeepAmount), dataCleanPeriod);
		//资讯清理线程
		ThreadPool.runInThread(new ClearArticleThread(articleService,dataKeepAmount), dataCleanPeriod);

	}
	
	public synchronized void startLotteryCrawler(){
		if(lotteryIsStart==0){
			ThreadPool.getlotteryServiceInstance().scheduleAtFixedRate(new LotterCrawler(http,lotteryService), 1, lotteryCrawlerTime, TimeUnit.MINUTES);
			lotteryIsStart=1;
		}
		
	}
	
	public synchronized  void startLotteryInfoCrawler(){
		if(lotteryInfoIsStart==0){
			ThreadPool.getlotteryInfoServiceInstance().scheduleAtFixedRate(new ArticleCrawler(http, articleService, lotteryInfoUrl, lotteryCalInfoUrl), 1, lotteryInfoTime, TimeUnit.MINUTES);
			lotteryInfoIsStart=1;
		}
	}
	
	public synchronized void stopLotteryCrawler(){
		
		ThreadPool.shutdownLotteryPool();
		lotteryIsStart=0;
	}
	
	public synchronized void stopLotteryInfoCrawler(){
		ThreadPool.shutdownLotteryInfoPool();
		lotteryInfoIsStart=0;
	}
	
}
class ClearLoterryThread implements Runnable{
	
	
	public Integer dataKeepAmount;
	private LotteryService lotteryService;
	
	
	public ClearLoterryThread(
			LotteryService lotteryService,Integer dataKeepAmount) {
		this.dataKeepAmount = dataKeepAmount;
		this.lotteryService = lotteryService;
	}


	@Override
	public void run() {
		
		List<Lottery> list = lotteryService.getLotteryMapper().queryGroup(dataKeepAmount);
		for(Lottery lot : list){
			lotteryService.getLotteryMapper().deleteRedundantData(lot.getName(), lot.getArea(), dataKeepAmount);
		}
	}
	
}

class ClearArticleThread implements Runnable{
	
	
	public Integer dataKeepAmount;
	private ArticleService articleService;
	
	public ClearArticleThread(
			ArticleService articleService,Integer dataKeepAmount) {
		super();
		this.dataKeepAmount = dataKeepAmount;
		this.articleService = articleService;
	}



	@Override
	public void run() {
		articleService.getArticleMapper().deleteRedundantData(1, dataKeepAmount);
		articleService.getArticleMapper().deleteRedundantData(2, dataKeepAmount);
	}
	
}
