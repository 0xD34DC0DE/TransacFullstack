package com.tp3.ministere.controller;

import com.tp3.ministere.model.Citoyen;
import com.tp3.ministere.service.CitoyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ministere")
public class CitoyenController {

    @Autowired
    CitoyenService citoyenService;

    @GetMapping("/{numeroAssuranceSocial}")
    public Citoyen findCitoyenByNumeroAssuranceSocial(@PathVariable String numeroAssuranceSocial) {
        return citoyenService.findCitoyenByNumeroAssuranceSocial(numeroAssuranceSocial).orElse(null);
    }

}
