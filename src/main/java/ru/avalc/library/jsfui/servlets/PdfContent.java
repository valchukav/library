package ru.avalc.library.jsfui.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.avalc.library.jsfui.controller.BookController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author Alexei Valchuk, 19.04.2023, email: a.valchukav@gmail.com
 */

@WebServlet(name = "PdfContent",
        urlPatterns = {"/PdfContent"})
public class PdfContent extends HttpServlet {

    private ApplicationContext context;

    @Override
    public void init() {
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try (OutputStream out = response.getOutputStream()) {
            long id = Long.parseLong(request.getParameter("id"));
            long viewCount = Long.parseLong(request.getParameter("viewCount"));

            BookController bookController = ((BookController) context.getBean("bookController"));

            byte[] content = bookController.getContent(id);

            if (content == null) {
                response.sendRedirect(request.getContextPath() + "/error/error-pdf.xhtml");
            } else {
                response.setContentType("application/pdf");

                bookController.updateViewCount(viewCount, id);

                response.setContentLength(content.length);
                out.write(content);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
