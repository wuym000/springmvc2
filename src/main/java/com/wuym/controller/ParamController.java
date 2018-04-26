package com.wuym.controller;

import com.wuym.model.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wuym on 2017/6/9.
 */
@Controller
public class ParamController {
    @RequestMapping("/recive")
    public ModelAndView recive(@RequestParam(value="max",defaultValue = "20") long max,
                               @RequestParam(value="count",defaultValue = "21") int count){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("recive");
        mav.addObject("max", max);
        mav.addObject("count", count);
        return mav;
    }
    @RequestMapping("/recive2")
    public ModelAndView recive_(long max,
                               int count){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("recive");
        mav.addObject("max", max);
        mav.addObject("count", count);
        return mav;
    }

    @RequestMapping("/recive/{max}/{count}")
    public ModelAndView recive2(@PathVariable("max") long max,
                               @PathVariable("count") int count){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("recive");
        mav.addObject("max", max);
        mav.addObject("count", count);
        return mav;
    }

    @RequestMapping(value="/reciveForm", method = RequestMethod.POST)
    public ModelAndView reciveForm(Page page){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/recive.do");
        mav.addObject("max", page.getMax());
        mav.addObject("count", page.getCount());

        return mav;
    }

    @RequestMapping("/numRecive")
    public ModelAndView recive(String max, String count){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("recive");
        int m = Integer.parseInt(max);
        mav.addObject("max", m);
        mav.addObject("count", count);
        return mav;
    }
}
