package com.jclz.fruit.youmeng;


import com.jclz.fruit.youmeng.ios.IOSBroadcast;

public class IosDemo {
    private String appkey = "5d1335c2570df30123000729";
    private String appMasterSecret = "zeixg7sryol2j9as6bv7objdqb3utw3s";
    private String timestamp = null;
    private PushClient client = new PushClient();

    public IosDemo(String key, String secret) {
        try {
            appkey = key;
            appMasterSecret = secret;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void sendIOSBroadcast() throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast(appkey, appMasterSecret);
//		broadcast.setTicker( "Android broadcast ticker");
//		broadcast.setTitle(  "中文的title");
//		broadcast.setText(   "Android broadcast text");
//		broadcast.goAppAfterOpen();
    }
}
