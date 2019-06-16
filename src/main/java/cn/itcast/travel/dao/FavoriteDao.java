package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author junhi
 * @date 2019/6/16 14:37
 */
public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     *
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 添加收藏
     *
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);

    /**
     * 根据rid 查询收藏次数
     *
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 通过用户的uid 查询收藏的rid，返回一个List<Route>
     * @param uid
     * @return
     */
    List<Route> findRouteByUid(int uid);
}
