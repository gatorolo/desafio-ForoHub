package com.AluraHub.Desafio.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class protectController {

    @RequestMapping(value = "/secured")
    public String welcome() {
        return "welcome from secured endpoint";
    }
}
