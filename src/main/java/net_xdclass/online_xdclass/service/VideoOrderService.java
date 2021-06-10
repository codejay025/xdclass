package net_xdclass.online_xdclass.service;

import net_xdclass.online_xdclass.model.entity.VideoOrder;

import java.util.List;

/**
 * 订单接口
 */
public interface VideoOrderService {

    /**
     *  下单接口
     * @param userId 需要知道是哪一个用户下单
     * @param videoId 需要知道是下单的哪一个视频
     * @return
     */
    int saveOrder(int userId, int videoId);

    /**
     * 开发视频订单列表，订单是谁的订单啊
     * @param userId
     * @return
     */
    List<VideoOrder> listOrderByUserId(Integer userId);
}
