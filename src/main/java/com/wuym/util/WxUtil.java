package com.wuym.util;

import com.wuym.controller.TestWXController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by wuym on 2017/6/26.
 */
public class WxUtil {
    public static final String APPID = "wxead7449448d6dbfa";
    public static final String SECRET = "411927fa003850bae79e17d0d5354296";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token"; //获取accesstoken_url
    public static final String GETTICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket"; //获取ticket
    public static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";  //统一下单
    public static final String MCH_ID = "1480439962";   //商户号
    public static final String MCH_KEY = "20170209FengShuiAirportCloud2017";   //商户KEY


    public static Map wxHttp(String recordUrl, Map<String, String> params){
        HashMap data = new HashMap();

        try {
            //  String recordUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe6deceee697768d6&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
            Set set = params.keySet();
            Iterator<String> it = set.iterator();
            recordUrl += "?";
            while(it.hasNext()){
                String key = it.next();
                recordUrl += "&" + key + "=" + params.get(key);
            }
            JSONArray json = JSONArray.fromObject(params);
            String requestStr = json.getString(0);
            System.out.println("******str" + requestStr);
            URL url = new URL(recordUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.connect();

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            out.write(convertKeyValueFormat(params));
            out.flush();
            out.close(); // flush and close

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();
            conn.disconnect();
            System.out.println(result.toString());
            JSONArray result_jo = JSONArray.fromObject("[" + result.toString() + "]");
            JSONObject result_json_map = result_jo.getJSONObject(0);


            // 将json字符串转换成jsonObject
            Iterator jsonKeys = result_json_map.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (jsonKeys.hasNext())
            {
                String key = String.valueOf(jsonKeys.next());
                Object value =  result_json_map.get(key);
                data.put(key, value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    /**
     * SHA1加密
     * */
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //请求xml组装
    public static String getRequestXml(SortedMap<String,Object> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)||"sign".equalsIgnoreCase(key)) {
                sb.append("<"+key+">"+"<![CDATA["+value+"]]></"+key+">");
            }else {
                sb.append("<"+key+">"+value+"</"+key+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static String ArrayToXml(Map<String, String> arr) {

        String xml = "<xml>";





        Iterator<Map.Entry<String, String>> iter = arr.entrySet().iterator();

        while (iter.hasNext()) {

            Map.Entry<String, String> entry = iter.next();

            String key = entry.getKey();

            String val = entry.getValue();

            xml += "<" + key + ">" + val + "</" + key + ">";

        }





        xml += "</xml>";

        return xml;

    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }



    //Map转xml数据
    public static String GetMapToXML(Map<String,String> param){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (Map.Entry<String,String> entry : param.entrySet()) {
            sb.append("<"+ entry.getKey() +">");
            sb.append(entry.getValue());
            sb.append("</"+ entry.getKey() +">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    //请求方法
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}"+ ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}"+ e);
        }
        return null;
    }

    //xml解析
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();

        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    //生成签名
    public static String createSign(SortedMap<String,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WxUtil.MCH_KEY);
        System.out.println(sb.toString());
        String sign = MD5(sb.toString()).toUpperCase();
        return sign;
    }


    public static void main(String[] args) {
        String md5 = MD5("appid=wxe6deceee697768d6&body=风数支付测试&mch_id=1480439962&nonce_str=5a21a15a3f7d4c11&notify_url=http://173290mz01.iok.la/notifyUrl.do&out_trade_no=8831058&spbill_create_ip=127.0.0.1&total_fee=1&trade_type=JSAPI&key=20170209FengShuiAirportCloud2017");
        System.out.println(md5.toUpperCase());
    }
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static String convertKeyValueFormat(Map<String, String>  paramsMap) {
        Iterator itResult = paramsMap.entrySet().iterator();
        StringBuilder result = new StringBuilder();
        while(itResult.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) itResult.next();
            if (result.length() != 0) {
                result.append("&");
            }
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return result.toString();
    }
}
