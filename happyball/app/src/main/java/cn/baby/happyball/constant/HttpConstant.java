package cn.baby.happyball.constant;

import okhttp3.MediaType;

/**
 * @author DRH
 */
public class HttpConstant {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String RES_URL = "http://leqiubaobei.staraise.com.cn";

    public static final String URL = "http://leqiubaobei.staraise.com.cn/index.php";
    /** 视频班级分类 **/
    public static final String VIDEO_CLASSES = "/Api/Video/classes";
    /** 视频课程列表 **/
    public static final String VIDEO_LESSON = "/Api/Video/lesson";
    /** 视频集数选择 **/
    public static final String VIDEO_EPISODE = "/Api/Video/episode";
    /** 单一舞蹈动作 **/
    public static final String VIDEO_SINGLEDANCE = "/Api/Video/singledance";
    /** 知识点 **/
    public static final String VIDEO_QUESTION = "/Api/Video/question";
    /** 视频班级分类 **/
    public static final String AUDIO_CLASSES = "/Api/Video/classes";
    /** 视频课程列表 **/
    public static final String AUDIO_LESSON = "/Api/Video/lesson";
    /** 视频集数选择 **/
    public static final String AUDIO_EPISODE = "/Api/Video/episode";
}
