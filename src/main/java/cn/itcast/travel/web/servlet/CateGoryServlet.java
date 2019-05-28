package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @Author junhi
 * @Date 2019/5/26 0026 10:43
 **/

@WebServlet("/category/*")
public class CateGoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    /**
     * 查询所有
     * @param request
     * @param response
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1、调用service查询所有
        List<Category> cs = service.findAll();
        // 2、序列化json返回
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf8");
        mapper.writeValue(response.getOutputStream(), cs);*/
        writeValue(cs, response);
    }

    public void find(HttpServletRequest request, HttpServletResponse response){
        System.out.println("CateGoryServlet的find方法");

    }
}
