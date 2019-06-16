package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author junhi
 * @date 2019/6/16 14:36
 */
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        //如果对象有值，则为true，反之，则为false
        return favorite != null;
    }

    @Override
    public int findCountByRid(int rid) {
        return favoriteDao.findCountByRid(rid);
    }

    @Override
    public void addFavorite(String rid, int uid) {
        favoriteDao.addFavorite(Integer.parseInt(rid), uid);
    }

    @Override
    public List<Route> findRouteByUid(int uid) {

        return favoriteDao.findRouteByUid(uid);
    }

    @Override
    public PageBean<Route> favoritePageQuery(int uid, int currentPage, int pageSize) {
        //封装PageBean
        PageBean<Route> pageBean = new PageBean<>();

        //用户收藏的总记录数
        int totalCount = favoriteDao.findRouteByUid(uid).size();
        pageBean.setTotalCount(totalCount);

        //总页数
        int totalPage = 0;
        if (totalCount > pageSize) {
            totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        }else {
            totalPage = 1;
        }
        pageBean.setTotalPage(totalPage);

        //设置当前页显示的数据集合
        //开始的记录数
        int start = (currentPage - 1) * pageSize;
        List<Route> list = favoriteDao.findByPage(cid, start, pageSize, rname);
        pageBean.setList(list);

        //设置当前页码
        pageBean.setCurrentPage(currentPage);

        return pageBean;
    }
}
