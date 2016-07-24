package andrey019.controller;

import andrey019.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

//	@Autowired
//	private AdvDAO advDAO;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LogService logService;

	@RequestMapping("/")
	public String listAdvs() {
		logService.accessToPage("main");
		return "test_page";
	}

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        //model.addAttribute("user", getPrincipal());
        logService.accessToPage("admin");
        return "admin";
    }

    @RequestMapping("rest")
    public String rest() {
        logService.accessToPage("rest to admin");
        return "admin";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        logService.accessToPage("logout");
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        logService.accessToPage("login");
        return "login";
    }

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        logService.accessToPage("access_denied");
        return "access_denied";
    }

	@RequestMapping("/favicon.ico")
    public String favicon() {
        logService.accessToPage("favicon.ico");
        return "forward:/resources/favicon.ico";
    }

    @RequestMapping("/ololo")
    public String ololo() {
        logService.accessToPage("ololo");
        return "ololo";
    }

    @RequestMapping("/user/ololo")
    public String userololo() {
        logService.accessToPage("user/ololo");
        return "ololo";
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

//    @RequestMapping("/backet")
//    public ModelAndView backet() {
//        AdsModel adsModel = new AdsModel();
//		adsModel.setAdsDel(advDAO.getDeleted());
//        return new ModelAndView("backet", "adsModel", adsModel);
//    }
//
//	@RequestMapping(value = "/add_page")
//	public String addPage() {
//		return "add_page";
//	}
//
//	@RequestMapping(value = "/search", method = RequestMethod.POST)
//	public ModelAndView search(@RequestParam(value="pattern") String pattern) {
//        AdsModel adsModel = new AdsModel(advDAO.list(pattern));
//		return new ModelAndView("index", "adsModel", adsModel);
//	}
//
//	@RequestMapping("/delete")
//	public String delete(@RequestParam(value="id") long id, HttpServletResponse response) {
//		if (advDAO.deleteToBacket(id)) {
//			return "redirect:/";
//		} else {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return null;
//        }
//	}
//
//    @RequestMapping("/deletechkbox")
//    public String deleteChkbox(@ModelAttribute("adsModel") AdsModel adsModel, HttpServletResponse response) {
//        if ( (adsModel.getIds() != null) && (adsModel.getIds().length != 0) ) {
//            if (advDAO.deleteToBacket(adsModel.getIds())) {
//                return "redirect:/";
//            } else {
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                return null;
//            }
//        } else {
//            return "redirect:/";
//        }
//    }
//
//    @RequestMapping("/restore")
//	public String restore(@RequestParam(value = "id") long id, HttpServletResponse response) {
//        if (advDAO.restoreFromBacket(id)) {
//            return "redirect:/backet";
//        } else {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return null;
//        }
//    }
//
//    @RequestMapping("/restorechkbox")
//    public String restoreChkbox(@ModelAttribute("adsModel") AdsModel adsModel, HttpServletResponse response) {
//        if ( (adsModel.getIds() != null) && (adsModel.getIds().length != 0) ) {
//            if (advDAO.restoreFromBacket(adsModel.getIds())) {
//                return "redirect:/backet";
//            } else {
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                return null;
//            }
//        } else {
//            return "redirect:/backet";
//        }
//    }
//
//	@RequestMapping("/image/{file_id}")
//	public void getFile(HttpServletResponse response, @PathVariable("file_id") long fileId) {
//		try {
//			byte[] content = advDAO.getPhoto(fileId);
//			response.setContentType("image/png");
//			response.getOutputStream().write(content);
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//	}
//
//    @RequestMapping("/imagebacket/{file_id}")
//    public void getFileFromBacket(HttpServletResponse response, @PathVariable("file_id") long fileId) {
//        try {
//            byte[] content = advDAO.getPhotoFromBacket(fileId);
//            response.setContentType("image/png");
//            response.getOutputStream().write(content);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @RequestMapping("/xml")
//    public Object getXml() {
//        Advertisements advertisements = new Advertisements(advDAO.getAll());
//        byte[] mediaBody = XmlService.getXmlBytes(advertisements);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("all", "xml"));
//        httpHeaders.set("Content-Disposition", "attachment; filename=ads.xml");
//        httpHeaders.setContentLength(mediaBody.length);
//        return new ResponseEntity<byte[]>(mediaBody, httpHeaders, HttpStatus.OK);
//    }
//
//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	public String addAdv(@RequestParam(value="name") String name,
//						 @RequestParam(value="shortDesc") String shortDesc,
//						 @RequestParam(value="longDesc", required=false) String longDesc,
//						 @RequestParam(value="phone") String phone,
//						 @RequestParam(value="price") double price,
//						 @RequestParam(value="photo") MultipartFile photo,
//						 HttpServletResponse response) {
//
//		try {
//			Advertisement adv = new Advertisement(
//					name, shortDesc, longDesc, phone, price,
//					photo.isEmpty() ? null : new Photo(photo.getOriginalFilename(), photo.getBytes())
//			);
//			advDAO.add(adv);
//			return "redirect:/";
//		} catch (IOException ex) {
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
//		}
//	}
}