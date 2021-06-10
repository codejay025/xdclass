package net_xdclass.online_xdclass.mapper;

import net_xdclass.online_xdclass.model.entity.PlayRecord;

public interface PlayRecordMapper {

    /**
     * 插入播放记录
     */
    int saveRecord(PlayRecord playRecord);
}
