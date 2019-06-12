package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author junhi
 * @Date 2019/5/25 16:10
 */
@WebServlet("/user/*")  //  /user/add  /user/find
public class UserServlet extends BaseServlet {

    // 声明UserService业务对象
    private UserService service = new UserServiceImpl();

    /**
     * 用户注册
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证校验
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service完成注册
        //UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("用户名已存在!");
        }

        //将info对象序列化为json
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);*/
        String json = writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3、调用service查询
        //UserService service = new UserServiceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();

        //4、判断用户对象是否是null
        if (u == null) {
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }

        //附加，判断验证码是否正确
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        /**
         *  为了执行忽略大小写的比较，可以调用equalsIgnoreCase( )方法
         */
        if(check == null || !checkcode_server.equalsIgnoreCase(check)){
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            writeValue(info, response);
            return;
        }

        //5、判断用户是否激活
        String status = "Y";
        if (u != null && !status.equals(u.getStatus())) {
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，11111！");
        }

        //6、判断登录成功
        if (u != null && status.equals(u.getStatus())) {
            //登录成功
            info.setFlag(true);
            // 设置session
            request.getSession().setAttribute("user", u);
        }

        //7、相应数据
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);*/
        writeValue(info, response);
    }

    /**
     * 查找用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        System.out.println(user);
        //将user写回客户端

       /* ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);*/
       writeValue(user, response);
    }

    /**
     * 用户退出
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();

        //2.跳转登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 用户激活
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、获取激活码
        String code = request.getParameter("code");
        System.out.println(code);
        if (code != null) {
            //2、调用service完成激活
            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            // 3、判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='" + request.getContextPath() + "/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员！";

            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);

        }
    }
}
