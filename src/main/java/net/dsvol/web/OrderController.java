package net.dsvol.web;

import lombok.extern.slf4j.Slf4j;
import net.dsvol.TacoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(error -> log.warn("Order error: {}", error));
//            log.warn("Order error: {}", errors.getAllErrors());
            return "orderForm";
        } else {
            log.info("Order submitted: {}", order);
            sessionStatus.setComplete();
            return "redirect:/";
        }
    }
}