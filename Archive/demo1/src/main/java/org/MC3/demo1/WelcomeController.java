package org.MC3.demo1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "<b>3_MC_3 Demo 1</b>";
    }
}
