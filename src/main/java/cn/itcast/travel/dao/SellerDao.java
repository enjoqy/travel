package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author junhi
 * @date 2019/6/15 14:35
 */
public interface SellerDao {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Seller findById(Integer id);
}
