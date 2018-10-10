package cn.baby.happyball.constant;

import okhttp3.MediaType;

/**
 * @author DRH
 */
public class HttpConstant {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String IMGURL = "http://leqiubaobei.staraise.com.cn";

    public static final String URL = "http://leqiubaobei.staraise.com.cn/index.php";
    /** 班级分类 **/
    public static final String CLASSES = "/Api/Video/classes";
    /** 课程列表 **/
    public static final String LESSON = "/Api/Video/lesson";
    /** 集数选择 **/
    public static final String MOBILECODE = "/Api/Auth/sendMobileCode";
}
