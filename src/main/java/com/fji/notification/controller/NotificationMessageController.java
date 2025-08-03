package com.fji.notification.controller;

import com.fji.notification.exception.NotificationServiceException;
import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.model.dto.UserShowMessageModel;
import com.fji.notification.service.CategoriesService;
import com.fji.notification.service.MessageLogService;
import com.fji.notification.validation.MessageFormValidator;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class NotificationMessageController {

    private static final String SUBMITTED_CATEGORY = "submittedCategory";
    private static final String SUBMITTED_MESSAGE = "submittedMessage";
    private static final String MESSAGE_FORM_MODEL = "messageFormModel";

    private static final PolicyFactory PLAIN_TEXT_SANITIZE_POLICY = new HtmlPolicyBuilder().toFactory();
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
        List<UserShowMessageModel> publishedMessages = messageLogService.getAllStoredMessages();
        MessageFormModel submittedPrev =
                model.containsAttribute(SUBMITTED_CATEGORY) || model.containsAttribute(SUBMITTED_MESSAGE) ?
                        MessageFormModel.builder()
                                .category((String) model.getAttribute(SUBMITTED_CATEGORY))
                                .message((String) model.getAttribute(SUBMITTED_MESSAGE))
                                .build()
                        : new MessageFormModel();
        model.addAttribute(MESSAGE_FORM_MODEL, submittedPrev);
        model.addAttribute("categories", categories);
        model.addAttribute("publishedMessages", publishedMessages);
        return "home";
    }

    @PostMapping("/messages")
    public String submitForm(@RequestParam("category") String category,
                             @RequestParam("message") String message,
                             RedirectAttributes redirectAttributes) {


        MessageFormModel sanitizedMessage = MessageFormModel.builder()
                .category(PLAIN_TEXT_SANITIZE_POLICY.sanitize(category))
                .message(PLAIN_TEXT_SANITIZE_POLICY.sanitize(message))
                .build();

        try {
            MessageFormValidator.validate(sanitizedMessage);
            messageLogService.processIncomingMessage(sanitizedMessage);
            redirectAttributes.addFlashAttribute("successMessage", "Message for category " + category + " saved successfully!");
        } catch (NotificationServiceException exception) {
            redirectAttributes.addFlashAttribute(SUBMITTED_CATEGORY, category);
            redirectAttributes.addFlashAttribute(SUBMITTED_MESSAGE, message);
            redirectAttributes.addFlashAttribute("errorMessage", "Could not save you message because: " + exception.getErrorDetails());
        }
        return "redirect:/";
    }
}
