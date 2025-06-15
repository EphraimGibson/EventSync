package pro.ephraimgibson.ibm.internproject.eventsync.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // return the name of your custom error view
        return "error";
    }
}
