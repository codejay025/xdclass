package net_xdclass.online_xdclass.service;

import net_xdclass.online_xdclass.model.entity.Video;
import net_xdclass.online_xdclass.model.entity.VideoBanner;

import java.util.List;

public interface VideoService {
    /**
     * 获取视频列表
     * @return
     */
    List<Video> listVideo();

    /**
     * 视频轮播图接口开发
     * @return
     */

    List<VideoBanner> listVideoBanner();

    /**
     * 查询视频详情信息
     * @param videoId
     * @return
     */

    Video findDetatilById(int videoId);


}
