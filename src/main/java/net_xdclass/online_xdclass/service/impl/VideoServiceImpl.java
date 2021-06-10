package net_xdclass.online_xdclass.service.impl;

import net_xdclass.online_xdclass.config.CacheKeyManager;
import net_xdclass.online_xdclass.model.entity.Video;
import net_xdclass.online_xdclass.model.entity.VideoBanner;
import net_xdclass.online_xdclass.mapper.VideoMapper;
import net_xdclass.online_xdclass.service.VideoService;
import net_xdclass.online_xdclass.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    /**
     * 查询视频列表
     * 将视频列表加入缓存
     * @return
     */

    public List<Video> listVideo() {

        try {
            Object cacheObj = baseCache.getTenMinCache().get(CacheKeyManager.INDEX_VIDEO_KEY,()->{
                List<Video> videoList = videoMapper.listVideo();
                return videoList;
            });

            // 判断缓存类型
            if (cacheObj instanceof List) {
                List<Video> videoList = (List<Video>) cacheObj;
                return videoList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询视频轮播图信息
     * @return
     */
    @Override
    public List<VideoBanner> listVideoBanner() {

        try {
            // 先从缓存里面找，找不到的话，再从回调函数数据库中寻找
            Object cacheObj = baseCache.getTenMinCache().get(CacheKeyManager.INDEX_BANNER_KEY, () -> {
                List<VideoBanner> bannerList = videoMapper.listVideoBanner();
                return bannerList;
            });

            // 判断缓存是不是List类型
            if (cacheObj instanceof List) {
                List<VideoBanner> bannerList = (List<VideoBanner>) cacheObj;
                return bannerList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询视频详情信息
     * @param videoId
     * @return
     */
    @Override
    public Video findDetatilById(int videoId) {

        /*
          String s = String.format("小名，%s","你好");
          System.out.print(s);
          输出的结果是 小名，你好
         */
        // 带参数的缓存处理办法
        String videoCacheKey = String.format(CacheKeyManager.VIDEO_DETAIL, videoId);
        try {
            // 总体来说，key是标记缓存哪一个数据的，然后()->{}，回调函数获取数据存储到缓存中
            Object cacheObj = baseCache.getOneHourCache().get(videoCacheKey, () -> {
                Video video = videoMapper.findDetilById(videoId);
                return video;
            });

            // 获取到的结果对象，判断是否是想要的结果类型
            if (cacheObj instanceof Video) {
                Video video = (Video)cacheObj;
                return video;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
