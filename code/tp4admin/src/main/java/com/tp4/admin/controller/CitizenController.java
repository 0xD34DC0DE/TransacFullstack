package com.tp4.admin.controller;

import com.tp4.admin.model.Citoyen;
import com.tp4.admin.model.Enfant;
import com.tp4.admin.model.Permis;
import com.tp4.admin.repository.CitoyenRepo;
import com.tp4.admin.repository.EnfantRepo;
import com.tp4.admin.repository.PermisRepo;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CitizenController {

    @Autowired
    CitoyenRepo citoyenRepo;

    @Autowired
    EnfantRepo enfantRepo;

    @Autowired
    PermisRepo permisRepo;

    @GetMapping("/citizens/edit")
    public String getAllCitizens(Model model) {
        model.addAttribute("citizens", citoyenRepo.findAll());
        return "citizenEdit";
    }

    @GetMapping("/citizens/edit/{nas}")
    public String getCitizen(Model model, @PathVariable String nas) {

        Optional<Citoyen> citoyen = citoyenRepo.findByNumeroAssuranceSocial(nas);
        if(citoyen.isPresent())
        {
            model.addAttribute("citizen", citoyen.get());
            if(citoyen.get() instanceof Enfant) {
                return "enfantEditForm";
            }
            return "citizenEditForm";
        }

        return "#";
    }

    @PostMapping("/citizens/update")
    public String updateCitizens(@ModelAttribute("citoyen") Citoyen citoyen, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "#";
        } else {
            citoyenRepo.save(citoyen);
            return "redirect:/citizens/edit";
        }
    }

    @PostMapping("/citizens/updateEnfant")
    public String updateEnfant(@ModelAttribute("enfant") Enfant enfant, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "#";
        } else {
            enfantRepo.save(enfant);
            return "redirect:/citizens/edit";
        }
    }

    @GetMapping("/citizens/add")
    public String createCitizen(Model model) {
        model.addAttribute("citizen", new Citoyen());
        return "citizenCreateForm";
    }

    @PostMapping("/citizens/add")
    public String createCitizens(@ModelAttribute("citoyen") Citoyen citoyen, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "#";
        } else {
            citoyenRepo.save(citoyen);
            return "redirect:/citizens/edit";
        }
    }

    @GetMapping("/citizens/addChild")
    public String createChild(Model model) {
        model.addAttribute("citizen", new Enfant());
        return "enfantCreateForm";
    }

    @PostMapping("/citizens/addChild")
    public String createCitizens(@ModelAttribute("citoyen") Enfant citoyen, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "#";
        } else {
            citoyenRepo.save(citoyen);
            return "redirect:/citizens/edit";
        }
    }

    @GetMapping("/citizens/delete/{nas}")
    public String deleteCitizen(Model model, @PathVariable String nas) {

        Optional<Citoyen> citoyen = citoyenRepo.findByNumeroAssuranceSocial(nas);
        if(citoyen.isPresent())
        {
            // Had to do this since the cascade keeps getting ignored
            List<Permis> permitOfCitizen = permisRepo.findAllByCitoyen_Id(citoyen.get().getId());
            if(!permitOfCitizen.isEmpty()) {
                permitOfCitizen.forEach(permisRepo::delete);
            }

            // See comment above
            List<Enfant> enfantOfCitizen = enfantRepo.findEnfantsByParentId(citoyen.get().getId());
            if(!enfantOfCitizen.isEmpty()) {
                enfantOfCitizen.forEach(enfantRepo::delete);
            }

            citoyenRepo.delete(citoyen.get());
            return "redirect:/citizens/edit";
        }

        return "#";
    }

}
