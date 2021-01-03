package com.example.studyroom.util;

public class TextUtil {
    public static String Text2Html(String str) {
        if (str == null) {
            return "";
        }else if (str.length() == 0) {
            return "";
        }
        str = str.replaceAll("\n", "<br />");
        str = str.replaceAll("\r", "<br />");
        return str;
    }

    public static String Html2Text(String str) {
        if (str == null) {
            return "";
        }else if (str.length() == 0) {
            return "";
        }
        str = str.replaceAll("<br />", "\n");
        str = str.replaceAll("<br />", "\r");
        return str;
    }
}
