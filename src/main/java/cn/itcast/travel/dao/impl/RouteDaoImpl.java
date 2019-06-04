package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author junhi
 * @Date 2019/6/3 20:14
 */
public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findById(int cid) {
        String sql = "select count(1) from tab_route where cid = ?";
        return template.queryForObject(sql, Integer.class, cid);
    }

    @Override
    public List<Route> findByPage(int cId, int start, int pageSize) {
        String sql = "select * from tab_route where cid = ? limit ?, ?";
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cId, start, pageSize);
    }
}
