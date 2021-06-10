package net_xdclass.online_xdclass.mapper;

import net_xdclass.online_xdclass.model.entity.VideoOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoOrderMapper {

    /**
     * 查询用户是否购买过此商品，通过用户id,视频id,支付订单状态，进行查询
     *
     * @param userId
     * @param videoId
     * @param state
     * @return
     */
    VideoOrder findByUserIdAndOrderIdAndState(@Param("user_id") int userId, @Param("video_id") int videoId, @Param("state") int state);

    /**
     * 下单模块
     * @param videoOrder 插入的是订单
     * @return
     */
    int saveOrder(VideoOrder videoOrder);

    /**
     * 开发视频订单接口
     * @param userId
     * @return
     */
    List<VideoOrder> listOrderByUserId(@Param("user_id") Integer userId);
}
