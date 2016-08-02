package andrey019.controller;

import andrey019.model.JsonModel;
import andrey019.model.dao.UserConfirmation;
import andrey019.service.maintenance.LogService;
import com.mchange.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private LogService logService;


    @RequestMapping("/ololo")
    public String userololo() {
        logService.accessToPage("user/ololo");
        return "ololo";
    }

    @RequestMapping("/json")
    public String tesingJson() {
        UserConfirmation userConfirmation = new UserConfirmation();
        userConfirmation.setDate(564354);
        userConfirmation.setCode("codeset");
        userConfirmation.setPassword("passset");
        userConfirmation.setEmail("emailset");
        return "json_test";//h
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    public String test() {
        System.out.println("!!!  !!!");
        //System.out.println(jsonModel);
        return "ololo";//sdf
    }
}
