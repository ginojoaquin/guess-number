package com.example.scaffolding.controllers;

import com.example.scaffolding.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guess-number/matches")
public class MatchController {
    @Autowired
    private MatchService matchService;
}
