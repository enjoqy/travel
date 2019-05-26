package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author junhi
 * @Date 2019/5/25 16:08
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("baseServlet的service方法被执行了。。。");

        // 完成方法的分发
        //1、获取请求的路径
        String uri = req.getRequestURI();
        // /travel/user/add
        System.out.println("请求uri: " + uri);
        //2、获取方法的名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println(methodName);

        //3、获取方法的对象
        try {
            // 此时的this，谁调用我，我就指代谁，，子方法会被子类调用，所以指代子类
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            // 暴力反射，跳过安全检查
//            method.setAccessible(true);

            // 调用子类中的方法，完成方法的分发
            //4、执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }



    }
}
