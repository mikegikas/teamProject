package com.teamproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.teamproject.bean.Route;
import com.teamproject.bean.User;
import static com.teamproject.controller.WelcomeController.session;
import com.teamproject.db.RouteDAO;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {

    User curUser;

    @RequestMapping("/allroutes")
    public ModelAndView getAllRoutes(HttpServletRequest request) {
        
        // if user's cookie does not match got to login page!
        if ( !(CookieHandler.validateCookie(request.getCookies())) ) return new ModelAndView("redirect:/");
        

        ModelAndView model = new ModelAndView("template");
        model.addObject("includeView", "viewRoutes");

        ArrayList<Route> allRoutes = new ArrayList<>();

        RouteDAO routeDAO = RouteDAO.getInstance();
        HashMap<Integer, Route> allRoutesMap = routeDAO.selectAllRoutes();

        allRoutesMap.forEach((k, v) -> allRoutes.add(v));

        model.addObject("allRoutes", allRoutes);

        return model;
    }
    
    @RequestMapping("/allcreatedroutes")
    public ModelAndView getAlljoinedRoutes(HttpServletRequest request) {
        
        // if user's cookie does not match got to login page!
        if ( !(CookieHandler.validateCookie(request.getCookies())) ) return new ModelAndView("redirect:/");
        

        ModelAndView model = new ModelAndView("template");
        model.addObject("includeView", "viewRoutes");

        ArrayList<Route> allRoutes = new ArrayList<>();

        RouteDAO routeDAO = RouteDAO.getInstance();
        curUser = (User) session().getAttribute("curUser");
        HashMap<Integer, Route> allRoutesMap = routeDAO.selectAllCreatedRoutesById(curUser);

        allRoutesMap.forEach((k, v) -> allRoutes.add(v));

        model.addObject("allRoutes", allRoutes);

        return model;
    }
    
    @GetMapping("/route/{id}")
    public ModelAndView viewSingleRoute( @PathVariable("id") int id, HttpServletRequest request ) {
        
        // if user's cookie does not match got to login page!
        if ( !(CookieHandler.validateCookie(request.getCookies())) ) return new ModelAndView("redirect:/");
        
        
        ModelAndView model = new ModelAndView("template");
        model.addObject("includeView", "route");

        Route route = RouteDAO.getInstance().selectRouteById(id);
        System.out.println(route.getDescription());

        model.addObject("aRoute", route);

        return model;
    }

    @GetMapping("/addroute")
    public ModelAndView getAddRoute(HttpServletRequest request) {

        // if user's cookie does not match got to login page!
        if ( !(CookieHandler.validateCookie(request.getCookies())) ) return new ModelAndView("redirect:/");

        ModelAndView model = new ModelAndView("template");
        model.addObject("includeView", "addroute");

        return model;
    }

    @PostMapping("/addroute")
    public ModelAndView postAddRoute(Route route, HttpServletRequest request) {
        
        // if user's cookie does not match got to login page!
        if ( !(CookieHandler.validateCookie(request.getCookies())) ) return new ModelAndView("redirect:/");

        RouteDAO routeDAO = RouteDAO.getInstance();
//        UserDAO userDao = UserDAO.getInstance();
        // if user exists reload page!
//       if  (userDao.checkUser(user.getUsername()) != 0)
//           return new ModelAndView("redirect:/signup");
        // if user is created go to login page
        if (routeDAO.createRoute(route) != 0) {
            return new ModelAndView("redirect:/login");
        } else {
            return new ModelAndView("error");
        }
    }
    
    @GetMapping("/editroute{id}")
    public ModelAndView getEditRoute(@PathVariable("id") int id, Route updatedRoute, HttpServletRequest request) {

        // if user's cookie does not match got to login page!
        if ( !(CookieHandler.validateCookie(request.getCookies())) ) return new ModelAndView("redirect:/");

        System.out.println(id);
        Route routeToEdit =  RouteDAO.getInstance().getRouteById( id );

        ModelAndView model = new ModelAndView("template");
        model.addObject("includeView", "editrouteform");
        model.addObject("routeToEdit",routeToEdit);

        return  model;
    }

    @PostMapping("/updateroute")
    public ModelAndView postEditRoute(@ModelAttribute("updatedRoute")Route updatedRoute, HttpServletRequest request){

        // if user's cookie does not match got to login page!
        if ( !(CookieHandler.validateCookie(request.getCookies())) ) return new ModelAndView("redirect:/");

        System.out.println(updatedRoute.getId());
        int updated = RouteDAO.getInstance().updateRoute(updatedRoute);

        return new ModelAndView("redirect:/allroutes");
    }

    @GetMapping("/deleteroute{id}")
    public ModelAndView postDeleteUser(@ModelAttribute("userToDelete")Route routeToDelete, HttpServletRequest request){

       // if user's cookie does not match got to login page!
        if ( !(CookieHandler.validateCookie(request.getCookies())) ) return new ModelAndView("redirect:/");

        int updated = RouteDAO.getInstance().deleteRoute(routeToDelete);

        return new ModelAndView("redirect:/allroutes");
    }
    
}
