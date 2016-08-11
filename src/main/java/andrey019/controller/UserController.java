package andrey019.controller;

import andrey019.model.json.JsonMessage;
import andrey019.model.json.JsonProfile;
import andrey019.model.json.JsonTodoList;
import andrey019.service.HtmlGenerator;
import andrey019.service.auth.ProfileService;
import andrey019.service.dao.TodoService;
import andrey019.service.maintenance.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static String RESPONSE_OK = "ok";
    private final static String RESPONSE_ERROR = "error";

    @Autowired
    private LogService logService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private HtmlGenerator htmlGenerator;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String userPage() {
        logService.accessToPage("user (user_page)");
        return "user_page";
    }

    @RequestMapping(value = "/loadLists", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadLists(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("loadLists " + getUserEmail());
        return htmlGenerator.generateTodoListsHtml(todoService.getAllTodoLists(getUserEmail()));
    }

    @RequestMapping(value = "/loadTodos", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadTodos(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("loadTodos " + getUserEmail());
        return htmlGenerator.generateTodosHtml(todoService.getTodosByListId(getUserEmail(), jsonMessage.getListId()));
    }

    @RequestMapping(value = "/loadDoneTodos", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadDoneTodos(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("loadDoneTodos " + getUserEmail());
        return htmlGenerator.generateDoneTodosHtml
                (todoService.getDoneTodosByListId(getUserEmail(), jsonMessage.getListId()));
    }

    @RequestMapping(value = "/addTodo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("addTodo " + getUserEmail());
        if (todoService.addTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getTodoText())) {
            return RESPONSE_OK;
        }
        return RESPONSE_ERROR;
    }

    @RequestMapping(value = "/doneTodo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String doneTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("doneTodo " + getUserEmail());
        if (todoService.doneTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getTodoId())) {
            return RESPONSE_OK;
        }
        return RESPONSE_ERROR;
    }

    @RequestMapping(value = "/unDoneTodo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String unDoneTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("unDoneTodo " + getUserEmail());
        if (todoService.unDoneTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getDoneTodoId())) {
            return RESPONSE_OK;
        }
        return RESPONSE_ERROR;
    }

    @RequestMapping(value = "/addTodoList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addTodoList(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("addTodoList " + getUserEmail());
        if (todoService.addTodoList(getUserEmail(), jsonMessage.getListName())) {
            return RESPONSE_OK;
        }
        return RESPONSE_ERROR;
    }

    @RequestMapping(value = "/todoListDeleteInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTodoListDeleteInfo(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("getTodoListDeleteInfo " + getUserEmail());
        return todoService.getTodoListInfo(getUserEmail(), jsonTodoList.getTodoListId());
    }

    @RequestMapping(value = "/getProfile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody JsonProfile getProfile() {
        logService.ajaxJson("getProfile " + getUserEmail());
        JsonProfile jsonProfile = profileService.getProfile(getUserEmail());
        System.out.println(jsonProfile);
        return jsonProfile;
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateProfile(@RequestBody JsonProfile jsonProfile) {
        logService.ajaxJson("updateProfile " + getUserEmail());
        System.out.println(jsonProfile);
        return profileService.updateProfile(getUserEmail(), jsonProfile);
    }

    private String getUserEmail(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

//    @RequestMapping("/ololo")
//    public String userololo() {
//        logService.accessToPage("user/ololo");
//        return "ololo";
//    }

//    @RequestMapping("/json")
//    public String tesingJson() {
//        UserConfirmation userConfirmation = new UserConfirmation();
//        userConfirmation.setDate(564354);
//        userConfirmation.setCode("codeset");
//        userConfirmation.setPassword("passset");
//        userConfirmation.setEmail("emailset");
//        return "json_test";//h
//    }

//    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
//    @ResponseBody
//    public String test(@RequestBody JsonMessage jsonMessage) {
//        System.out.println("!!!  !!!");
//        System.out.println(jsonMessage);
//        return jsonMessage.getListId() + " = " + getUserEmail() + "<button id=\"99999\" class=\"btn btn-default\" type=\"button\" onclick=\"oneMore(event)\">json button</button>";//sdf
//    }

//    @RequestMapping("testdao")
//    @ResponseBody
//    public String testDao() {
//        innerDao();
//        return "done";
//    }

//    private void innerDao() {
//        User user = userDao.getById(1);
//        TodoList todoList = new TodoList();
//        todoList.setOwner(user);
//        todoList.addUsers(user);
//        todoList.setName("spring name ололо");
//        todoListDao.save(todoList);
//        System.out.println(todoListDao.getByUsers(1).size());
//        System.out.println(todoListDao.getUsersByTodoListId(1).size());
//    }
}
