package com.kongfu.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import com.kongfu.vo.Result;

public class Tools{
	
	static Properties props=System.getProperties(); //系统属性
	
	public static void main(String[] args) {
		System.out.println(MD5("123"));
	}
	//MD5加密算法
	public static String MD5(String s) {  
		 char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
		 try {  
			 byte[] strTemp = s.getBytes();  
			 MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
			 mdTemp.update(strTemp);  
			 byte[] md = mdTemp.digest();  
			 int j = md.length;  
			 char str[] = new char[j * 2];  
			 int k = 0;  
			 for (int i = 0; i < j; i++) {  
				 byte byte0 = md[i];  
				 str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
				 str[k++] = hexDigits[byte0 & 0xf];  
			 }  
			 return new String(str);  
		 } catch (Exception e) {  
			 return null;  
		 }  
	}
	
	//创建UUID
	public static String createId(){
		UUID uid = UUID.randomUUID();
		return uid.toString().replaceAll("-", "");
	}
	
	//创建Token
	public static String createToken(){
		return createId();
	}
	
	//获取服务器当前时间
	public static Timestamp createTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp createtime = Timestamp.valueOf(sdf.format(date));
		return createtime;
	}
	
	//获取服务器当前IP
	public static String getIp() throws UnknownHostException{  
        InetAddress address = InetAddress.getLocalHost();  
        return address.getHostAddress();  
    }
	
	//获取服务器当前操作系统信息
	public static String getSystem(){
		String osName = props.getProperty("os.name")+" "; //操作系统名称 
		osName  += props.getProperty("os.arch"); //操作系统构架 
		return osName;
	}
	
	//获取当前服务器JAVA版本
	public static String getJavaVersion(){
		String str = "JAVA ";
		str += props.getProperty("java.version");
		return str;
	}
	
	public static String location(){
		String str = props.getProperty("user.dir");
		return str;
	}
	
	/* Result结果集封装
	 * deal(结果集，数据，状态码(0:成功，1：失败)，处理结果信息)
	 * 说明：结果处理方法
	 */
	public static Result deal(Result result,int num,String message){
		result.setStatus(num);
		result.setMessage(message);
		return result;
	}
	
	public static Result deal(Result result,Object date,int num,String message){
		result.setData(date);
		result.setStatus(num);
		result.setMessage(message);
		return result;
	}
}









