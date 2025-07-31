package com.fji.notification.controller;

import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.service.CategoriesService;
import com.fji.notification.service.NotifierDelegator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationMessageController {

    private final CategoriesService categoriesService;
    private final NotifierDelegator notifierDelegatorService;

    @GetMapping("/")
    public String showUserInterface(Model model) {
        List<String> categories = categoriesService.getAllCategoryNames();
        model.addAttribute("messageFormModel", new MessageFormModel());
        model.addAttribute("categories", categories);
        return "home";
    }

    @PostMapping("/messages")
    public String submitForm(@RequestParam("category") String category,
                             @RequestParam("message") String message,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        model.addAttribute("submittedCategory", category);
        model.addAttribute("submittedMessage", message);
        MessageFormModel messageFormModel = MessageFormModel.builder()
                .category(category)
                .message(message)
                .build();
        notifierDelegatorService.incomingMessage(messageFormModel);

        if (true) {
            redirectAttributes.addFlashAttribute("successMessage", "Message saved successfully!");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save Message.");
            return "redirect:/";
        }
    }
}
