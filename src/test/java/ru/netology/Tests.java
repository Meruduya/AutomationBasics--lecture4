package ru.netology;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.format.DateTimeFormatter;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selectors.*;

class Tests {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        Selenide.open("http://localhost:9999");
        SelenideElement form = $$("form").find(Condition.visible);

        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(planningDate);
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79272000000");
        form.$("[data-test-id=agreement]").click();
        form.$("[class='button__text']").click();
        $("[data-test-id='notification']")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }
}

