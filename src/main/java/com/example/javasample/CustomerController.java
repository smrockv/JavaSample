package com.example.javasample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.javasample.CustomerDao.*;

@Controller
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private CustomerDao customerDao;
    private AreaDao areaDao;
    record CustomerEditForm(String customerId, String customerName, Boolean isEdit, String areaId) {}

    @Autowired
    CustomerController(CustomerDao customerDao, AreaDao areaDao) {
        this.customerDao = customerDao;
        this.areaDao = areaDao;
    }

    @GetMapping("/")
    public String index(Model model) {
        logger.debug("index in");
        model.addAttribute("customerList", customerDao.findAll());
        return "index";
    }

//    @PostMapping("/add")
//    public String add(@ModelAttribute CustomerForm form) {
//        logger.info("{}", form.toString());
//        return "redirect:/";
//    }

    @PostMapping("/delete")
    public String delete(@RequestParam String id) {
        logger.info("delete id is {}", id);
        customerDao.delete(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam String id, Model model) {
        logger.debug("edit in");
        model.addAttribute("isEdit", true);
        model.addAttribute("customer", customerDao.find(id));
        model.addAttribute("areaList", areaDao.findAll());
        return "edit";
    }

    @GetMapping("/new")
    public String new_entry(Model model) {
        logger.debug("new in");
        model.addAttribute("isEdit", false);
        model.addAttribute("customer", new CustomerInfo(-1,"",-1,""));
        model.addAttribute("areaList", areaDao.findAll());
        return "edit";
    }

    @PostMapping("/regist")
    public String registry(@ModelAttribute CustomerEditForm form) {
        logger.debug("registry in {}", form.customerId);
        if (form.isEdit) {
            customerDao.update(new CustomerRecord(
                    Integer.parseInt(form.customerId),
                    form.customerName,
                    form.areaId
            ));
        } else {
            customerDao.insert(new CustomerRecord(
                    Integer.parseInt(form.customerId),
                    form.customerName,
                    form.areaId
            ));
        }
        return "redirect:/";
    }
}
