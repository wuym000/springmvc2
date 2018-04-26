package com.wuym.controller;

import com.wuym.cache.CacheUtil;
import com.wuym.model.WeChat;
import com.wuym.util.WxUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by wuym on 2017/6/5.
 */
@Controller
public class TestWXController {
    @Autowired
    private CacheUtil cacheUtil;
    /**
     * 校验信息是否从微信服务发起
     * */
    @RequestMapping("/wx")
    public void wxRecive(WeChat weChat, PrintWriter out){
        //TODO 校验逻辑

        String echostr = weChat.getEchostr();
        System.out.println(echostr);
        out.print(echostr);
        out.flush();
        out.close();
    }
    @RequestMapping("/authResponse")
    public ModelAndView authResponse(String code, String state){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auth");
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", WxUtil.APPID);
        params.put("secret", WxUtil.SECRET);
        params.put("code", code);
        params.put("grant_type", WxUtil.GRANT_TYPE);
        //获取access_token以及openid
        Map respMap = WxUtil.wxHttp(WxUtil.ACCESSTOKEN_URL, params);
        mav.addObject("access_token",respMap.get("access_token"));
        mav.addObject("expires_in",respMap.get("expires_in"));
        mav.addObject("refresh_token",respMap.get("refresh_token"));
        mav.addObject("openid",respMap.get("openid"));
        mav.addObject("scope",respMap.get("scope"));
        /*//获取jsapi_ticket
        Map nomal_access_token_map = test(WxUtil.APPID, WxUtil.SECRET);
        String jsapi_ticket = getTicket(nomal_access_token_map.get("access_token")+"");
        mav.addObject("jsapi_ticket",jsapi_ticket);
        //获取url ，暂时写死  这一步为止以上信息可以作缓存 后面的调用每次单独发起
        String url="http://173290mz01.iok.la/authWxActive.do";
        //时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
        //将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
        String signature =WxUtil.SHA1(str);
        System.out.println("参数："+str+"\n签名："+signature);
        mav.addObject("noncestr",noncestr);
        mav.addObject("timestamp",timestamp);
        mav.addObject("signature",signature);*/
        return mav;
    }
    @RequestMapping("/authWxActive")
    public ModelAndView active(String code, String state){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("active");
        //获取jsapi_ticket
        Map nomal_access_token_map = test(WxUtil.APPID, WxUtil.SECRET);
        String jsapi_ticket = getTicket(nomal_access_token_map.get("access_token")+"");
        mav.addObject("jsapi_ticket",jsapi_ticket);
        //TODO 获取url ，暂时写死  这一步为止以上信息可以作缓存暂不实现 后面的调用每次单独发起,因为每天请求ticket次数有上限2000次*7200秒
        String url="http://173290mz01.iok.la/authWxActive.do";
        //时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
        //将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
        String signature =WxUtil.SHA1(str);
        System.out.println("参数："+str+"\n签名："+signature);
        mav.addObject("appid",WxUtil.APPID);
        mav.addObject("noncestr",noncestr);
        mav.addObject("timestamp",timestamp);
        mav.addObject("signature",signature);

        //
        /*Map<String, String> params = new HashMap<String, String>();
        params.put("appid", WxUtil.APPID);
        params.put("secret", WxUtil.SECRET);
        params.put("code", code);
        params.put("grant_type", WxUtil.GRANT_TYPE);
        //获取access_token以及openid
        Map respMap = WxUtil.wxHttp(WxUtil.ACCESSTOKEN_URL, params);
        mav.addObject("access_token",respMap.get("access_token"));
        mav.addObject("expires_in",respMap.get("expires_in"));
        mav.addObject("refresh_token",respMap.get("refresh_token"));
        mav.addObject("openid",respMap.get("openid"));
        mav.addObject("scope",respMap.get("scope"));*/
        return mav;
    }

    @RequestMapping("/h5pay")
    public ModelAndView h5pay(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("h5pay");
        //时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String trade_no =  (int)(Math.random()*10000000) + "";//随机订单号
        SortedMap<String, Object> params = new TreeMap<String, Object>();
        params.put("appid", WxUtil.APPID);
        params.put("mch_id", WxUtil.MCH_ID);
        params.put("nonce_str", noncestr);
        params.put("body", "风数H5支付测试");
        params.put("out_trade_no", trade_no);
        params.put("total_fee", "1");
        params.put("spbill_create_ip", request.getRemoteAddr());
        params.put("notify_url", "http://173290mz01.iok.la/notifyUrl.do");
        params.put("trade_type", "MWEB");
        params.put("scene_info", "{\"h5_info\": {\"type\":\"WEB\",\"app_name\": \"风数\",\"package_name\": \"com.fengshu.tech\"}}");
        String sign = WxUtil.createSign(params);
        System.out.println(sign);
        params.put("sign", sign);
        String xml = WxUtil.getRequestXml(params);
//System.out.println(" xml 为"+ xml);
        String xmlStr = WxUtil.httpsRequest(WxUtil.UNIFIEDORDER, "POST", xml);
        System.out.println(xmlStr);
        Map map = null;
        try {
            map = WxUtil.doXMLParse(xmlStr);
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(map.get("prepay_id")!=null & map.get("mweb_url")!=null){
            mav.addObject("mweb_url" , map.get("mweb_url"));
        }
        return mav;
    }
    /**
     * 统一下单
     * */
    @ResponseBody
    @RequestMapping("/order")
    public Map order(String openid,HttpServletRequest request){
        //TODO 校验
        //时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String trade_no =  (int)(Math.random()*10000000) + "";//随机订单号
//        Map<String, String> params = new HashMap<String, String>();
        SortedMap<String, Object> params = new TreeMap<String, Object>();
        params.put("appid", WxUtil.APPID);
        params.put("mch_id", WxUtil.MCH_ID);
        params.put("nonce_str", noncestr);
        params.put("body", "风数支付测试");
        params.put("out_trade_no", trade_no);
        params.put("total_fee", "1");
        params.put("spbill_create_ip", request.getRemoteAddr());
        params.put("notify_url", "http://173290mz01.iok.la/notifyUrl.do");
        params.put("trade_type", "JSAPI");
        params.put("openid", openid);

        String paramsStr = "appid=" + WxUtil.APPID + "&body=风数支付测试&mch_id=" + WxUtil.MCH_ID + "&nonce_str=" + noncestr +
                "&notify_url=http://173290mz01.iok.la/notifyUrl.do&openid=" + openid + "&out_trade_no=" + trade_no + "&spbill_create_ip=" + params.get("spbill_create_ip")
                + "&total_fee=1&trade_type=JSAPI&key=" + WxUtil.MCH_KEY;
       // paramsStr = "appid=wxe6deceee697768d6&body=风数支付测试&mch_id=1480439962&nonce_str=5a21a15a3f7d4c11&notify_url=http://173290mz01.iok.la/notifyUrl.do&out_trade_no=8831058&spbill_create_ip=127.0.0.1&total_fee=1&trade_type=JSAPI&key=20170209FengShuiAirportCloud2017";
        System.out.println("需要加密字符串-------->" + paramsStr);
        String sign = WxUtil.MD5(paramsStr.trim()).toUpperCase();
        System.out.println("md5签名--->" + sign);
        params.put("sign", sign);
        String xml = WxUtil.getRequestXml(params);
//System.out.println(" xml 为"+ xml);
        String xmlStr = WxUtil.httpsRequest(WxUtil.UNIFIEDORDER, "POST", xml);
        System.out.println(xmlStr);
        Map map = null;
         try {
             map = WxUtil.doXMLParse(xmlStr);
         } catch (JDOMException e) {
             // TODO Auto-generated catch block
                      e.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
        if(map.get("prepay_id")!=null){
            //时间戳和随机字符串
            String payNoncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
            SortedMap<String, Object> payMap = new TreeMap<String, Object>();
            payMap.put("appId", WxUtil.APPID);
            payMap.put("timeStamp", timestamp);
            payMap.put("nonceStr", payNoncestr);
            payMap.put("package", "prepay_id=" + map.get("prepay_id"));
            payMap.put("signType", "MD5");
            String paySign = WxUtil.createSign(payMap);
            payMap.put("paySign", paySign);
            map.put("payMap", payMap);
        }
        return map;
        /*Map respMap = new HashMap();
        respMap.put("xmlStr", xmlStr);
        return respMap;*/

    }





    @ResponseBody
    @RequestMapping(value = "/notifyUrl")
    public Map notifyUrl(){
        return null;
    }
    @ResponseBody
    @RequestMapping(value = "/getAccessToken")
    public Map test(String appid, String secret){
        Map map = new HashMap();
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("grant_type", "client_credential");
            /*params.put("appid", "wxe6deceee697768d6");
            params.put("secret", "546e3cd49ad897faa87d0c702c83ab5c");*/
            params.put("appid", appid);
            params.put("secret", secret);

           String recordUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
          //  String recordUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe6deceee697768d6&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
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
            String access_token = result_json_map.get("access_token") + "";
            String expires_in = result_json_map.get("expires_in") + "";
            map.put("access_token", access_token);
            map.put("expires_in", expires_in);
            /*Object errorCode = result_json_map.get("errorCode");
            if (errorCode != null && "success".equals(errorCode)) {
                JSONObject json_result = (JSONObject) result_json_map.get("result");
                String timestamp = json_result.get("timestamp") + "";
                String wxAppId = json_result.get("appId") + "";
                String nonceStr = json_result.get("nonceStr") + "";
                String signature = json_result.get("signature") + "";
                map.put("timestamp", Long.parseLong(timestamp));
                map.put("wxAppId", wxAppId);
                map.put("nonceStr", nonceStr);
                map.put("signature", signature);
                map.put("errorNo", "0");
            } else {
                map.put("errorNo", "-1");
                map.put("errorInfo", "获取数据失败");
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public static String getTicket(String access_token){
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", access_token);
        params.put("type", "jsapi");
        Map resp = WxUtil.wxHttp(WxUtil.GETTICKET_URL, params);
        return ""+resp.get("ticket");
    }
    @ResponseBody
    @RequestMapping("/ehcache")
    public Map ehcache(){
        Map map = new HashMap();
        String ticket = cacheUtil.ticket("ticket", "a", "b");
        System.out.println("huode;;"+ticket);
        map.put("ticket", ticket);
        return map;
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
