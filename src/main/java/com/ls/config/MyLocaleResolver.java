package com.ls.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

//语言解析，请求链接携带语言参数
public class MyLocaleResolver implements LocaleResolver {
    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {

        String language = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault(); //没有获取到就使用默认的

        //如果请求链接携带语言参数
        if (!StringUtils.isEmpty(language)) {
            //分割请求参数
            String[] split = language.split("_");
            //国家，地区
            locale = new Locale(split[0], split[1]);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
