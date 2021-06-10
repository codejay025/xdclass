package net_xdclass.online_xdclass.config;

/**
 * 缓存key管理类，通过key区别存储在缓存中的数据
 */
public class CacheKeyManager {
    /**
     * 首页轮播图缓存类
     */
    public static final String INDEX_BANNER_KEY = "index:banner:list";

    /**
     * 视频列表缓存
     */
    public static final String INDEX_VIDEO_KEY = "index:video:list";

    /**
     * 视频详情列表
     */
    public static final String VIDEO_DETAIL = "video:detail:%s";
}
