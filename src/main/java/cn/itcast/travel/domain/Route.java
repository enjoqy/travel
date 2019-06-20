package cn.itcast.travel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 旅游线路商品实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Route implements Serializable {

    /**
     * 线路id，必输
     */
    private int rid;
    /**
     * 线路名称，必输
     */
    private String rname;
    /**
     * 价格，必输
     */
    private double price;
    /**
     * 线路介绍
     */
    private String routeIntroduce;
    //是否上架，必输，0代表没有上架，1代表是上架
    private String rflag;
    //上架时间
    private String rdate;
    private String isThemeTour;//是否主题旅游，必输，0代表不是，1代表是
    private int count;//收藏数量
    private int cid;//所属分类，必输
    private String rimage;//缩略图
    private int sid;//所属商家
    private String sourceId;//抓取数据的来源id

    private Category category;//所属分类
    private Seller seller;//所属商家
    private List<RouteImg> routeImgList;//商品详情图片列表

}
