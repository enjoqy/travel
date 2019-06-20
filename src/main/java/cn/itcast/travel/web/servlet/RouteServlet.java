package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author junhi
 * @Date 2019/6/3 15:46
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接收rname路线名称
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        if (rname.equals("null")) {
            rname = "";
        }

        //解决乱码
//        if(rname != null && rname.length() >0){
//            //对url中文进行解码
//            rname = URLDecoder.decode(rname, "utf-8");
//        }else {
//            rname = null;
//        }

        //2、处理参数，cid：类别id
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        //当前页码，如果不传递，默认显示第一页
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        //每页显示的条数，如果不传递，默认显示每页5条
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        //3、调用service查询PageBean对象
        PageBean<Route> pageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);

        //4、将PageBean对象序列化为json，返回
        writeValue(pageBean, response);

    }

    /**
     * 根据id查询一个旅游线路的详细信息
     *
     * @param request
     * @param response
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1、接收id
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findOne(rid);
        //3.转为json返回客户端
        writeValue(route, response);
    }

    /**
     * 判断当前用户是否收藏过该线路
     *
     * @param request
     * @param response
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1、获取线路id
        String rid = request.getParameter("rid");
        //2、获取当前登录用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;  //用户id
        if (user == null) {
            //用户尚未登录
            uid = 0;
        } else {
            //用户已经登录
            uid = user.getUid();
        }

        //3、调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4、写会客户端
        writeValue(flag, response);
    }

    /**
     * 添加收藏
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1、获取线路rid
        String rid = request.getParameter("rid");
        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        //用户id
        int uid;
        if (user == null) {
            //用户尚未登录
            return;
        } else {
            //用户已经登录
            uid = user.getUid();
        }

        //3.调用service添加
        favoriteService.addFavorite(rid, uid);
    }

    /**
     * 通过用户的uid 查询收藏的rid，返回一个List<Route>
     *
     * @param request
     * @param response
     */
    public void findFavoriteByUid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取用的uid
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();
        //2.通过用户的uid查询收藏的route
        List<Route> routeList = favoriteService.findRouteByUid(uid);

        //3.将数据传回客户端
        writeValue(routeList, response);
    }

    /**
     * 收藏路线，分页查询
     * 通过用户的uid 查询收藏的route的数量，进行分页处理
     *
     * @param request
     * @param response
     */
    public void favoritePageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1、接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        //这个标志是判断查询用户收藏，还是查询rank排行榜
        String flag = request.getParameter("flag");
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if(user != null){
            uid = user.getUid();
        }

        //当前页码，如果不传递，默认显示第一页
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        //每页显示的条数，如果不传递，默认显示每页8条
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 8;
        }

        //flag这个标志是判断查询用户收藏，还是查询rank排行榜
        //2、调用service查询PageBean对象
        PageBean<Route> pageBean = favoriteService.favoritePageQuery(uid, currentPage, pageSize, flag);

        //3、将PageBean对象序列化为json，返回
        writeValue(pageBean, response);
    }

}
