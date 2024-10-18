package org.mrshoffen.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class JspHelper {
    private static final String JSP_FORMAT = "/WEB-INF/pages/%s.jsp";
    public static String getPath(String jspName){
        return JSP_FORMAT.formatted(jspName);
    }


}
