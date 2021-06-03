package AllureTest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class AllureReportsTest {

    private static final String BASE_URL = "https://github.com";
    private static final String SEARCH = "allure";
    private static final String REPOSITORY = "allure-framework/allure2";
    private static final int ISSUE_NUMBER = 1294;
    private WebSteps steps = new WebSteps();


    @Test
    @Feature("Issues")
    @Owner("Ivan")
    @Story("Создание Issues")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Github", url = "https://github.com")
    @DisplayName("Тест на проверку создания Issue")
    public void testStepIssueSearch() {
        step("Открываем страницу", (s) -> {
            s.parameter("Url", BASE_URL);
            open(BASE_URL);
        });
        step("Ищем репозиторий" + SEARCH, (s) -> {
            s.parameter("repository", SEARCH);
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(SEARCH);
            $(".header-search-input").submit();
        });
        step("Переходим в репозиторий" + REPOSITORY, (s) -> {
            s.parameter("repository", REPOSITORY);
            $(By.linkText("allure-framework/allure2")).click();
        });
        step("Открываем таб Issues в репозитории", () ->
                $(withText("Issues")).click()
        );
        step("Проверяем что Issues с номером" + ISSUE_NUMBER + "существует", (s) -> {
            s.parameter("number", ISSUE_NUMBER);
            $(withText("#1294")).should(Condition.visible);
        });
    }


    @Test
    public void testSelenideIssue() {
        open(BASE_URL);
        $(".header-search-input").setValue(SEARCH).submit();
        $(By.linkText("allure-framework/allure2")).click();
        $(withText("Issues")).click();
        $(withText("#" + ISSUE_NUMBER)).should(Condition.visible);

    }

    @Test
    public void testAnnotatedStepsIssue() {
        steps.openThePage();
        steps.lookingRepository("allure");
        steps.goRepository("allure-framework/allure2");
        steps.openIssuesTabRepository();
        steps.checkIssuesNumberExists(1294);

    }
}
