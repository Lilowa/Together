package paw.togaether.member.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import paw.togaether.common.domain.CommandMap;
import paw.togaether.member.service.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    Logger log = Logger.getLogger(this.getClass());

    @Resource(name="loginService")
    private LoginService loginService;

    @GetMapping(value="/member/login")
    public ModelAndView getLogin(CommandMap commandMap) throws Exception {
        ModelAndView mv = new ModelAndView("/member/Login");

        return mv;
    }

    @PostMapping(value="/member/login")
    public ModelAndView postLogin(CommandMap commandMap, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();

        Map<String, Object> userdata = loginService.Login(commandMap.getMap());
        boolean isloginsuccess = (boolean) userdata.get("isloginsuccess");

        if(isloginsuccess) {
            HttpSession session = request.getSession();
            String mem_id = userdata.get("MEM_ID").toString();
            String mem_dog_name = userdata.get("MEM_DOG_NAME").toString();
            String mem_addr = userdata.get("MEM_ADDR").toString();
            String mem_dog_weight = userdata.get("MEM_DOG_WEIGHT").toString();
            session.setAttribute("mem_id", mem_id);
            session.setAttribute("mem_dog_name", mem_dog_name);
            session.setAttribute("mem_addr", mem_addr);
            session.setAttribute("mem_dog_weight", mem_dog_weight);
            session.setMaxInactiveInterval(3600);

            mv.setViewName("redirect:/sample.paw");


        } else {
            mv.setViewName("redirect:/member/loginError.paw");
        }

        return mv;

    }

    @GetMapping(value="/member/loginError")
    public ModelAndView loginError() throws Exception{
        ModelAndView mv = new ModelAndView("/member/loginError");

        return mv;
    }

}
