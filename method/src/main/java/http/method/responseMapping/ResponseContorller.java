package http.method.responseMapping;


import http.method.ModelObject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class ResponseContorller {

    @RequestMapping("/response-v1")
    public ModelAndView responseViewV1(){
        ModelAndView mv = new ModelAndView("response/hello");
        mv.addObject("data", "test");
        return mv;
    }

    @RequestMapping("/response-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data", "test");
        return "response/hello";
    }

    @RequestMapping("/response-v3")
    public void responseViewV3(Model model){
        model.addAttribute("data", "test");
    }


    @GetMapping("/responseBody-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/responseBody-v2")
    public ResponseEntity<String> responseBodyV2(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping("/responseBody-v3")
    public String responseBodyV3(){
        return "ok";
    }

    @GetMapping("/responseBody-v4")
    public ResponseEntity<ModelObject> responseBodyV4(){
        ModelObject object = new ModelObject();
        object.setUsername("userA");
        object.setAge(20);
        return new ResponseEntity<>(object,HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/responseBody-v5")
    public ModelObject responseBodyV5(){
        ModelObject object = new ModelObject();
        object.setUsername("userA");
        object.setAge(20);
        return object;
    }




}
