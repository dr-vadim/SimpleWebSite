package servlets;

import factories.ServiceFactory;
import interfaces.servlets.ServletRequestActions;
import models.User;
import org.json.JSONObject;
import services.Service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

public class MainServlet extends HttpServlet implements ServletRequestActions{
    Service service;
    @Override
    public void init() throws ServletException {
        super.init();
        service = ServiceFactory.getInstance().getService();
    }

    public MainServlet(){
        super();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //Service service = ServiceFactory.getInstance().getService();
        List<User> users = service.getUsersWithAuto();
        req.setAttribute("Title", "Test java site page");
        req.setAttribute("User", users.get(0).getName());
        req.setAttribute("userList", users);

        req.getRequestDispatcher("index.jsp").forward(req,resp);
        //index_jsp index = new index_jsp();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        String[] paths = servletPath.split("/");
        String action = "";
        if(paths.length > 1) {
            action = paths[1].toLowerCase();
            try {
                ServletRequestActions mainServletClass = MainServlet.class.newInstance();
                Method method = MainServlet.class.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(mainServletClass, req, resp);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String headerName = req.getHeader("x-requested-with");
        if(headerName != null) {
            String name = req.getParameter("name");
            int age = 0;
            try {
                age = Integer.valueOf(req.getParameter("age"));
            }catch (NumberFormatException e){
                throw new IllegalArgumentException(e);
            }

            service = ServiceFactory.getInstance().getService();

            User user = service.addUser(new User.Builder().setName(name).setAge(age).build());
            JSONObject js = new JSONObject();
            js.put("name", name);
            js.put("age", age);
            js.put("id", user.getId());
            String textJson = js.toString();

            resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
            resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
            resp.getWriter().write(textJson);       // Write response body.
        }
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String headerName = req.getHeader("x-requested-with");
        if(headerName == null) return;
        service = ServiceFactory.getInstance().getService();

        String name = req.getParameter("name");
        int age = 0, id = 0;
        try {
            age = Integer.valueOf(req.getParameter("age"));
            id = Integer.valueOf(req.getParameter("id"));
        }catch (NumberFormatException e){
            throw new IllegalArgumentException(e);
        }

        boolean result = service.updateUser(id,new User.Builder().setName(name).setAge(age).build());
        if(!result)
            throw new IllegalArgumentException("Cannot update user data with id:"+id);
        JSONObject js = new JSONObject();
        js.put("name", name);
        js.put("age", age);
        js.put("id", id);
        String textJson = js.toString();

        resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        resp.getWriter().write(textJson);       // Write response body.
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String headerName = req.getHeader("x-requested-with");
        if(headerName == null) return;
        service = ServiceFactory.getInstance().getService();
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            throw new IllegalArgumentException(e);
        }
        boolean result = service.deleteUser(id);

        if(!result)
            throw new IllegalArgumentException("Connot delete user with id:"+id);

        JSONObject js = new JSONObject();
        js.put("success", true);
        String textJson = js.toString();

        resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        resp.getWriter().write(textJson);       // Write response body.
    }

    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String headerName = req.getHeader("x-requested-with");
        if(headerName == null) return;
        service = ServiceFactory.getInstance().getService();
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            throw new IllegalArgumentException(e);
        }
        User user = service.getUser(id);
        if(user == null)
            throw new IllegalArgumentException("No such user with id:"+id);

        JSONObject js = new JSONObject();
        js.put("name", user.getName());
        js.put("age", user.getAge());
        js.put("id", user.getId());
        String textJson = js.toString();

        resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        resp.getWriter().write(textJson);       // Write response body.
    }


}
