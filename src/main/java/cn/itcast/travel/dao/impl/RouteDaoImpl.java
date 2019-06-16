package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author junhi
 * @Date 2019/6/3 20:14
 */
public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
//        String sql = "select count(1) from tab_route where cid = ?";
        //1.定义sql模板
        String sql = "select count(1) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        ArrayList<Object> params = new ArrayList<>();
        //2.判断参数是否有值
        if(cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }

        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//        String sql = "select * from tab_route where cid = ? and rname = ? limit ?, ?";
        //1.定义sql模板
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        ArrayList<Object> params = new ArrayList<>();
        //2.判断参数是否有值
        if(cid != 0){
            sb.append(" and cid = ? ");
            //添加？对应的值
            params.add(cid);
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }

        //分页条件
        sb.append(" limit ?, ? ");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();

        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    @Override
    public Route findOne(Integer rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
