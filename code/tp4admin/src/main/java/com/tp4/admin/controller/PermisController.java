package com.tp4.admin.controller;

import com.tp4.admin.model.Permis;
import com.tp4.admin.model.PermisTest;
import com.tp4.admin.repository.PermisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class PermisController {

    @Autowired
    PermisRepo permisRepo;

    @GetMapping("/permits/edit")
    public String editPermit(Model model) {
        model.addAttribute("permits", permisRepo.findAll());
        return "permitEdit";
    }

    @GetMapping("/permits/add")
    public String createPermit(Model model) {
        model.addAttribute("permit", new Permis());
        return "permitCreateForm";
    }

    @GetMapping("/permits/addTest")
    public String createPermitTest(Model model) {
        model.addAttribute("permit", new PermisTest());
        return "permitTestCreateForm";
    }

    @GetMapping("/permits/edit/{id}")
    public String editPermit(Model model, @PathVariable String id) {
        Optional<Permis> permis = permisRepo.findById(Integer.parseInt(id));
        if(permis.isPresent())
        {
            if(permis.get() instanceof PermisTest) {
                PermisTest permisTest = (PermisTest) permis.get();

                // Date conversion needed because of the browser's HTML5 date standard https://www.w3.org/TR/2011/WD-html-markup-20110405/input.date.html
                permisTest.generateHTMLDate(null);

                model.addAttribute("permit", permisTest);

                return "permitTestEditForm";
            }

            model.addAttribute("permit", permis.get());
            return "permitEditForm";
        }

        return "#";
    }

    @PostMapping("/permits/update")
    public String updatePermit(@ModelAttribute("permisTest") Permis permis, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "#";
        } else {

            try {
                Permis newPermis = permisRepo.save(permis);
                newPermis.generateHash();
                newPermis.setAutomaticFields();
                permisRepo.save(newPermis);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return "redirect:/permits/edit";
        }
    }

    @PostMapping("/permits/updateTest")
    public String updatePermitTest(@ModelAttribute("permisTest") PermisTest permis, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "#";
        } else {

            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sourceFormat.parse(permis.getDateCreationHTML());
                permis.setDateCreation(date);

                Permis newPermis = permisRepo.save(permis);
                newPermis.generateHash();
                newPermis.setAutomaticFields();
                permisRepo.save(newPermis);

            } catch (Exception e) {
                e.printStackTrace();
                return "#";
            }

            return "redirect:/permits/edit";
        }
    }

    @PostMapping("/permits/create")
    public String createPermit(@ModelAttribute("permisTest") Permis permis, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "#";
        } else {

            if(permis instanceof PermisTest) {
                SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
                PermisTest permisTest = (PermisTest) permis;

                try {
                    Date date = sourceFormat.parse(permisTest.getDateCreationHTML());
                    permisTest.setDateCreation(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return "#";
                }

                permis = permisTest;
            }

            try {
                Permis newPermis = permisRepo.save(permis);
                newPermis.generateHash();
                newPermis.setAutomaticFields();
                permisRepo.save(newPermis);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "redirect:/permits/edit";
        }
    }

    @GetMapping("/permits/delete/{id}")
    public String deletePermit(Model model, @PathVariable String id) {
        Optional<Permis> permis = permisRepo.findById(Integer.parseInt(id));
        if(permis.isPresent()) {
            permisRepo.delete(permis.get());
            return "redirect:/permits/edit";
        }

        return "#";
    }

}
