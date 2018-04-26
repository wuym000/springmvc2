package com.wuym.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuym.model.Classinfo;
import com.wuym.model.SubClassinfo;
import com.wuym.model.UserInfo;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuym on 2017/6/27.
 */
public class Test {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        /*String md5 = MD5("appid=wxe6deceee697768d6&body=风数支付测试&mch_id=1480439962&nonce_str=e3a028f3096a41b9&notify_url=http://173290mz01.iok.la/notifyUrl.do&out_trade_no=7949195&spbill_create_ip=127.0.0.1&total_fee=1&trade_type=JSAPI&key=20170209FengShuiAirportCloud2017");
        System.out.println(md5.toUpperCase());
*/
        /*Class<UserInfo> c = UserInfo.class;
        System.out.println(c.getPackage().getName());*/
        /*SubClassinfo subClassinfo = new SubClassinfo();
        subClassinfo.setSubField("field");
        subClassinfo.setId(101);
        subClassinfo.setClassie(102);
        subClassinfo.setGrand(103);
        subClassinfo.setSubSubSubb1Subb("sub");
        subClassinfo.setSubIntField(10003);
*/
        /*Field[] fields = subClassinfo.getClass().getDeclaredFields();
       // Field[] superFields = subClassinfo.getClass().getSuperclass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            System.out.println(field.getName() + "---->" + field.get(subClassinfo) );
        }*/

        /*List<Field> fields = new ArrayList<Field>();
        Class clazz = subClassinfo.getClass();
        while(clazz != null){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        for(Field field:fields){
            field.setAccessible(true);
            System.out.println(field.getName() + "---->" + field.get(subClassinfo) );
        }*/

        /*System.out.println(fromObjectToSortedMap(subClassinfo));*/
        /*for(Field field:superFields){
            field.setAccessible(true);
            System.out.println(field.getName() + "---->" + field.get(subClassinfo) );
        }*/
//        System.out.println(subClassinfo.getClass().getDeclaredField("subField"));

        /*String json = "{'nofield':'nofield','subField':'field', 'id':101, 'classie':102, 'grand':103, 'subSubSubb1Subb':'sub', 'subIntField':10004}";
        JSONObject js = JSONObject.parseObject(json);
        SubClassinfo s = js.toJavaObject(SubClassinfo.class);
        System.out.println(s.getSubField());
        System.out.println(s.getSubIntField());
        System.out.println(s.getSubSubSubb1Subb());
        System.out.println(s.getClassie());
        System.out.println(s.getGrand());
        System.out.println(s.getId());*/
        //System.out.println(lineToHump("abcd"));

        /*String json = "{'sub_field':'field', 'id':101, 'classie':102, 'grand':103, 'sub_sub_subb1_subb':'sub', 'sub_int_field':10004}";
        JSONObject js = JSONObject.parseObject(json);
        //下划线转驼峰
        Set<String> keys = js.keySet();
        Iterator<String> it = keys.iterator();

        JSONObject jsNew = new JSONObject();
        while(it.hasNext()){
            String key = it.next();
            jsNew.put(lineToHump(key), js.get(key));

        }
        System.out.println(js);
        System.out.println(jsNew);*/

       /* Classinfo c = s;
        System.out.println(c.getClass().getSimpleName());*/
       Pattern pattern = Pattern.compile("^((2[5-9])|30)\\d{14,22}$");
       Pattern pattern1 = Pattern.compile("^(1[0-5])\\d{16}");
      //  System.out.println(pattern.matcher("305724205885572445").matches());
        System.out.println(pattern1.matcher("120061098828009406").matches());
    }

    //获取包括父类的所有属性
    public static SortedMap<String , Object> fromObjectToSortedMap(Object obj) throws NoSuchFieldException, IllegalAccessException {
        SortedMap<String,Object> reMap = new TreeMap<>();
        //    Field[] fields = obj.getClass().getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        Class clazz = obj.getClass();
        while (clazz != null){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }


        for(int i = 0; i < fields.size(); i++){
            Field subField = fields.get(i);
            subField.setAccessible(true);
            Object o = subField.get(obj);
            if(o != null){
                reMap.put(camelToUnderline(subField.getName()), o);
            }
        }
        return reMap;
    }
    // 驼峰命名转下划线命名
    public static String camelToUnderline(String str) {
        if (str == null || str.trim().isEmpty()){
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append('_');
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    //下划线转驼峰
    public static String lineToHump(String str){
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
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
        }
        return result;
    }
}
