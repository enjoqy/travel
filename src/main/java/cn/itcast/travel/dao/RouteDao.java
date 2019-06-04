package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @Author junhi
 * @Date 2019/6/3 20:10
 */
public interface RouteDao {

    /**
     * 根据cId查询总记录数
     * @param cId
     * @return
     */
    int findById(int cId);

    /**
     * 根据cId, start, pageSize查询当前页的数据集合
     * @param cId
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int cId, int start, int pageSize);

}
