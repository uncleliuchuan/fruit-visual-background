package com.jclz.fruit.youmeng.android;


import com.jclz.fruit.youmeng.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification {

    public AndroidBroadcast(String appkey, String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "broadcast");
    }
}
