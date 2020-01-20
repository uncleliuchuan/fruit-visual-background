package com.jclz.fruit.config;

public class AlipayConfig {
    // 1.商户appid
//    public static String APPID = "2019053165374817";//酷玩攻略
    public static String APPID = "2021001106636933";//沙箱测试


    //2.私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCmCI6DscbTPL6OM+i9otDiBr1fdaumgPRKBuRcYb+q3B9AjIHVvhkK+Qi0ZWf1DmZA2OfUCgO+lG/7z2YrO+xWmaycKOn42F1l1IEH4U5HEeTns0Sr4WWMY497sc3B7mpoiC+6vuc7j612hRLgh6SeaY+mXeOZLdLhs82yeS2HdJqej70keHCLfyTh42Ft7zbfTZ/lAh3UyrMWiWP9R4TDCYfx5ITDDDw5w5/RU2FAq1D6eKrN9mVZ5cE4dFz2DOAeyrlFhRASPL1YwfoXRJ1rKlvMHj+d0oMH5JkyDBR87byr0mshz9/ZnNkpOyNDANXUHS0OZFl5z5Kf0v+GhhfXAgMBAAECggEAXG9LGFpy/MtGs1iG7Hymmg9ygqC6LXqBhm0L2bq5wF/ILmGLLvokaw12ISiLlcysdNHNTyk8KG/vYFt43/z+eMAZbt8AU3/xj22dYEu5DqwRqkUNqDtWE7CiAxVcHemj6ApZuDfr+CbnRaKUfdhGd8MBLHf87Bys2OcHT6NTC6OfxHp9qo5L5tXbtMrVvgMT58IfIM/1affOmtWhyX+J2+7D0YRePDQuOhn9R0MCPj6KcS0klYw7aRq34D2T7nY7p3Ouros6neNH+B38k4i+Pe3pKON5nh6Se+uCVWunr1HpPgpgPL1KLIQLpbYF9HYMRNn03Ldjq7eowJ5aNENrsQKBgQD3c5g0fOc4L0e4RoMcSy9cEokmVdby58YGI7/crW1wGMPaPIMS9rQ3y89dFy/zTVnY9CkVHgBBD6ELEnLPRW3bZzROrRb76l5BzONW4y8MpUrLFO7gVIXhkJBSYJmXjKqhs3i6CZvL3hRfX/UntKD0VYubfdFuPvuqQGYAk0HKtQKBgQCrxOsyrrI41+rJlWTwcSUS3nZurWM6H7XLFhePfC+oOThAPKCUab8gjJmnxLP8EM9ElRyBz+T1ez3tQzK1h1l/zQUwsdDgGa7CFE+KVzoi8pvV4KW/yueeWO8rLlu7r6+lG/Cuuq2jOwQPr6NGYCLyBMpjrD6p0LPagaTayJFT2wKBgQCbuiejgKeS+E5ObjG88KQRcajWc1ce7zX0TzNDkfE5hpxbD5H9sTyhkKYjk6AdoiRVIPNp0sOeLlPPQAD46FzGoyCMEupQB6LiDitUVw0ZDD0RqU7b5tWKqwvoi9qdQW1w7h2gFsPxfQWdMCp8nnRc0ClX5cqTcxe2gWiwG/xfsQKBgDRl5bmYQRXGCAFmaVxPp5eR9V7d1Z8tfS9uldFXeNJAYkB2PiYH/B3ZxiW/g4gpOJEbS47t9FJOPh3ameV2XH7389Ve6PDz5LOfroPtB/cROyiLVwSiyPUs/AiYI7OlbFdZppomqEFbB2ohkhUtzns1ques65Cx8at+UOPMu0hxAoGAfkNnt26UDgNrDLj6gx3R1eNoh2vDAwBL8yGrE1XnAalLUcYhzb065Qp3HLKKn1V4TliybaLUxqW8VPrMPB+j0UvHeaQlrKz8bVdFwZ301jcAkbT/tW/caZsciXiSPPrWGJwstWY7cvONBgoSb/oavvMVypzbVO3jvAtNxdWPVUA=";

    // 3.支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1KwP4fNtxZspOTUkhgn1tmTx9BM4BqaMI/rPpNnOp1YouQSVYTIuLEc2obG5ltxwIyBz2yZI+gxSqA4867pQMDIQYuEwIddK9hrtWc5OlbXq+jTtFa0wbS7xmdJetYQbXoT0UtRUn2wZG4pPgDdVEBRTQPeHT57R7uZq3pa0d+eRNQq8EYA1P/C2BRJzVZXFoBqwIBLwfRoI1PipOhsjIHOnEHRjgkNlxhyXbKphehbGgPc2IEMPXBYtog7fASKlJwrBMEvZ5LaF3yAhALnNsp635MjSliM9dPX+AJtx4BxcpjE9YZ+n7c3UJDnHYUFmdUN1LZfNzkE70ildCDJzEQIDAQAB";

    // 4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://39.104.138.184:9000/alipay/notify_url";

    //5.页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://39.104.138.184:9000/alipay/return_url";

    // 6.请求支付宝的网关地址
//    public static String URL = "https://openapi.alipay.com/gateway.do";//正式环境
    public static String URL = "https://openapi.alipay.com/gateway.do";//沙箱环境

    // 7.编码
    public static String CHARSET = "UTF-8";

    // 8.返回格式
    public static String FORMAT = "json";

    // 9.加密类型
    public static String SIGNTYPE = "RSA2";

    public static String APP_PRIVATE_KEY;

    public static String GATE;

    public static String PUBLIC_KEY;

}
