package org.launchcode.controllers;

import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Menu());

        return "menu/add";
    }

    @RequestMapping(value= "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu menu, Errors errors) {
            if (errors.hasErrors()) {
                return "menu/add";
            }

        menuDao.save(menu);
        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id) {
        model.addAttribute("menu", menuDao.findOne(id));

        return "view.html";
    }

    @RequestMapping(value = "/add-item", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int id) {
        model.addAttribute("menu", menuDao.findOne(id));

        AddMenuItemForm addMenuItemForm = new AddMenuItemForm(menuDao.findOne(id), cheeseDao.findAll());
        model.addAttribute("form", addMenuItemForm);

        return "add-item";
    }
}
