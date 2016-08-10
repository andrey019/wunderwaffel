package andrey019.controller;

import andrey019.model.json.JsonEmail;
import andrey019.model.json.JsonRegistration;
import andrey019.service.auth.PasswordRecovery;
import andrey019.service.maintenance.LogService;
import andrey019.service.auth.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth/")
public class AuthController {

    private final static String RESPONSE_OK = "ok";
    private final static String RESPONSE_ERROR = "error";

    @Autowired
    private LogService logService;

    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PasswordRecovery passwordRecovery;


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        logService.accessToPage("logout");
        return "redirect:/";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login() {
//        logService.accessToPage("login");
//        return "redirect:/";
//    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        logService.accessToPage("access_denied");
        return "access_denied";
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public ModelAndView getRegistrationForm() {
//        logService.accessToPage("registration get");
//        ModelAndView modelAndView = new ModelAndView("registration", "teststr", "ололошка");
//        return modelAndView;
//    }

    @RequestMapping(value = "/emailCheck", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String emailCheck(@RequestBody JsonEmail jsonEmail) {
        logService.ajaxJson("emailCheck " + jsonEmail.getEmail());
        String check = registrationService.preRegistrationCheck(jsonEmail.getEmail());
        if (check != null) {
            return check;
        }
        return RESPONSE_OK;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String registration(@RequestBody JsonRegistration jsonRegistration) {
        logService.ajaxJson("registration " + jsonRegistration.getEmail());
        return registrationService.registration(jsonRegistration.getEmail(), jsonRegistration.getPassword(),
                jsonRegistration.getfName(), jsonRegistration.getlName());
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmation(@RequestParam("code") String code, HttpServletRequest request) {
        if (code == null) {
            logService.accessToPage("confirm, code == null");
            request.setAttribute("confirm", RESPONSE_ERROR);
            return "main_page";
        }
        if (registrationService.confirmRegistration(code)) {
            logService.accessToPage("confirm, ok");
            request.setAttribute("confirm", RESPONSE_OK);
            return "main_page";
        } else {
            logService.accessToPage("confirm, error");
            request.setAttribute("confirm", RESPONSE_ERROR);
            return "main_page";
        }
    }

    @RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String passwordRecovery(@RequestBody JsonEmail jsonEmail) {
        logService.ajaxJson("passwordRecovery " + jsonEmail.getEmail());
        return passwordRecovery.generateNewPassword(jsonEmail.getEmail());
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public ModelAndView registrationResponse(@RequestParam("email") String email,
//                                             @RequestParam("password") String password) {
//        String check = registrationService.preRegistrationCheck(email);
//        if (check != null) {
//            logService.accessToPage("registration post, " + email + ", " + check);
//            return new ModelAndView("registration", "error", check);
//        }
//        if (registrationService.registration(email, password)) {
//            logService.accessToPage("registration post, " + email + ", ok");
//            return new ModelAndView("registration", "error", "check your email(may take a couple of minutes)");
//        } else {
//            logService.accessToPage("registration post, " + email + ", error");
//            return new ModelAndView("registration", "error", "error! talk to support");
//        }
//    }

//    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
//    public ModelAndView confirmation(@RequestParam("code") String code) {
//        if (code == null) {
//            logService.accessToPage("confirm, code == null");
//            return new ModelAndView("registration", "error", "error! talk to support");
//        }
//        if (registrationService.confirmRegistration(code)) {
//            logService.accessToPage("confirm, ok");
//            return new ModelAndView("test_page", "confirm", "Registration complete! Please, sign in.");
//        } else {
//            logService.accessToPage("confirm, error");
//            return new ModelAndView("test_page", "confirm", "Registration error! talk to support");
//        }
//    }


}