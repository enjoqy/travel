package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author junhi
 * @date 2019/6/16 13:53
 */
public interface FavoriteService {

    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(String rid, int uid);

    /**
     * 根据rid 查询收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(String rid, int uid);

    /**
     * 根据用户的uid查询收藏的route
     * @param uid
     * @return
     */
    List<Route> findRouteByUid(int uid);

    /**
     * * 收藏路线，分页查询
     * 通过用户的uid 查询收藏的route的数量，进行分页处理
     * @param uid
     * @param currentPage
     * @param pageSize
     */
    PageBean<Route> favoritePageQuery(int uid, int currentPage, int pageSize);
}
