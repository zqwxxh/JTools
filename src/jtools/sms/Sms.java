package jtools.sms;
/** * 
@author  张青伟  E-mail: 790036007@qq.com 
@date 创建时间：2016-1-4 上午10:37:46 * 
@description: 短信发送
@version 1.0 * 
@parameter  * 
@since  * 
@return  
 */

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;   
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   


public class Sms {
		
	/**
	 * @param mobilePhone 手机号码
	 * @param url 请求地址   例：http://106.ihuyi.cn/webservice/sms.php?method=Submit
	 * @param username 短信网关用户名
	 * @param password 短信网关密码
	 * @param content 发送内容
	 * @param ContentCharset 内容编码  UTF-8
	 * @return
	 */
	public static int sendCode(String mobilePhone,String url,String username,String password,String content,String ContentCharset) {
		
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(url); 
			
		//client.getParams().setContentCharset("GBK");		
		client.getParams().setContentCharset(ContentCharset);
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

		
		int mobile_code = (int)((Math.random()*9+1)*100000);

		//System.out.println(mobile);
		
		content = new String(content); 

		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", "cf_zhizhen"), 
			    new NameValuePair("password", "zhizhenpinhui"), //密码可以使用明文密码或使用32位MD5加密
			    //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
			    new NameValuePair("mobile", mobilePhone), 
			    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);		
		
		
		try {
			client.executeMethod(method);	
			
			String SubmitResult =method.getResponseBodyAsString();
					
			//System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();


			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			String smsid = root.elementText("smsid");	
			
			
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
						
			if("2".equals(code)){
				return mobile_code;
			}
			 
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return 0;
	}
	
}
