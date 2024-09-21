package http.method.requestMapping;

import http.method.ModelObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;

@RestController
@Slf4j
public class MappingController {

    @RequestMapping("/hello-basic")
    public String hello(){
        log.info("hello");
        // 템플릿엔진이 없으면 String으로 응답이 나옴
        return "ok";
    }

    @RequestMapping(value="/getMapping-v1", method= RequestMethod.GET)
    public String getMappingV1(){
        log.info("getMapping V1");
        return "ok";
    }

    @GetMapping("/getMapping-v2")
    public String getMappingV2(){
        log.info("getMapping V2");
        return "ok";
    }

    @GetMapping("/getMapping-v3/{userId}")
    public String getMappingV3(@PathVariable("userId") int userId){
        log.info("getMapping V3 , userId={}", userId);
        return "ok";
    }

    @GetMapping("/getMapping-v3/{userId}/orders/{orderId}")
    public String getMappingV4(@PathVariable("userId")int userId, @PathVariable("orderId") int orderId){
        log.info("getMappingV3 , userId={}. orderId={}",userId, orderId);
        return "ok";
    }

    @RequestMapping("/request-param-v1")
    public String getMappingV4(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age= {}", username, age);
        return "ok";
    }

    //message body에 있는 내용을 가져옴 (PostMan으로 확인)
    @ResponseBody
    @RequestMapping("/requestParam-v1")
    public String requestParamV1(@RequestParam(value = "username",required = true, defaultValue = "test") String username,
                               @RequestParam(value="age",required = false) Integer age){
        //Integer을 사용하는 이유는 false로 인해 null이 들어올수 있으므로 int 대신해서 Integer 사용
        log.info("username={}, age={}",username,age);
        return "ok";
    }

    //@RequestParam value 생략하면, 받는 변수에 따라 값을 가져오게됨
    //@RequestParam String username 이면, "username"값을 변수에 저장함
    @ResponseBody
    @RequestMapping("requestParam-v2")
    public String requestParamV2(@RequestParam Map<String,Object> paramMap){
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    //postman으로 확인
    @PostMapping(value="/postMapping", consumes = "application/json")
    public String postMapping(){
        log.info("application/json");
        return "ok";
    }

    @PostMapping(value="postMapping-V2", produces = "text/html")
    public String postMappingV2(){
        log.info("text/html");
        return "ok";
    }

    @PatchMapping("/patchMapping/{userId}")
    public String patchMapping(@PathVariable("userId") int userId){
        log.info("patch Mapping = {}",userId);
        return "ok";
    }

    @DeleteMapping("/deleteMapping/{userId}")
    public String deleteMapping(@PathVariable("userId") int userId){
        log.info("delete Mapping = {}", userId);
        return "ok";
    }


    // Header 조회
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod,
                          Locale locale, @RequestHeader MultiValueMap<String, String> multiValueMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value="myCookie", required = false) String cookie){
        log.info("request={}", request);
        log.info("respons={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("multiValueMap={}", multiValueMap);
        log.info("host={}",host);
        log.info("cookie={}", cookie);
        return "ok";
    }

    //@ModelAttribute를 생략해도 값을 객체에 넣어줌
    // 객체에 request 값을 저장하고 싶을때 사용
    @ResponseBody
    @RequestMapping("/ModelAttribute-v1")
    public String modelAttribute(@ModelAttribute ModelObject data){
        log.info("username ={}, age ={}", data.getUsername(), data.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/requestBody")
    public String requestBody(@RequestBody String message){
        log.info("message = {}", message);
        return "ok";
    }


    //JSON
    @ResponseBody
    @RequestMapping("/requestBody/json")
    public String requestBodyJson(@RequestBody ModelObject data){
        log.info("username ={}, age ={}", data.getUsername(), data.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/requestBody/json/v2")
    public ModelObject requestBodyJsonV2(@RequestBody ModelObject data){
        log.info("username ={}, age ={}", data.getUsername(), data.getAge());
        return data; // 반환값 객체 반환
    }

    @ResponseBody
    @PostMapping("/requestBody/json/v3")
    public String requestBodyJsonV3(HttpEntity<ModelObject> httpEntity){
        ModelObject data = httpEntity.getBody();
        log.info("username ={}, age={}",data.getUsername(), data.getAge());
        return "ok";
    }







}
