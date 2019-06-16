package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 根据toute的rid查询图片
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}
