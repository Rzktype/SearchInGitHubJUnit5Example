import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class SelenideRepositorySearchTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https:github.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;

    }

    @Test
    void shouldFindSelenideRepository() {
        // открыть главную страницу
        open("https:github.com");
        //ввести в поле поиска selenide и нажать enter
        $(".octicon-search").click();
        $("#query-builder-test").setValue("selenide").pressEnter();
        // найти и перейти на ссылку в репозиторий selenide
        $(byTagAndText("em", "selenide")).click();
        // перейти в wiki
        $("#wiki-tab").click();
        // раскрыть Pages и перейти в SoftAssertions
        $("#wiki-pages-box").$(withText("Show")).click();
        $(byText("SoftAssertions")).shouldHave(text("SoftAssertions")).click();
        // найти примеры кода для JUnit5
        $(".repository-content ").shouldHave(text("Using JUnit5 extend test class:"));
        $(".markdown-body").shouldHave(text(
                """
                        @ExtendWith({SoftAssertsExtension.class})
                        class Tests {
                          @Test
                          void test() {
                            Configuration.assertionMode = SOFT;
                            open("page.html");
                                                
                            $("#first").should(visible).click();
                            $("#second").should(visible).click();
                          }
                        }
                        """));
        // Второй пример
        $(".repository-content ").shouldHave(text("Or register extension inside test class:"));
        $(".markdown-body").shouldHave(text("""
                class Tests {
                  @RegisterExtension
                  static SoftAssertsExtension softAsserts = new SoftAssertsExtension();
                                
                  @Test
                  void test() {
                    Configuration.assertionMode = SOFT;
                    open("page.html");
                                
                    $("#first").should(visible).click();
                    $("#second").should(visible).click();
                  }
                }
                """));


    }

}
