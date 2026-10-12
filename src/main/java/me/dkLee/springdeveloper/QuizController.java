package me.dkLee.springdeveloper;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {
    // 요청 URL과 요청 방식(GET, POST, PATCH, PUT, DELETE)에 따라 그 요청을 실행할 매서드를 매핑
    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam("code") int code) {
        switch (code) {
            case 1:
                return ResponseEntity.created(null).body("Created!");   // 201. "created!"
            case 2:
                return ResponseEntity.badRequest().body("Bad Request!");    // 300. "Bad Request!"
            default:
                return ResponseEntity.ok("OK!"); // 200. "OK!"
        }
    }

    @PostMapping("/quiz")
    public ResponseEntity<String> quiz2(@RequestBody Code code) {
        System.out.println("--------------------------------------------------------------["+code+"]");
        switch (code.value()) {
            case 1:
                return ResponseEntity.status(403).body("Forbidden!");
            default:
                return ResponseEntity.ok().body("OK!");
        }

    }

//    @PatchMapping("/quiz")
//    public ResponseEntity<String> quiz3() {
//
//    }

}


record Code(int value) {}
