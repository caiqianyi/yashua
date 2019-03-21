package com.lebaoxun.portal.rest.yashua;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lebaoxun.commons.exception.I18nMessageException;
import com.lebaoxun.commons.exception.ResponseMessage;
import com.lebaoxun.commons.utils.StringUtils;
import com.lebaoxun.modules.account.entity.UserEntity;
import com.lebaoxun.modules.account.service.IUserService;
import com.lebaoxun.modules.yashua.entity.UserDeviceEntity;
import com.lebaoxun.modules.yashua.service.IUserDeviceService;
import com.lebaoxun.security.oauth2.Oauth2SecuritySubject;


/**
 * 用户设备表
 *
 * @author caiqianyi
 * @email 270852221@qq.com
 * @date 2018-06-23 16:42:44
 */
@RestController
public class UserDeviceController {
    @Autowired
    private IUserDeviceService userDeviceService;
    
    @Resource
	private Oauth2SecuritySubject oauth2SecuritySubject;

    @Resource
    private IUserService userService;
    /**
     * 列表
     */
    @RequestMapping("/yashua/device/mylist")
    ResponseMessage list(@RequestParam Map<String, Object> params){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
    	params.put("account", ue.getAccount());
        return userDeviceService.list(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/yashua/device/info/{id}")
    ResponseMessage info(@PathVariable("id") Integer id){
        return userDeviceService.info(id, oauth2SecuritySubject.getCurrentUser());
    }
    
    
	/**
     * 评论
     */
    @RequestMapping("/yashua/device/macupload")
    @ResponseBody
    ResponseMessage replys(@RequestParam("identity") String identity,@RequestParam("mac") String mac) throws Exception{
    	 UserDeviceEntity userDevice = new UserDeviceEntity();
    	 System.out.println("identity===="+identity+"===mac===="+mac);
    	 Map<String, Object> mparams =new HashMap<String, Object>();
    	 userDevice.setIdentity(identity);
    	 mparams.put("mac", mac);
    	 mparams.put("identity", identity);
    	 String biaos = userDeviceService.selcone(mparams).getErrcode();
          if(biaos.equals("-2")){
          	throw new I18nMessageException("-2","设备号重复");
         }
        if(biaos.equals("-3")){
        	throw new I18nMessageException("0","设备号修改成功");
        }

    	
       	    userDevice.setMac(mac);
       	 
           //1、获取AccessToken  
			String accessToken =getAccessToken(); //"16_LN17KXa9jdOU5Eg4qjupBupH_XxdEbIHLxCzZ944nAoIC9wHmL1fKhx82duWdX7ad_0B8W-Sip5jGnM1DAckBzAgD61a4_kbt8P7tsfm8dDrnlyGKtrPVsv-EuMFKSaAJAUZH";  
			System.out.println("accessToken========="+accessToken);
			//2、获取deviceid与qrticket 
			Map<String,String> mtemp = getdeviceid(accessToken);
			String deviceId =mtemp.get("deviceid"); //"16_LN17KXa9jdOU5Eg4qjupBupH_XxdEbIHLxCzZ944nAoIC9wHmL1fKhx82duWdX7ad_0B8W-Sip5jGnM1DAckBzAgD61a4_kbt8P7tsfm8dDrnlyGKtrPVsv-EuMFKSaAJAUZH";  
			System.out.println("deviceId========="+deviceId);
			//3、绑定deviceid 
			String params="{\"device_num\":\"1\",\"device_list\":[{"
	                   +"\"id\":\""+deviceId+"\","
	                   +"\"mac\":\""+mac+"\","
	                    +"\"connect_protocol\":\"3\","
	                    +"\"auth_key\":\"\","
	                    +"\"close_strategy\":\"1\","
	                    +"\"conn_strategy\":\"1\","
	                    +"\"crypt_method\":\"0\","
	                    +"\"auth_ver\":\"0\","
	                    +"\"manu_mac_pos\":\"-1\","
	                    +"\"ser_mac_pos\":\"-2\""
	                    + "}],"
	                    +"\"op_type\":\"1\","
	                    +"\"product_id\": \"50972\""
	                   + "}";
			String s=sendPost("https://api.weixin.qq.com/device/authorize_device?access_token="+accessToken, params);
            System.out.println("=========s返回：===="+s);
			//4、验证qrticket 
            String qrticket =mtemp.get("qrticket");
            String params1="{\"ticket\":\""+qrticket+"\""
	                   + "}";
            String s1=sendPost("https://api.weixin.qq.com/device/verify_qrcode?access_token="+accessToken, params1);
            System.out.println("=========s1返回：===="+s1);
            userDevice.setIcon(qrticket);
    	return userDeviceService.save(1l, userDevice);
    }

    /**
     * 绑定
     */
    @RequestMapping("/yashua/device/bind")
    ResponseMessage bind(@RequestParam("identity") String identity){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
        return userDeviceService.bind(ue.getAccount(), identity, 5);//最多绑定5个设备
    }

    /**
     * 解绑
     */
    @RequestMapping("/yashua/device/unbind")
    ResponseMessage unbind(@RequestParam("identity") String identity){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
        return userDeviceService.unbind(ue.getAccount(), identity);
    }
   
    /**
     * 链接设备
     */
   
    @RequestMapping("/yashua/device/connect")
    ResponseMessage connect(@RequestParam("identity") String identity){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
        return userDeviceService.connect(ue.getAccount(), identity);
    }
    
    /**
     * 设置牙刷名称
     */
    
    @RequestMapping("/yashua/device/setName")
    ResponseMessage setName(@RequestParam("name") String name,@RequestParam("identity") String identity){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
        return userDeviceService.setName(ue.getAccount(), name,identity);
    }
    
    
    
    /**
     * 获取牙刷名称
     */
    
    @RequestMapping("/yashua/device/getDeviceName")
    ResponseMessage getDeviceName(@RequestParam("identity") String identity){
    	UserEntity ue = userService.findByUserId(oauth2SecuritySubject.getCurrentUser());
        return userDeviceService.getDeviceName(ue.getAccount(),identity);
    }
    
	 private String getAccessToken() throws Exception{
	        String Url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx3916e075f64cf1b5&secret=666749094c9fca38de21e2a9d572bc49";
	        URL url = new URL(Url);
		     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	 
		     connection.setRequestMethod("GET");
		     connection.setDoOutput(true);
		     connection.setDoInput(true);
		     connection.connect();
	 
		     //获取返回的字符
		     InputStream inputStream = connection.getInputStream();
		     int size =inputStream.available();
		     byte[] bs =new byte[size];
		     inputStream.read(bs);
		     String message=new String(bs,"UTF-8");
		     JSONObject json = JSON.parseObject(message);
		     //获取access_token
		     String token = json.getString("access_token");
	        return token;
	    }
	 private Map<String,String> getdeviceid(String acctoken) throws Exception{
	        String Url = "https://api.weixin.qq.com/device/getqrcode?access_token="+acctoken+"&product_id=50972";
	        URL url = new URL(Url);
		     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	 
		     connection.setRequestMethod("GET");
		     connection.setDoOutput(true);
		     connection.setDoInput(true);
		     connection.connect();
	 
		     //获取返回的字符
		     InputStream inputStream = connection.getInputStream();
		     int size =inputStream.available();
		     byte[] bs =new byte[size];
		     inputStream.read(bs);
		     String message=new String(bs,"UTF-8");
		     JSONObject json = JSON.parseObject(message);
		     //获取access_token
		     String deviceid = json.getString("deviceid");
		     String qrticket = json.getString("qrticket");
		     Map<String,String> dqm = new HashMap<String,String>();
		     dqm.put("deviceid", deviceid);
		     dqm.put("qrticket", qrticket);
	        return dqm;
	    }
	 
	 public static String sendPost(String requrl,String param){
         URL url;
          String sTotalString="";  
        try {
            url = new URL(requrl);
             URLConnection connection = url.openConnection(); 
               
             connection.setRequestProperty("accept", "*/*");
             connection.setRequestProperty("connection", "Keep-Alive");
             connection.setRequestProperty("Content-Type", "text/xml");
            // connection.setRequestProperty("Content-Length", body.getBytes().length+"");
             connection.setRequestProperty("User-Agent",
                     "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
               
               
                connection.setDoOutput(true);  
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");  
                out.write(param); // 向页面传递数据。post的关键所在！  
                out.flush();  
                out.close();  
                // 一旦发送成功，用以下方法就可以得到服务器的回应：  
                String sCurrentLine;  
                
                sCurrentLine = "";  
                sTotalString = "";  
                InputStream l_urlStream;  
                l_urlStream = connection.getInputStream();  
                // 传说中的三层包装阿！  
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(  
                        l_urlStream));  
                while ((sCurrentLine = l_reader.readLine()) != null) {  
                    sTotalString += sCurrentLine + "\r\n";  
            
                }  
                  
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
             
            System.out.println(sTotalString);  
            return sTotalString;
     }
}
