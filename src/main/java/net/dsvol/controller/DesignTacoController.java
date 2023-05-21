package net.dsvol.controller;

import lombok.extern.slf4j.Slf4j;
import net.dsvol.Ingredient;
import net.dsvol.Taco;
import net.dsvol.data.TacoOrder;
import net.dsvol.repository.IngredientRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
//    private final IngredientRepository ingredientRepo;

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
    @GetMapping
    public String showDesignForm() {
        return "design";
    }
    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processTaco(Taco taco,
                              @ModelAttribute TacoOrder tacoOrder) {
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

//    @Autowired
//    public DesignTacoController(
//            IngredientRepository ingredientRepo) {
//        this.ingredientRepo = ingredientRepo;
//    }
//    @ModelAttribute
//    public void addIngredientsToModel(Model model) {
//        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
//        Ingredient.Type[] types = Ingredient.Type.values();
//        for (Ingredient.Type type : types) {
//            model.addAttribute(type.toString().toLowerCase(),
//                    filterByType((List<Ingredient>) ingredients, type));
//        }
//    }
//    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
//        return ingredients.stream()
//                .filter(x -> x.getType().equals(type))
//                .collect(Collectors.toList());
//    }
//
//    @ModelAttribute(name = "tacoOrder")
//    public TacoOrder order() {
//        return new TacoOrder();
//    }
//
//    @ModelAttribute(name = "taco")
//    public Taco taco() {
//        return new Taco();
//    }
//
//    @GetMapping
//    public String showDesignForm() {
//        return "design";
//    }
//
//    @PostMapping
//    public String processTaco(@Valid Taco taco,
//                              Errors errors,
//                              @ModelAttribute TacoOrder tacoOrder) {
//        if (errors.hasErrors()){
//            return "design";
//        }
//        tacoOrder.addTaco(taco);
//        log.info("Processing taco: {}", taco);
//
//        return "redirect:/orders/current";
//    }
}
