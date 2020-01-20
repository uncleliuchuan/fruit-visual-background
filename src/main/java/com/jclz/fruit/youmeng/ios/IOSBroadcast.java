package com.jclz.fruit.youmeng.ios;


import com.jclz.fruit.youmeng.IOSNotification;

public class IOSBroadcast extends IOSNotification {

    public IOSBroadcast(String appkey, String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "broadcast");

    }
}
