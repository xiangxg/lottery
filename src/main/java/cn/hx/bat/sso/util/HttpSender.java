package cn.hx.bat.sso.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求发送类
 * 
 * @author chenlong
 * 
 */
public class HttpSender
{
	//public static final String url ="/user/queryUserByToken.do";
    private static final Logger log = LoggerFactory.getLogger(HttpSender.class);

    /**
     * post方式提交
     * 
     * @param urladdress
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String urladdress, Object... params) throws Exception
    {
        return sendRequest(urladdress, "POST", params);
    }

    /**
     * get方式提交
     * 
     * @param urladdress
     * @param params
     * @return
     * @throws Exception
     */
    public static String get(String urladdress, Object... params) throws Exception
    {
        return sendRequest(urladdress, "GET", params);
    }

    /**
     * 
     * @param urladdress
     * @param method
     * @param params
     * @return
     * @throws Exception
     */
    public static String sendRequest(String urladdress, String method, Object... params) throws Exception
    {
        URL url = new URL(urladdress);

        URLConnection rulConnection = url.openConnection();// 此处的urlConnection对象实际上是根据URL的
        // 请求协议(此处是http)生成的URLConnection类
        // 的子类HttpURLConnection,故此处最好将其转化
        // 为HttpURLConnection类型的对象,以便用到
        // HttpURLConnection更多的API.如下:

        HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;

        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        httpUrlConnection.setDoOutput(true);

        // 设置是否从httpUrlConnection读入，默认情况下是true;
        httpUrlConnection.setDoInput(true);

        // 设置超时时间
        httpUrlConnection.setConnectTimeout(60000);

        // Post 请求不能使用缓存
        httpUrlConnection.setUseCaches(false);

        // 设定请求的方法为"POST"，默认是GET
        httpUrlConnection.setRequestMethod(null == method ? "GET" : method);

        // 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
        httpUrlConnection.connect();

        // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
        // 所以在开发中不调用上述的connect()也可以)。
        OutputStream outStrm = httpUrlConnection.getOutputStream();

        StringBuffer content = new StringBuffer();
        if (null != params)
        {
            for (int i = 0; i < params.length - 1; i += 2)
            {
                if (i != 0)
                {
                    content.append("&");
                }
                content.append(params[i]).append("=").append(String.valueOf(params[i + 1]));
            }
        }
        log.info("向url:" + urladdress + "发送" + httpUrlConnection.getRequestMethod() + "请求消息:" + content.toString());
        // 向对象输出流写出数据，这些数据将存到内存缓冲区中
        outStrm.write(content.toString().getBytes("utf-8"));

        // 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
        outStrm.flush();

        // 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
        // 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
        //outStrm.close();

        // 调用HttpURLConnection连接对象的getInputStream()函数,
        // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
        if (HttpURLConnection.HTTP_OK == httpUrlConnection.getResponseCode())
        {
            InputStream inStrm = httpUrlConnection.getInputStream(); // <===注意，实际发送请求的代码段就在这里

            BufferedReader reader = new BufferedReader(new InputStreamReader(inStrm, "utf-8"));
            // 定义BufferedReader输入流来读取URL的ResponseData
            String strRead;
            StringBuffer result = new StringBuffer();
            while ((strRead = reader.readLine()) != null)
            {
                result.append(strRead);
                result.append("\r\n");
            }
            return result.toString();
        }
        return null;
    }

    public static void main(String[] args) throws Exception
    {
        /*StringBuffer bf = new StringBuffer();
        bf.append("openId=").append("o2k9wuKY-fxt33kQvvh_1TK3qnmw");
        bf.append("&").append("parentName=").append("老钱");
        bf.append("&").append("studentName=").append("小钱");
        bf.append("&").append("studentNo=").append("学号");
        bf.append("&").append("className=").append("班级名称");
        bf.append("&").append("time=").append("2014/09/15 07:30:16");
        bf.append("&").append("optstr=").append("进入");
        bf.append("&").append("position=").append("校门");
        String result = post("http://localhost:8080/weixin/msg/attend.do", "openId", "o2k9wuKY-fxt33kQvvh_1TK3qnmw",
                "parentName", "老钱", "studentName", "小钱");
        System.out.println(result);*/
       /* HttpClient httpclient = new DefaultHttpClient();
        String url="http://test.hxfzsoft.com:10047/minsheng/client/login/doLogin.do?isThirdLogin=0&name=yg_ba03008178&password=123456&random=app";
        
        HttpPost httppost=new HttpPost(url);
        httpclient.execute(httppost);
        httppost.releaseConnection();
        httppost = new HttpPost("http://test.hxfzsoft.com:10047/minsheng/client/user/updateUserProfile.do");
        NameValuePair nvp=new BasicNameValuePair(name, value);
        new UrlEncodedFormEntity(parameters)
        httppost.setEntity(entity);
        FileBody bin = new FileBody(new File("E:\\codebackup\\code\\pojo\\pojo\\WebRoot\\images\\404.jpg"),"image/jpg");
        //FileBody bin2 = new FileBody(new File("E:\\codebackup\\code\\pojo\\pojo\\WebRoot\\images\\index\\bg2.jpg"),"image/jpg");
        //FileBody bin2 = new FileBody(new File(filepath + File.separator + filename2));  
        StringBody comment = new StringBody("311591832972274");  
        //StringBody comment1 = new StringBody("a150805077");  
        MultipartEntity reqEntity = new MultipartEntity();  
        reqEntity.addPart("userHeadImg", bin);//file1为请求后台的File upload;属性      
        //reqEntity.addPart("upload", bin2);//file2为请求后台的File upload;属性  
        reqEntity.addPart("profile.socialCardId", comment);
        //reqEntity.addPart("actId", comment1);
        httppost.setEntity(reqEntity);  
          
        HttpResponse response = httpclient.execute(httppost);  
        int statusCode = response.getStatusLine().getStatusCode();  
          
              
        if(statusCode == HttpStatus.SC_OK){
            String s=EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(s);
            
        }  
        */
   
        
    }
}
