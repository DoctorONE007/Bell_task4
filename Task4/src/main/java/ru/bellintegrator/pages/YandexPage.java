package ru.bellintegrator.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.*;


public class YandexPage extends BasePage {


    @Step("Выбрать {name}")
    public MarketPage selectMarket(String name) {
        ElementsCollection listYandexMenu = $$x("//li[@class = 'services-new__list-item']//*[contains(text(),'Маркет')]");
        SelenideElement menuName = listYandexMenu.find(Condition.text(name));
        menuName.click();
        switchTo().window(1);
        Assertions.assertTrue(title().contains(name),"Ошибка при выборе маркета");
        return pageManager.getMarketPage();
    }


}
