package com.fji.notification.controller;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.model.dto.UserShowMessageModel;
import com.fji.notification.service.CategoriesService;
import com.fji.notification.service.MessageLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.fji.notification.exception.NotificationServiceExceptionCode.INVALID_LENGTH;
import static com.fji.notification.exception.NotificationServiceExceptionCode.INVALID_VALUE;
import static com.fji.notification.utils.TestConstants.MESSAGE_WITH_200_CHARACTERS;
import static com.fji.notification.validation.MessageFormValidator.ALLOWED_MESSAGE_LENGTH;
import static com.fji.notification.validation.MessageFormValidator.CATEGORY_FIELD_NAME;
import static com.fji.notification.validation.MessageFormValidator.MESSAGE_FIELD_NAME;
import static com.fji.notification.utils.TestConstants.MESSAGE_MORE_THAN_200_CHARACTERS;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NotificationMessageController.class)
class NotificationMessageControllerTest {

    public static final String SPORT = "SPORT";
    public static final String FINANCE = "FINANCE";
    public static final String MESSAGE = "MESSAGE";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriesService categoriesService;

    @MockitoBean
    private MessageLogService messageLogService;


    private static Stream<Arguments> homePageTestCases() {
        return Stream.of(
                Arguments.of(asList(SPORT, FINANCE), emptyList())
//                Arguments.of(asList("SPORT", "FINANCE"), singletonList(UserShowMessageModel.builder().category("SPORT").message("Message").createdAt(LocalDateTime.now().toString())))
        );
    }
    @ParameterizedTest
    @MethodSource("homePageTestCases")
    void showUserInterfaceTest(List<String> categories, List<UserShowMessageModel> showMessageModelList) throws Exception {
        when(categoriesService.getAllCategoryNames()).thenReturn(categories);
        when(messageLogService.getAllStoredMessages()).thenReturn(showMessageModelList);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("messageFormModel", "categories", "publishedMessages"))
                .andExpect(model().attribute("messageFormModel", new MessageFormModel()))
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attribute("publishedMessages", showMessageModelList));
    }


    private static Map<String , Object> getRedirectValues(String prevSubmittedCategory, String prevSubmittedMessage) {
        Map<String, Object> redirectAttributes = new HashMap<>();
        redirectAttributes.put("submittedCategory", prevSubmittedCategory);
        redirectAttributes.put("submittedMessage", prevSubmittedMessage);
        return redirectAttributes;
    }
    private static Stream<Arguments> homePageTestRedirectCases() {
        return Stream.of(
                Arguments.of(getRedirectValues(SPORT, MESSAGE), SPORT, MESSAGE),
                Arguments.of(getRedirectValues(EMPTY, MESSAGE), EMPTY, MESSAGE),
                Arguments.of(getRedirectValues(EMPTY, MESSAGE), null, MESSAGE),
                Arguments.of(getRedirectValues(SPORT, EMPTY), SPORT, EMPTY),
                Arguments.of(getRedirectValues(SPORT, EMPTY), SPORT, null)
        );
    }
    @ParameterizedTest
    @MethodSource("homePageTestRedirectCases")
    void showUserInterfaceRedirectParametersTest(Map<String, Object> redirectValues,
                                             String preCategory,
                                             String preMessage) throws Exception {
        if(preCategory == null ) redirectValues.remove("submittedCategory");
        if(preMessage == null ) redirectValues.remove("submittedMessage");
        mockMvc.perform(get("/")
                        .flashAttrs(redirectValues)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("messageFormModel", MessageFormModel.builder().category(preCategory).message(preMessage).build()));
    }

    @Test
    void submitFormSuccessTest() throws Exception {
        String category = CategoryEnum.FINANCE.getName();
        mockMvc.perform(post("/messages")
                        .param("category", category)
                        .param("message", MESSAGE_WITH_200_CHARACTERS)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/")) // Verify redirect URL and parameter
                .andExpect(flash().attributeExists("successMessage"))
                .andExpect(flash().attribute("successMessage", "Message for category " + category + " saved successfully!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Wrong Category"})
    void submitFormCategoryValidationFailuresTest(String categoryValue) throws Exception {
        mockMvc.perform(post("/messages")
                        .param("category", categoryValue)
                        .param("message", "message")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/")) // Verify redirect URL and parameter
                .andExpect(flash().attributeExists("errorMessage", "submittedCategory", "submittedMessage"))
                .andExpect(flash().attribute("errorMessage", "Could not save you message because: " + MessageFormat.format(INVALID_VALUE.getMessage(), categoryValue, CATEGORY_FIELD_NAME)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void submitFormMessageValidationFailuresTest(String messageValue) throws Exception {
        mockMvc.perform(post("/messages")
                        .param("category", CategoryEnum.SPORT.getName())
                        .param("message", messageValue)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/")) // Verify redirect URL and parameter
                .andExpect(flash().attributeExists("errorMessage", "submittedCategory", "submittedMessage"))
                .andExpect(flash().attribute("errorMessage", "Could not save you message because: " + MessageFormat.format(INVALID_VALUE.getMessage(), messageValue, MESSAGE_FIELD_NAME)));
    }

    @Test
    void submitFormMessageValidationFailuresTest() throws Exception {
        mockMvc.perform(post("/messages")
                        .param("category", CategoryEnum.SPORT.getName())
                        .param("message", MESSAGE_MORE_THAN_200_CHARACTERS)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/")) // Verify redirect URL and parameter
                .andExpect(flash().attributeExists("errorMessage", "submittedCategory", "submittedMessage"))
                .andExpect(flash().attribute("errorMessage", "Could not save you message because: " + MessageFormat.format(INVALID_LENGTH.getMessage(), MESSAGE_FIELD_NAME, valueOf(MESSAGE_MORE_THAN_200_CHARACTERS.length()), ALLOWED_MESSAGE_LENGTH)));
    }
}