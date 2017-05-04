package cn.hx.bat.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hx.bat.sso.entity.Pager;
import cn.hx.bat.sso.exception.CustomException;
import cn.hx.bat.sso.pojo.SetData;
import cn.hx.bat.sso.pojo.User;
import cn.hx.bat.sso.service.ArticleService;
import cn.hx.bat.sso.service.LotteryService;
import cn.hx.bat.sso.service.PropertieService;
import cn.hx.bat.sso.service.SetDataService;
import cn.hx.bat.sso.service.StartCrawlerService;
import cn.hx.bat.sso.service.UserService;
import cn.hx.common.bean.JsonResult;

@RequestMapping("web")
@Controller
public class WebLoginController {
    
   
    private static final Logger loger = LoggerFactory.getLogger(WebLoginController.class);
    @Autowired
    private SetDataService setDataService;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private StartCrawlerService startCrawlerService;
    @Autowired
    private UserService userService;
    
    
    @Autowired
    private PropertieService propertieService;
    
    public static final String COOKIE_NAME = "BAT_TOKEN";
    //
    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public String toLogin(String redirectUrl,HttpServletRequest request) {
        return "login";
    }
    
    private boolean volidRequest(Long times,String code){
    	if(times==null || StringUtils.isEmpty(code)){
    		return false;
    	}
    	long millis = System.currentTimeMillis()-times;
    	if(millis/(1000*60*60)>1){
    		return false;
    	}
    	if(!DigestUtils.md5Hex(times+"CPShc1&^2017").equals(code)){
    		return false;
    	}
    	return true;
    }
    
    public static void main(String[] args) {
    	long time = System.currentTimeMillis();
    	System.out.println(time);
    	System.out.println(DigestUtils.md5Hex(time+"CPShc1&^2017"));
	}
    
    @RequestMapping(value = "lotterySwitch")
    @ResponseBody
    public ResponseEntity<JsonResult> lotterySwitch() {
    	if(startCrawlerService.lotteryIsStart==0){
    		startCrawlerService.startLotteryCrawler();
    	}else{
    		startCrawlerService.stopLotteryCrawler();
    	}
    	try {
    		Thread.sleep(1000*10);
		} catch (Exception e) {
			
		}
    	
    	JsonResult result = new JsonResult();
    	result.setData(startCrawlerService.lotteryIsStart);
        return ResponseEntity.ok(result);
    }
    
    @RequestMapping(value = "lotteryInfoSwitch")
    @ResponseBody
    public ResponseEntity<JsonResult> lotteryInfoSwitch() {
    	if(startCrawlerService.lotteryInfoIsStart==0){
    		startCrawlerService.startLotteryInfoCrawler();
    	}else{
    		startCrawlerService.stopLotteryInfoCrawler();
    	}
    	try {
    		Thread.sleep(1000*10);
		} catch (Exception e) {
			
		}
    	JsonResult result = new JsonResult();
    	result.setData(startCrawlerService.lotteryInfoIsStart);
        return ResponseEntity.ok(result);
    }
    
    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public String toAdd() {
       
        return "setAdd";
    }
    
    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    public String toEdit(Integer id,HttpServletRequest request) {
    	request.setAttribute("setData", setDataService.getSetDataMapper().selectByPrimaryKey(id));
        return "setEdit";
    }
    @RequestMapping(value = "lotterys")
    @ResponseBody
    public ResponseEntity<JsonResult> lotterys(Long times,String code,String type,String area,@RequestParam(value="start", defaultValue="1")Integer start,@RequestParam(value="count", defaultValue="10")Integer count) throws CustomException {
    	if(!volidRequest(times, code)){
    		throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "请求不合法！");
    	}
    	Pager page = new Pager();
    	page.setStart(start);
    	page.setCount(count);
    	if(type.startsWith("东方6")){
    		type=type.replace(" ", "+");
    	}
    	lotteryService.findPage(page,type,area);
    	JsonResult result = new JsonResult();
    	result.setData(page);
        return ResponseEntity.ok(result);
    }
    /*@RequestMapping(value = "setDatas")
    public ResponseEntity<JsonResult> setDatas(Long times,String code,@RequestParam(value="start", defaultValue="1")Integer start,@RequestParam(value="count", defaultValue="10")Integer count) throws Exception {
    	if(!volidRequest(times, code)){
    		throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "请求不合法！");
    	}
    	Pager pager = setDataService.queryPage(start,count);
    	JsonResult result= new JsonResult();
    	result.setData(pager);
        return ResponseEntity.ok(result);
    }*/
    
    @RequestMapping(value = "setDatas")
    public ResponseEntity<JsonResult> setDatas(Long times,String code,String name) throws Exception {
    	if(!volidRequest(times, code)){
    		throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "请求不合法！");
    	}
    	if(StringUtils.isEmpty(name)){
    		throw new CustomException(HttpStatus.BAD_REQUEST.value(), "参数错误！");
    	}
    	SetData sd = setDataService.queryByName(name);
    	JsonResult result= new JsonResult();
    	result.setData(sd);
        return ResponseEntity.ok(result);
    }
    @RequestMapping(value = "lotteryInfos")
    public ResponseEntity<JsonResult> lotteryInfos(Long times,String code,@RequestParam(value="start", defaultValue="1")Integer start,
    		@RequestParam(value="count", defaultValue="10")Integer count,@RequestParam(value="type", defaultValue="1")Byte type) throws Exception {
    	if(!volidRequest(times, code)){
    		throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "请求不合法！");
    	}
    	Pager page = new Pager();
    	page.setStart(start);
    	page.setCount(count);
    	
    	articleService.queryPage(page,type);
    	JsonResult result= new JsonResult();
    	result.setData(page);
        return ResponseEntity.ok(result);
    }
    
    @RequestMapping(value = "lotteryInfo")
    public ResponseEntity<JsonResult> lotteryInfo(Long times,String code,Integer id) throws CustomException {
    	if(!volidRequest(times, code)){
    		throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "请求不合法！");
    	}
    	JsonResult result= new JsonResult();
    	result.setData(articleService.getArticleMapper().selectByPrimaryKey(id));
        return ResponseEntity.ok(result);
    }
   
  
    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
       session.invalidate();
        return "login";
    }
    
    @RequestMapping(value = "setIndex")
    public String set(HttpSession session) {
    	String user = (String)session.getAttribute("user");
    	if(StringUtils.isEmpty(user)){
    		return "login";
    	}
        return "setIndex";
    }
    
    @RequestMapping(value = "findSetData")
    public ResponseEntity<JsonResult> findSetData(String page,String rows) {
    	
    	Pager pager = setDataService.queryPage(Integer.parseInt(page),Integer.parseInt(rows));
    	JsonResult result= new JsonResult();
    	result.setData(pager);
        return ResponseEntity.ok(result);
    }
    
    @RequestMapping(value = "save")
    @ResponseBody
    public ResponseEntity<JsonResult> save(SetData data) {
    	
    	setDataService.getSetDataMapper().insertSelective(data);
    	
        return ResponseEntity.ok(new JsonResult());
    }
    
    @RequestMapping(value = "update")
    @ResponseBody
    public ResponseEntity<JsonResult> update(SetData data) {
    	
    	setDataService.getSetDataMapper().updateByPrimaryKeySelective(data);
    	
        return ResponseEntity.ok(new JsonResult());
    }
    
    @RequestMapping(value = "delete")
    @ResponseBody
    public ResponseEntity<JsonResult> delete(Integer id) {
    	
    	setDataService.getSetDataMapper().deleteByPrimaryKey(id);
    	
        return ResponseEntity.ok(new JsonResult());
    }
    
   
   
    /**
     * 登录
     * 
     * @param phone
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public ResponseEntity<JsonResult> login(String account, String password, 
            HttpServletRequest request) throws Exception {
    	if(!account.equals(propertieService.account)){
    		throw new CustomException(HttpStatus.BAD_REQUEST.value(), "用户名错误！");
    	}
    	User user = userService.queryUserByAccountAndPassword(account,DigestUtils.md5Hex(password));
    	if(user==null){
    		throw new CustomException(HttpStatus.BAD_REQUEST.value(), "密码错误！");
    	}
    	 request.getSession().setAttribute("user", account);
        return ResponseEntity.ok(new JsonResult());
    }
    /**
     * 修改密码
     * @param newPassword
     * @param oldPassword
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updatePassword")
    @ResponseBody
    public ResponseEntity<JsonResult> updatePassword(String account,String newPassword, String oldPassword) throws Exception {
    	User user = userService.queryUserByAccountAndPassword(account,DigestUtils.md5Hex(oldPassword));
    	if(user==null){
    		throw new CustomException(HttpStatus.BAD_REQUEST.value(), "旧密码错误！");
    	}
    	user.setPassword(DigestUtils.md5Hex(newPassword));
    	userService.getUserMapper().updateByPrimaryKey(user);
        return ResponseEntity.ok(new JsonResult());
    }
    
    @RequestMapping(value = "toUpdPwd")
    public String toUpdPwd() {
     
        return "updPassword";
    }

   

}
