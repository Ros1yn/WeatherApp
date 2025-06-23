package pl.ros1yn.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/weather")
    public String home(Model model) {
        model.addAttribute("title", "Weather☀️");
        model.addAttribute("message", "Witaj w Thymeleaf!");
        return "weather";
    }
}
