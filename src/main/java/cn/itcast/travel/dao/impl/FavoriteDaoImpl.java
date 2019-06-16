package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author junhi
 * @date 2019/6/16 14:39
 */
public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ? ";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?, ?, ?) ";
        template.update(sql, rid, new Date(), uid);
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ? ";
        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public List<Route> findRouteByUid(int uid) {
        String sql = "select * from tab_route, tab_favorite where uid = ? and tab_route.rid = tab_favorite.rid ";
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid);
    }
}
