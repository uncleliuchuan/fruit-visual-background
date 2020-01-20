package com.jclz.fruit.utils;

import com.jclz.fruit.youmeng.AndroidNotification;
import com.jclz.fruit.youmeng.PushClient;
import com.jclz.fruit.youmeng.android.AndroidBroadcast;
import com.jclz.fruit.youmeng.android.AndroidCustomizedcast;
import com.jclz.fruit.youmeng.android.AndroidFilecast;
import com.jclz.fruit.youmeng.android.AndroidGroupcast;
import com.jclz.fruit.youmeng.ios.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 友盟工具类
 */
public class YouMengUtil {
    private static final String appkey = "5e16d2d6cb23d231c9000165";
    private static final String appMasterSecret = "vixlneqjlx3hsprdnmx371okbtrr4wsx";
    private static final String APPKEY_IOS = "5e16d60a4ca357145b000743";
    private static final String APP_MASTERSECRET_IOS = "nobfygcjjjt4gs8li8iwqepvcom662xl";
    private String timestamp = null;
    private static final PushClient client = new PushClient();
   /* public YouMengUtil(String key,String secret){
        try{
            appkey = key;
            appMasterSecret = secret;
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }*/

    /**
     * 安卓广播
     *
     * @throws Exception
     */
    public static boolean sendAndroidBroadcast(String content, String type, String contentExtral) throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
        broadcast.setTicker("Android broadcast ticker");
        broadcast.setTitle("鲜果优选");
        broadcast.setText(content);
        broadcast.goAppAfterOpen();
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        broadcast.setProductionMode();
        // Set customized fields
        broadcast.setExtraField("type", type);
        broadcast.setExtraField("content", contentExtral);
        return client.send(broadcast);
    }

    /**
     * 安卓组播
     *
     * @throws Exception
     */
    public static void sendAndroidGroupcast() throws Exception {
        AndroidGroupcast groupcast = new AndroidGroupcast(appkey, appMasterSecret);
        /*  TODO
         *  Construct the filter condition:
         *  "where":
         *	{
         *		"and":
         *		[
         *			{"tag":"test"},
         *			{"tag":"Test"}
         *		]
         *	}
         */
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        JSONObject testTag = new JSONObject();
        JSONObject TestTag = new JSONObject();
        testTag.put("tag", "test");
        TestTag.put("tag", "Test");
        tagArray.put(testTag);
        tagArray.put(TestTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());
        groupcast.setFilter(filterJson);
        groupcast.setTicker("Android groupcast ticker");
        groupcast.setTitle("鲜果优选");
        groupcast.setText("Android groupcast text");
        groupcast.goAppAfterOpen();
        groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        groupcast.setProductionMode();
        client.send(groupcast);
    }

    /**
     * 安卓自定义播(customizedcast, 且file_id不为空)默认每小时可推送300次
     * 根据别名单播 别名对应用户id
     *
     * @throws Exception
     * @Param receivedId 接收消息的用户id
     * @Param content 单播消息内容
     */
    public static boolean sendAndroidCustomizedcast(Integer receivedId, String content, String type) throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
        // TODO Set your alias here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        JSONObject result = new JSONObject();
        JSONObject custom = new JSONObject();
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        object.put("sound", "todayTask");
        object.put("messageTitle", "推送标题");
        object.put("pushContent", content);//推送内容
        object.put("type", type);//TODO
        array.put(object);
        result.put("arr0", array);
        custom.put("result", result);
        customizedcast.setCustomField(custom);
        customizedcast.setAlias(receivedId.toString(), "JingChengUnited");
        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("鲜果优选·推送");
        customizedcast.setText(content);
        customizedcast.goAppAfterOpen();
        //TODO
        System.out.println(customizedcast.getPostBody());
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        return client.send(customizedcast);
    }

    /**
     * 安卓自定义文件播
     *
     * @throws Exception
     */
    public static void sendAndroidCustomizedcastFile() throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
        // TODO Set your alias here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
        customizedcast.setFileId(fileId, "alias_type");
        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("中文的title");
        customizedcast.setText("Android customizedcast text");
        customizedcast.goAppAfterOpen();
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        client.send(customizedcast);
    }

    /**
     * 安卓文件播
     *
     * @throws Exception
     */
    public static void sendAndroidFilecast() throws Exception {
        AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
        // TODO upload your device tokens, and use '\n' to split them if there are multiple tokens
        String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
        filecast.setFileId(fileId);
        filecast.setTicker("Android filecast ticker");
        filecast.setTitle("中文的title");
        filecast.setText("Android filecast text");
        filecast.goAppAfterOpen();
        filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        client.send(filecast);
    }

    /**
     * ios播
     *
     * @throws Exception
     * @Param send 返回广播发送结果
     */
    public static boolean sendIOSBroadcast(String content, String type, String contentExtral) throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast(APPKEY_IOS, APP_MASTERSECRET_IOS);
        broadcast.setAlert(content);
        broadcast.setBadge(1);
        broadcast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        //broadcast.setTestMode();
        broadcast.setProductionMode();
        // Set customized fields
        broadcast.setCustomizedField("type", type);
        broadcast.setCustomizedField("content", contentExtral);
        return client.send(broadcast);
    }

    /**
     * ios单播   向指定的设备发送消息
     *
     * @throws Exception
     * @Param deviceToken 单播对象设备token
     */
    public static void sendIOSUnicast(String deviceToken, String alertContent) throws Exception {
        IOSUnicast unicast = new IOSUnicast(APPKEY_IOS, APP_MASTERSECRET_IOS);
        // TODO Set your device token
        unicast.setDeviceToken(deviceToken);
        unicast.setAlert(alertContent);
        unicast.setBadge(0);
        unicast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        unicast.setTestMode();
        // Set customized fields
        unicast.setCustomizedField("test", "helloworld");
        client.send(unicast);
    }

    /**
     * ios组播
     *
     * @throws Exception
     */
    public static void sendIOSGroupcast() throws Exception {
        IOSGroupcast groupcast = new IOSGroupcast(APPKEY_IOS, APP_MASTERSECRET_IOS);
        /*  TODO
         *  Construct the filter condition:
         *  "where":
         *	{
         *		"and":
         *		[
         *			{"tag":"iostest"}
         *		]
         *	}
         */
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        JSONObject testTag = new JSONObject();
        testTag.put("tag", "iostest");
        tagArray.put(testTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());
        // Set filter condition into rootJson
        groupcast.setFilter(filterJson);
        groupcast.setAlert("IOS 组播测试");
        groupcast.setBadge(0);
        groupcast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        groupcast.setTestMode();
        client.send(groupcast);
    }

    /**
     * @param alias 用户别名
     *              别名类型 JingChengUnited
     * @param alert 通知内容
     * @param type  通知类型
     * @throws Exception
     */
    public static boolean sendIOSCustomizedcast(String alias, String alert, String type) throws Exception {
        IOSCustomizedcast customizedcast = new IOSCustomizedcast(APPKEY_IOS, APP_MASTERSECRET_IOS);
        // TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        customizedcast.setAlias(alias, "JingChengUnited");
        customizedcast.setAlert(alert);
        customizedcast.setBadge(0);
        customizedcast.setSound("default");
        customizedcast.setCustomizedField("test", type);
        // TODO set 'production_mode' to 'true' if your app is under production mode
        customizedcast.setTestMode();
        return client.send(customizedcast);
    }

    /**
     * ios文件播
     *
     * @throws Exception
     */
    public static void sendIOSFilecast() throws Exception {
        IOSFilecast filecast = new IOSFilecast(APPKEY_IOS, APP_MASTERSECRET_IOS);
        // TODO upload your device tokens, and use '\n' to split them if there are multiple tokens
        String fileId = client.uploadContents(APPKEY_IOS, APP_MASTERSECRET_IOS, "aa" + "\n" + "bb");
        filecast.setFileId(fileId);
        filecast.setAlert("IOS 文件播测试");
        filecast.setBadge(0);
        filecast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        filecast.setTestMode();
        client.send(filecast);
    }
}
