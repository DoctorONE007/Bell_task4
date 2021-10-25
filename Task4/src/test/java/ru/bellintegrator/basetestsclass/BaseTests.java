package ru.bellintegrator.basetestsclass;


import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.BeforeEach;
import ru.bellintegrator.managers.PageManager;


import static com.codeborne.selenide.Selenide.open;


public class BaseTests {

    /**
     * Менеджер страничек
     *
     * @see PageManager#getPageManager()
     */
    protected PageManager app = PageManager.getPageManager();


    @BeforeEach
    public void beforeEach() {
        Configuration.timeout = 6000;
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;

        open("https://yandex.ru/");
    }

}
