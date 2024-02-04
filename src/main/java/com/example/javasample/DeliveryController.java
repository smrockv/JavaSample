package com.example.javasample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeliveryController {
    private CustomerDao customerDao;
    private AreaDao areaDao;

    @Autowired
    DeliveryController(CustomerDao customerDao, AreaDao areaDao) {
        this.customerDao = customerDao;
        this.areaDao = areaDao;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customerList", customerDao.findAll());
        areaDao.findAll();
        return "index";
    }

}
