package net_xdclass.online_xdclass.mapper;

import net_xdclass.online_xdclass.model.entity.Episode;
import org.apache.ibatis.annotations.Param;

public interface EpisodeMapper {

    /**
     * 通过找哪一个视频的信息的第几集的信息，查找哪个视频的第几集
     * @return
     */
    Episode findFirstEpisodeByVideoId(@Param("video_id") int videoId);
}
