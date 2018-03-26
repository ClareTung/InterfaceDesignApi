package cn.iponkan.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by Administrator on 2018/3/19.
 */
public class DigestUtil {


    public static String md5(String inStr) {
        try {
            return DigestUtils.md5Hex(inStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }
    }



    public static String sign(HashMap<String, String> params, String appSecret) {
        StringBuilder valueSb = new StringBuilder();
        params.put("appSecret", appSecret);
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortParams.entrySet();
        // 遍历排序的字典,并拼接value1+value2......格式
        for (Entry<String, String> entry : entrys) {
            valueSb.append(entry.getValue());
        }
        params.remove("appSecret");
        return md5(valueSb.toString());
    }


    public static boolean verify( HttpServletRequest request) throws Exception {

        String sign = request.getParameter("sign");
        if (sign == null) {
            throw new Exception(URLEncoder.encode("请求中没有带签名", "UTF-8"));
        }
        if (request.getParameter("timestamp") == null) {
         //   throw new Exception(URLEncoder.encode("请求中没有带时间戳", "UTF-8"));
        }

        HashMap<String, String> params = new HashMap<String, String>();

        // 获取url参数
        @SuppressWarnings("unchecked")
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign")) {
                // 拼接参数值字符串并进行utf-8解码，防止中文乱码产生
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
            }
        }

       // params.put("appSecret", appSecret);

        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortParams.entrySet();

        // 遍历排序的字典,并拼接value1+value2......格式
        StringBuilder valueSb = new StringBuilder();
        for (Entry<String, String> entry : entrys) {
            if(entry.getValue()!=null)
            valueSb.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        valueSb.deleteCharAt(valueSb.length()-1);//去掉最后一个&


        String mysign = md5(valueSb.toString()).toLowerCase();

       String  testsign = reverseString(mysign);
        System.out.println(testsign+"''ddddddd");

        if (testsign.equals(sign)) {
            return true;
        } else {
            return false;
        }

    }


    /**
     *   取8位之后16个字符反转
     */
    public static String reverseString(String mysign){
        String testsign="";
        StringBuffer str = new StringBuffer();
        if(mysign.length()>23) {
            testsign = mysign.substring(8, 24);
        }else{
            testsign = mysign.substring(8);
        }
        for (int i = testsign.length() - 1; i >= 0; i--) {

            char c = testsign.charAt(i);
            str.append(c);
        }

        return  str.toString();
    }



    public static void main(String[] args) throws Exception {
      /*  HttpClient httpclient = new HttpClient();
        PostMethod httpPost =new PostMethod("http://localhost:8999/");
        NameValuePair[] param = { new NameValuePair("loginName", "admin"),new NameValuePair("loginPassword","zl@bjs")};
        httpPost.setRequestBody(param);
        System.out.println(httpclient.executeMethod(httpPost));*/
      Map<String,String> map= new HashMap<>();
      map.put("loginName","admin");
      map.put("loginPassword","zl@bjs");
        String s =HttpClientUtils.doPost("http://localhost:8999/login",map);
        String s1 = HttpClientUtils.sendPost("http://localhost:8999/stock_in/gss","");
        Map mapTypes = JSON.parseObject(s);
        Map json = (Map) JSONObject.parse(s);
        System.out.println(s+"\n"+mapTypes);
        System.out.println(json.get("ll"));
   /*  PageData p =new PageData();
     p.put("s","ss");

      String s = "{\"data\":"+"\""+p+"\""+",\"pageParams\":null}";
        Map mapTypes = JSON.parseObject(s);
        System.out.println(s+"\n"+mapTypes);*/
    }


    public  static String toSign(PageData pageData){



        Iterator iter = pageData.entrySet().iterator();
        while (iter.hasNext()) {
            while (iter.hasNext()) {
                Entry entry = (Entry) iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key.equals("sign") || value == null) {
                  //  iter.remove();
                }
            }
        }

        Map<String, String> sortParams = new TreeMap<String, String>(pageData);
        Set<Entry<String, String>> entrys = sortParams.entrySet();

        // 遍历排序的字典,并拼接value1+value2......格式
        StringBuilder valueSb = new StringBuilder();
        for (Entry<String, String> entry : entrys) {
            if(entry.getValue()!=null&&!entry.getKey().equals("sign"))
                valueSb.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        valueSb.deleteCharAt(valueSb.length()-1);//去掉最后一个&


        String mysign = md5(valueSb.toString()).toLowerCase();

        String  result = reverseString(mysign);
        return  result;
    }
}
