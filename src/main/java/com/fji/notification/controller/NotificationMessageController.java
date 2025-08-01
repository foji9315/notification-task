package com.fji.notification.controller;

import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.model.dto.UserShowMessageModel;
import com.fji.notification.service.CategoriesService;
import com.fji.notification.service.MessageLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NotificationMessageController {

    private final CategoriesService categoriesService;
    private final MessageLogService messageLogService;

    public NotificationMessageController(CategoriesService categoriesService,
                                         MessageLogService messageLogService) {
        this.categoriesService = categoriesService;
        this.messageLogService = messageLogService;
    }

    @GetMapping("/")
    public String showUserInterface(Model model) {
        List<String> categories = categoriesService.getAllCategoryNames();
        List<UserShowMessageModel> publishedMessages= messageLogService.getAllStoredMessages();
        model.addAttribute("messageFormModel", new MessageFormModel());
        model.addAttribute("categories", categories);
        model.addAttribute("publishedMessages", publishedMessages);
        return "home";
    }

    @PostMapping("/messages")
    public String submitForm(@RequestParam("category") String category,
                             @RequestParam("message") String message,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        model.addAttribute("submittedCategory", category);
        model.addAttribute("submittedMessage", message);
        MessageFormModel validatedMessage = MessageFormModel.builder()
                .category(category)
                .message(message)
                .build();

        boolean isMessageProcessedCorrectly = messageLogService.processIncomingMessage(validatedMessage);

        if (isMessageProcessedCorrectly) {
            redirectAttributes.addFlashAttribute("successMessage", "Message saved successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save Message. Please try agian");
        }
        return "redirect:/";
    }
}
