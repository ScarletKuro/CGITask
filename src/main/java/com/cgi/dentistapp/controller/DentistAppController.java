package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dao.entity.PractitionerEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.dto.PractionerDTO;
import com.cgi.dentistapp.service.PractitionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;

    @Autowired
    private PractitionerService practitionerService;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO, Model model) {
        model.addAttribute("allPractitioners", this.practitionerService.listPractitioners());
        return "form";
    }

    @GetMapping("/appointments")
    public String showAppointments(Model model){
        model.addAttribute("allAppointments", this.dentistVisitService.listVisits());
        return "appointments";
    }

    @GetMapping("/appointments/remove/{id}")
    public String removeAppointment(@PathVariable("id") Long id){
        this.dentistVisitService.removeById(id);

        return "redirect:/appointments";
    }

    @GetMapping("/appointments/{id}")
    public String viewAppointment(@PathVariable("id") Long id, Model model){
        DentistVisitEntity dentistVisitEntity = this.dentistVisitService.getById(id);

        DentistVisitDTO dentistVisitDTO = new DentistVisitDTO(dentistVisitEntity.getDentistName(), dentistVisitEntity.getVisitDate(), dentistVisitEntity.getVisitDate());
        model.addAttribute("allPractitioners", this.practitionerService.getCheckedPractioners(dentistVisitEntity.getPractitioners()));
        model.addAttribute("dentistVisitDTO", dentistVisitDTO);
        model.addAttribute("id", id);
        model.addAttribute("dtodatevisit", new SimpleDateFormat("dd.MM.yyyy").format(dentistVisitEntity.getVisitDate()));
        model.addAttribute("dtotimevisit", new SimpleDateFormat("HH:mm").format(dentistVisitEntity.getVisitDate()));



        return "view";
    }

    @GetMapping("/appointments/edit/{id}")
    public String editAppointment(@PathVariable("id") Long id, Model model){
        DentistVisitEntity dentistVisitEntity = this.dentistVisitService.getById(id);

        DentistVisitDTO dentistVisitDTO = new DentistVisitDTO(dentistVisitEntity.getDentistName(), dentistVisitEntity.getVisitDate(), dentistVisitEntity.getVisitDate());
        model.addAttribute("allPractitioners", this.practitionerService.getCheckedPractioners(dentistVisitEntity.getPractitioners()));
        model.addAttribute("dentistVisitDTO", dentistVisitDTO);
        model.addAttribute("id", id);
        model.addAttribute("dtodatevisit", new SimpleDateFormat("dd.MM.yyyy").format(dentistVisitEntity.getVisitDate()));
        model.addAttribute("dtotimevisit", new SimpleDateFormat("HH:mm").format(dentistVisitEntity.getVisitDate()));

        return "edit";
    }

    @PostMapping("/appointments/edit/{id}")
    public String postEditAppointment(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, Model model, @PathVariable("id") Long id){
        DentistVisitEntity dentistVisitEntity = this.dentistVisitService.getById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("allPractitioners", this.practitionerService.getCheckedPractioners(dentistVisitEntity.getPractitioners()));
            return editAppointment(id, model);
        }

        dentistVisitEntity.setDentistName(dentistVisitDTO.getDentistName());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dentistVisitDTO.getVisitDate());
        calendar.add(Calendar.HOUR_OF_DAY, dentistVisitDTO.getVisitTime().getHours());
        calendar.add(Calendar.MINUTE, dentistVisitDTO.getVisitTime().getMinutes());
        if (dentistVisitEntity.getVisitDate().compareTo(calendar.getTime()) != 0){
            if (!this.dentistVisitService.checkDate(calendar.getTime())){
                bindingResult.rejectValue("visitDate", "error.visitDate", "Date and time is already taken!");
                bindingResult.rejectValue("visitTime", "error.visitTime", "Date and time is already taken!");
                model.addAttribute("dtodatevisit", new SimpleDateFormat("dd.MM.yyyy").format(dentistVisitEntity.getVisitDate()));
                model.addAttribute("dtotimevisit", new SimpleDateFormat("HH:mm").format(dentistVisitEntity.getVisitDate()));
                model.addAttribute("allPractitioners", this.practitionerService.getCheckedPractioners(dentistVisitEntity.getPractitioners()));
                return "edit";
            }
        }

        dentistVisitEntity.setVisitDate(calendar.getTime());
        dentistVisitEntity.setPractitioners(this.practitionerService.listPractitionersById(dentistVisitDTO.getPractitioners()));


        this.dentistVisitService.updateVisit(dentistVisitEntity);
        return "redirect:/appointments/edit/"+id;
    }


    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allPractitioners", practitionerService.listPractitioners());
            return "form";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dentistVisitDTO.getVisitDate());
        calendar.add(Calendar.HOUR_OF_DAY, dentistVisitDTO.getVisitTime().getHours());
        calendar.add(Calendar.MINUTE, dentistVisitDTO.getVisitTime().getMinutes());

        if (!this.dentistVisitService.checkDate(calendar.getTime())){
            model.addAttribute("allPractitioners", practitionerService.listPractitioners());
            bindingResult.rejectValue("visitDate", "error.visitDate", "Date and time is already taken!");
            bindingResult.rejectValue("visitTime", "error.visitTime", "Date and time is already taken!");
            return "form";
        }

        this.dentistVisitService.addVisit(dentistVisitDTO.getDentistName(), calendar.getTime(), this.practitionerService.listPractitionersById(dentistVisitDTO.getPractitioners()));
        return "redirect:/results";
    }

    @GetMapping("/error")
    public String error(){

        return "error";
    }


    @GetMapping("/search")
    public String searchByName(@RequestParam(required = true, defaultValue = "", value="name") String dentistName, Model model){
        model.addAttribute("allAppointments", this.dentistVisitService.searchByName(dentistName));
        model.addAttribute("dentistname", dentistName);
        return "appointments";
    }
}
