package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!!!");
        return "hello";
    }

    // 정적 컨텐츠 방식은 static에 있는 html 파일로 바로이동
    // localhost:8080/hello-static.html로 접근 가능
    // 스프링부트에서 hello-static.html이라는 컨트롤러 먼저 찾고 없으면 static으로 이동

    // mvc 방식 >> 페이지를 리턴함(HTML)
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);

        return "hello-template";
    }

    // API 방식 >> 데이터를 리턴함 (페이지 소스 보기를 누르면 HTML이 아니라 그냥 string임)
    // @ResponseBody 사용시
    // HTTP의 BODY에 문자 내용을 직접 반환
    // 스프링 자체에서 viewResolver 대신에 HttpMessageConverter가 동작함
    // 기본 문자 처리 : StringHttpMessageConverter
    // 기본 객체 처리 : MappingJackson2HttpMessageConverter  >> Jackson? 객체를 JSON 형식으로 바꿔주는 라이브러리(Spring에서는 Jackson이 디폴트, 구글에서 만든 gson도 있음)
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // "hello spring"
    }

    // API 방식에서 객체타입을 리턴하면 디폴트로 JSON 형식으로 전달됨
    // HttpMessageConverter
    // 단순 문자면 StringConverter, 객체면 JsonConverter 동작 (객체가 JSON 형식으로 변환됨)
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();

        hello.setName(name);

        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
