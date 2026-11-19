package me.dkLee.springdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    @ResponseBody   // 리스판스바디는 데이터를 출력(example)
    public String thymeleaf() {
        // html은 리턴타입이 무조건 String
        return "example";
    }

    @GetMapping("/thymeleaf/example2")
    // 기본은 html 출력
    public String thymeleaf2(Model model) {
        Person person = new Person();
        person.setName("홍길동");
        person.setAge(23);
        model.addAttribute("person", person);
        return "example";
    }
}


@Getter
@Setter
class Person {
    private String name;
    private  int age;
}
