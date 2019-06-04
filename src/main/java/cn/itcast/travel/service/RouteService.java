package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * 线路Service
 * @Author junhi
 * @Date 2019/6/3 15:58
 */
public interface RouteService {

    /**
     * 根据类别进行分页展示
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize);
}
