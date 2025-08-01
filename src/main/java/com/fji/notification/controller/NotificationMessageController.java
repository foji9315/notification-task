package com.fji.notification.controller;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.service.CategoriesService;
import com.fji.notification.service.MessageLogService;
import com.fji.notification.service.notifiers.Notifiable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.fji.notification.service.NotifierDelegator.NOTIFIER_DELEGATOR;

@Controller
public class NotificationMessageController {

    private final CategoriesService categoriesService;
    private final Notifiable notifierDelegatorService;
    private final MessageLogService messageLogService;

    public NotificationMessageController(CategoriesService categoriesService,
                                         MessageLogService messageLogService,
                                         @Qualifier(NOTIFIER_DELEGATOR) Notifiable notifierDelegatorService) {
        this.categoriesService = categoriesService;
        this.messageLogService = messageLogService;
        this.notifierDelegatorService = notifierDelegatorService;
    }

    @GetMapping("/")
    public String showUserInterface(Model model) {
        List<String> categories = categoriesService.getAllCategoryNames();
        List<MessageFormModel> publishedMessages= messageLogService.getAllStoredMessages();
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
        notifierDelegatorService.notifyIncomingMessage(NotificationMessage.builder().message(message).category(CategoryEnum.valueOf(category)).build());

        if (true) {
            redirectAttributes.addFlashAttribute("successMessage", "Message saved successfully!");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save Message.");
            return "redirect:/";
        }
    }
}
