package com.sallu.BillingApplication.exceptionhandlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler
    public String method(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("wentWrong", true);
        return "redirect:/dashboard";
    }
}
