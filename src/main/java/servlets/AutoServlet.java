package servlets;

import factories.ServiceFactory;
import interfaces.servlets.ServletRequestActions;
import jdk.nashorn.internal.codegen.types.Type;
import models.Auto;
import models.User;
import org.json.JSONObject;
import org.springframework.web.util.UriTemplate;
import services.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.URIParameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface PatternUri{
    String value() default "";
}

public class AutoServlet extends HttpServlet implements ServletRequestActions{

    private Service service;
    private String mainUrl = "/auto";

    public AutoServlet() {
        super();
    }

    public void init() throws ServletException {
        super.init();
        service = ServiceFactory.getInstance().getService();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Method[] methods = getClass().getMethods();
        UriTemplate template;
        for(Method method: methods){
            PatternUri patternUri = method.getAnnotation(PatternUri.class);
            if(patternUri != null){
                template = new UriTemplate(patternUri.value());

                if(template.matches(uri)){
                    Map<String, String> machMap = template.match(uri);
                    machMap.forEach((key, value) -> {
                        req.setAttribute(key,value);
                    });
                    try{
                        method.invoke(this,req,resp);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        req.getRequestDispatcher("/404.jsp").forward(req,resp);
    }

    @PatternUri(value = "/user/{user-id}/autos")
    public void showAutosForUser(HttpServletRequest req, HttpServletResponse resp){

        String userIdS = (String)req.getAttribute("user-id");
        if(userIdS.isEmpty()) return;

        int userId = Integer.valueOf(userIdS);
        User user = service.getUser(userId);
        if(user == null) return;;
        List<Auto> autos = service.getAutoByUser(userId);

        req.setAttribute("user", user);
        req.setAttribute("listAuto", autos);
        req.setAttribute("Title", "Машины "+user.getName());

        try {
            req.getRequestDispatcher("/auto.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String servletPath = req.getServletPath();
        servletPath = servletPath.replace(mainUrl,"");
        String[] paths = servletPath.split("/");

        String action = "";
        if(paths.length > 1) {
            action = paths[1].toLowerCase();
            try {
                ServletRequestActions autoServletClass = AutoServlet.class.newInstance();
                Method method = AutoServlet.class.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(autoServletClass, req, resp);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }


    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String headerName = req.getHeader("x-requested-with");
        if(headerName == null) return;
        service = ServiceFactory.getInstance().getService();
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            throw new IllegalArgumentException(e);
        }
        Auto auto = service.getAuto(id);
        if(auto == null)
            throw new IllegalArgumentException("No such user with id:"+id);
        else {
            JSONObject js = new JSONObject();
            js.put("id", auto.getId());
            js.put("model", auto.getModel());
            js.put("color", auto.getColor());
            js.put("userId", auto.getUser().getId());
            String textJson = js.toString();

            resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
            resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
            resp.getWriter().write(textJson);       // Write response body.
        }
    }

    @Override
    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String headerName = req.getHeader("x-requested-with");
        System.out.println("add app method");
        if(headerName != null) {
            String model = req.getParameter("model");
            String color = req.getParameter("color");
            int userId = 0;
            try {
                userId = Integer.valueOf(req.getParameter("userId"));
            }catch (NumberFormatException e){
                throw new IllegalArgumentException(e);
            }

            service = ServiceFactory.getInstance().getService();
            User user = new User.Builder().setId(userId).build();
            Auto auto = service.addAuto(new Auto.Builder().setModel(model).setColor(color).setUser(user).build());
            if(auto != null) {
                JSONObject js = new JSONObject();
                js.put("id", auto.getId());
                js.put("model", model);
                js.put("color", color);
                js.put("userId", user.getId());
                String textJson = js.toString();

                resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
                resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                resp.getWriter().write(textJson);       // Write response body.
            }
        }
    }

    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String headerName = req.getHeader("x-requested-with");
        if(headerName != null) {
            String model = req.getParameter("model");
            String color = req.getParameter("color");
            int userId = 0, id = 0;
            try {
                id = Integer.valueOf(req.getParameter("id"));
                userId = Integer.valueOf(req.getParameter("userId"));
            }catch (NumberFormatException e){
                throw new IllegalArgumentException(e);
            }

            service = ServiceFactory.getInstance().getService();
            User user = new User.Builder().setId(userId).build();
            boolean result = service.updateAuto(id,new Auto.Builder().setId(id).setModel(model).setColor(color).setUser(user).build());
            if(result) {
                JSONObject js = new JSONObject();
                js.put("id", id);
                js.put("model", model);
                js.put("color", color);
                js.put("userId", user.getId());
                String textJson = js.toString();

                resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
                resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                resp.getWriter().write(textJson);       // Write response body.
            }
        }
    }

    @Override
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String headerName = req.getHeader("x-requested-with");
        if(headerName == null) return;
        service = ServiceFactory.getInstance().getService();
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException e){
            throw new IllegalArgumentException(e);
        }
        boolean result = service.deleteAuto(id);

        if(!result)
            throw new IllegalArgumentException("Connot delete auto with id:"+id);

        JSONObject js = new JSONObject();
        js.put("success", true);
        String textJson = js.toString();

        resp.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        resp.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        resp.getWriter().write(textJson);       // Write response body.
    }
}
