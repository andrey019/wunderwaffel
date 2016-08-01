package andrey019.controller;

import andrey019.model.UserConfirmation;
import andrey019.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public @ResponseBody String tesingJson() {
        UserConfirmation userConfirmation = new UserConfirmation();
        userConfirmation.setDate(564354);
        userConfirmation.setCode("codeset");
        userConfirmation.setPassword("passset");
        userConfirmation.setEmail("emailset");
        return "<b>sfasfa</b>safsdfasdfa <div/>";
    }
}