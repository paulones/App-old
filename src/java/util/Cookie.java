/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ipti004
 */
public class Cookie { 

    public static String getCookie(String name) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        String value = null;
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().trim().equalsIgnoreCase(name)) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }

    public static void apagarCookies() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setValue(null);
                cookies[i].setPath("/");
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
        }
    }

    public static void apagarCookie(String name) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name)) {
                    cookies[i].setValue(null);
                    cookies[i].setPath("/");
                    cookies[i].setMaxAge(0);
                    response.addCookie(cookies[i]);
                }
            }
        }
    }

    public static void addCookie(String key, String value, Integer seconds) {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(seconds);
        response.addCookie(cookie);
    }
}
