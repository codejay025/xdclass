package net_xdclass.online_xdclass.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net_xdclass.online_xdclass.model.entity.VideoOrder;
import net_xdclass.online_xdclass.model.request.VideoOrderRequest;
import net_xdclass.online_xdclass.service.VideoOrderService;
import net_xdclass.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pri/order")
public class VideoOrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 下单接口操作，就是插入
     *
     * @param videoOrderRequest 前端用户输入的信息封装在videoOrderRequest中
     * @param request           哪一个用户下的单，从request域中取出
     * @return
     */
    @RequestMapping("/save")
    public JsonData saveOrder(@RequestBody VideoOrderRequest videoOrderRequest, HttpServletRequest request) {

        // 获取用户id
        Integer userId = (Integer) request.getAttribute("user_id");
        int rows = videoOrderService.saveOrder(userId, videoOrderRequest.getVideoId());

        return rows == 0 ? JsonData.buildError("下单失败！") : JsonData.buildSuccess();
    }

    /**
     * 开发我的 视频订单列表
     * @param request
     * @return
     */
    @GetMapping("/list")
    public JsonData listOrder(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");
        List<VideoOrder> listOrder = videoOrderService.listOrderByUserId(userId);

        return JsonData.buildSuccess(listOrder);
    }
}
