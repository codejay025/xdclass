package net_xdclass.online_xdclass.service.impl;

import net_xdclass.online_xdclass.exception.XDException;
import net_xdclass.online_xdclass.mapper.EpisodeMapper;
import net_xdclass.online_xdclass.mapper.PlayRecordMapper;
import net_xdclass.online_xdclass.mapper.VideoMapper;
import net_xdclass.online_xdclass.mapper.VideoOrderMapper;
import net_xdclass.online_xdclass.model.entity.Episode;
import net_xdclass.online_xdclass.model.entity.PlayRecord;
import net_xdclass.online_xdclass.model.entity.Video;
import net_xdclass.online_xdclass.model.entity.VideoOrder;
import net_xdclass.online_xdclass.service.VideoOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private EpisodeMapper episodeMapper;

    @Autowired
    private PlayRecordMapper playRecordMapper;

    /**
     * 下单操作，如果已经下单，也就是订单不为空，则返回的是0.说明买过了
     * 如果为空，说明要新建一个订单，先通过videoId查询出视频信息
     * @param userId 需要知道是哪一个用户下单
     * @param videoId 需要知道是下单的哪一个视频
     * @return
     *
     */
    /*
    *  先查询出来这个订单是不是这个用户已经买了,已经买了则返回0
    *   没买的话，则创建新的订单
    *   订单中的数据由VideoId查询出来，返回的是一个Video,由Video提供给VideoOrder相关数据
    *   最后将将结果插入到Mapper里，返回受影响的行数
    *
    * */
    @Transactional
    public int saveOrder(int userId, int videoId) {

        // 判断是否购买，已经支付成功后就不能购买，以为是视频课程，支付状态默认是1
        VideoOrder videoOrder = videoOrderMapper.findByUserIdAndOrderIdAndState(userId, videoId, 1);

        // 为空说明已经购买了
        if (videoOrder != null) {
            return 0;
        }

        /**
         * 根据videoId查询出视频信息,
         * 通过前端传入的用户和videoId查询出视频
         * 然后将视频的信息属性加入视频订单
          */

        Video video = videoMapper.findById(videoId);

        // 创建一个新的视频订单
        VideoOrder newVideoOrder = new VideoOrder();

        // 设置订单相关属性
        newVideoOrder.setCreateTime(new Date());
        newVideoOrder.setOutTradeNo(UUID.randomUUID().toString());
        //设置订单状态，默认是1
        newVideoOrder.setState(1);
        newVideoOrder.setTotalFee(video.getPrice());
        newVideoOrder.setUserId(userId);
        newVideoOrder.setVideoId(videoId);
        newVideoOrder.setVideoImg(video.getCoverImg());
        newVideoOrder.setVideoTitle(video.getTitle());

        // 返回的是受影响的行数
        int rows = videoOrderMapper.saveOrder(newVideoOrder);

        // 如果已经下单了,需要找到相关的视频的集的信息
        if (rows == 1) {
            Episode episode = episodeMapper.findFirstEpisodeByVideoId(videoId);

            if (episode == null) {
                throw new XDException(-1, "集信息不存在，请运营工程师查证！");
            }
            // 拿到集信息，将集信息的相关信息封装到播放记录表里面
            PlayRecord playRecord = new PlayRecord();
            playRecord.setCreateTime(new Date());
            playRecord.setCurrentNum(episode.getNum());
            playRecord.setEpisodeId(episode.getId());
            playRecord.setUserId(userId);
            playRecord.setVideoId(videoId);

            playRecordMapper.saveRecord(playRecord);
        }
        return rows;
    }

    @Override
    public List<VideoOrder> listOrderByUserId(Integer userId) {

        return videoOrderMapper.listOrderByUserId(userId);
    }
}
