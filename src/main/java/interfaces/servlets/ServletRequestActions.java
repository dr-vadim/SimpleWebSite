package interfaces.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ServletRequestActions {
    void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
