package net_xdclass.online_xdclass.mapper;

import net_xdclass.online_xdclass.model.entity.Video;
import net_xdclass.online_xdclass.model.entity.VideoBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {

    /**
     * 查询视频列表
     * @return
     */
    List<Video> listVideo();

    /**
     * 视频轮播图列表
     * @return
     */
    List<VideoBanner> listVideoBanner();

    /**
     * 视频详情信息查询
     * @param videoId
     * @return
     */
    Video findDetilById(@Param("video_id") int videoId);

    /**
     *
     * @param videoId
     * @return
     */
    Video findById(@Param("video_id") int videoId);
}
