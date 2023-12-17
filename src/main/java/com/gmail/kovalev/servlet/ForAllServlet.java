package com.gmail.kovalev.servlet;

import com.gmail.kovalev.config.Config;
import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.service.FacultyService;
import com.gmail.kovalev.service.impl.FacultyServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "all", urlPatterns = {"/all"})
public class ForAllServlet extends HttpServlet {
    FacultyService facultyService;
    private Gson gson;

    private final int pageSize = Integer.parseInt(Config.getInstance().config.get("application").get("page size"));

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        facultyService = new FacultyServiceImpl();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pageStr = req.getParameter("page");
        int page = Integer.parseInt(pageStr);
        List<FacultyInfoDTO> allFaculties = facultyService.findAllFaculties(page, pageSize);
        String json = gson.toJson(allFaculties);
        resp.getWriter().println(json);
    }
}
