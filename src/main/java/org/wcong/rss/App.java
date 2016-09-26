package org.wcong.rss;

import org.apache.catalina.startup.Tomcat;

/**
 * Created by wcong on 2016/9/26.
 */
public class App {

    public static void main() {
        Tomcat tomcat = new Tomcat();
        tomcat.addWebapp();
    }

}
