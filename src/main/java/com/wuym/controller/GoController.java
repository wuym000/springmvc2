package com.wuym.controller;

import com.payment.api.config.DefaultPaymentSDKClient;
import com.payment.api.config.PaymentSDKClient;
import com.payment.api.request.PaymentGeneralStateRequest;
import com.payment.api.request.PaymentWxAccessRequest;
import com.payment.api.request.PaymentWxOrderRequest;
import com.payment.api.request.PaymentWxRegisterRequest;
import com.payment.api.response.PaymentGeneralStateResponse;
import com.payment.api.response.PaymentWxAccessResponse;
import com.payment.api.response.PaymentWxOrderResponse;
import com.payment.api.response.PaymentWxRegisterResponse;
import com.wuym.model.Classinfo;
import com.wuym.model.UserInfo;
import com.wuym.model.WeChat;
import com.wuym.modelBean.CompactDisc;
import com.wuym.modelBean.Computer;
import com.wuym.modelBean.Hifi;
import com.wuym.modelBean.USB;
import com.wuym.modelBean.impl.CDplayer;
import com.wuym.modelBean.impl.DivPc;
import com.wuym.modelBean.impl.Mylove;
import com.wuym.modelBean.impl.SgtPeppers;
import com.wuym.service.UserInfo.UserInfoService;
import com.wuym.service.classinfo.ClassInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wuym on 2017/5/22.
 */
@Controller
public class GoController implements EnvironmentAware{




    private final Log logger = LogFactory.getLog(GoController.class);
    @Autowired
    private SgtPeppers cd;


    @Autowired
    private CDplayer cDplayer;
    @Autowired
    private Mylove mylove;
    @Autowired
    private Hifi hifi;
    @Autowired
    private Computer pc;//（primary）默认选择sony
    @Autowired
    @Qualifier("divPc")
    private Computer divPc;
    @Autowired
    @Qualifier("lenovo1")
    private Computer lenovo;
    @Autowired
    @Qualifier("lenovo2")
    private Computer lenovo_;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ClassInfoService classInfoService;
    /*@Autowired
    private USB usb;*/
    //处理HEAD类型的“/”请求
    @RequestMapping("/dps")
    public ModelAndView head(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("go");
        mav.addObject("msg","gogogo");
        mav.addObject("name","value");
        cd.play();
        cDplayer.play();

        mylove.play();

        hifi.play();
        System.out.println(hifi.getTitle());

        pc.playSound();

        divPc.playSound();
        /*List<String> musList = usb.getMusicList();
        for(String music:musList){
            System.out.println("track--" + music);
        }*/
        lenovo.playSound();
        lenovo_.playSound();
        System.out.println(request.getRemoteAddr());
     //   return "go";
        return mav;
    }

    @RequestMapping("/selectUser")
    public ModelAndView selectUser(int id){
        ModelAndView mav = new ModelAndView("userInfoView");
        UserInfo userInfo = userInfoService.searchForUser(id);
        mav.addObject("id", id);
        mav.addObject("name", userInfo.getName());
        mav.addObject("age", userInfo.getAge());
        mav.addObject("sex", userInfo.getSex());
        return mav;
    }

    @RequestMapping("/testPaySdk")
    public ModelAndView paySdk(){
        ModelAndView mav = new ModelAndView("payment");
        DefaultPaymentSDKClient sdkClient = new DefaultPaymentSDKClient("http://localhost:8888");
        PaymentWxRegisterRequest request = new PaymentWxRegisterRequest();
        request.setAppName("LJG2");
        request.setMchName("测试");
        request.setSecretKey("FS3333333");
        PaymentWxRegisterResponse resp = sdkClient.excute(request);
        mav.addObject("body", resp.isSuccess()+"");
        return mav;
    }
    @RequestMapping("/testPaySdk2")
    public ModelAndView paySdk2(){
        ModelAndView mav = new ModelAndView("payment");
        DefaultPaymentSDKClient sdkClient = new DefaultPaymentSDKClient("http://localhost:8888");
        PaymentWxAccessRequest request = new PaymentWxAccessRequest();
        request.setAppName("LJG2");
        request.setPayType("WXPAY");
        request.setAppid("testid22");
        request.setMchId("134556");
        request.setNotifyUrl("http://www.wuym.com");
        request.setRemarks("备注");
        PaymentWxAccessResponse resp = sdkClient.excute(request);
        mav.addObject("body", resp.isSuccess()+"");
        return mav;
    }
    @RequestMapping("/testPaySdk3")
    public ModelAndView paySdk3(){
        ModelAndView mav = new ModelAndView("payment2");
        DefaultPaymentSDKClient sdkClient = new DefaultPaymentSDKClient("http://localhost:8888");
        PaymentGeneralStateRequest request = new PaymentGeneralStateRequest();
        String appnme = "NGB,CAN";
        request.setAppName(appnme);
        PaymentGeneralStateResponse resp = sdkClient.excute(request);
        mav.addObject("body", resp.isSuccess()+"");
        mav.addObject("list", resp.getStateList());
        return mav;
    }
    @ResponseBody
    @RequestMapping("/testPaySdk4")
    public PaymentWxOrderResponse paySdk4(String body){
        PaymentSDKClient client = new DefaultPaymentSDKClient("NATIVE", "bYTMptU4BJ4zkQJ6", "https://wx.funnycode.cn");
        PaymentWxOrderRequest request = new PaymentWxOrderRequest();
        request.setBody("测试微信扫码订单");

        request.setOutTradeNo(body);
        request.setTotalFee(1);
        request.setSpbillCreateIp("127.0.0.1");
        request.setTradeType("NATIVE");
        PaymentWxOrderResponse response = client.excute(request);
        return response;
    }

    @RequestMapping("/selectClass")
    public ModelAndView selectClass(int id){
        ModelAndView mav = new ModelAndView("classView");
        Classinfo classinfo = classInfoService.searchForClass(id);
        mav.addObject("id", id);
        mav.addObject("grade", classinfo.getGrand());
        mav.addObject("classie", classinfo.getClassie());
        return mav;
    }
    @ResponseBody
    @RequestMapping("/we")
    public WeChat we(){
        WeChat we = new WeChat();
        we.setEchostr("echostr");
        we.setNonce("nonce");
        we.setSignature("signature");
        we.setTimestamp("times");
        return we;
    }

    private Environment environment = null;
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}