package com.milexpress.milexpressserver.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/health")
public class ApplicationController {

    @GetMapping()
    public Boolean applicationHealth() {
        return true;
    }
}
