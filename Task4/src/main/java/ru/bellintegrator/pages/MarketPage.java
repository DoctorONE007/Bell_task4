package ru.bellintegrator.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.title;

/**
 * Реализация домашней страницы
 */
public class MarketPage extends BasePage {

    /**
     * Функция клика на любой пункт меню
     *
     * @param nameBaseMenu - наименование меню
     * @return HomePage - т.е. остаемся на этой странице
     */
    @Step("Открытие меню {nameBaseMenu}")
    public MarketPage selectBaseMenu(String nameBaseMenu) {
        closeAdv($x("//div[@data-apiary-widget-name='@MarketNode/DailyBonusesPopup']//*[local-name() = 'path']"), null);
        ElementsCollection listBaseMenu = $$x("//div[@data-zone-name= 'menu']//div[@data-zone-name='category-link']");
        SelenideElement menuItem = listBaseMenu.find(text(nameBaseMenu));
        menuItem.click();
        Assertions.assertTrue(title().contains(nameBaseMenu), "Ошибка при переходе в меню");
        return this;
    }

    /**
     * Функция клика на любое подменю
     *
     * @param nameSubMenu - наименование подменю
     * @return MarketPage
     */
    @Step("Открыть подменю {nameSubMenu}")
    public MarketPage selectSubMenu(String nameSubMenu) {
        ElementsCollection listSubMenu = $$x("//div[@data-apiary-widget-name='@MarketNode/NavigationTree']//ul[@data-autotest-id='subItems']/li");
        SelenideElement menuItem = listSubMenu.find(text(nameSubMenu));
        menuItem.click();
        Assertions.assertTrue(title().contains(nameSubMenu), "Ошибка при переходе в подменю");
        return this;
    }

    /**
     * Функция ввода цены
     *
     * @param min - минимальная цена
     * @param max - максимальная цена
     * @return MarketPage
     */
    @Step("Ввести цену от {min} до {max}")
    public MarketPage priceInput(String min, String max) {
        inputPrice($x("//input[@name='Цена от']"), min);
        inputPrice($x("//input[@name='Цена до']"), max);
        return this;
    }

    @Step("Выбрать чекбокс модели {nameField}")
    public MarketPage chooseModel(String operation, String nameField) {
        String booleanFlag = operation.equals("Поставить") ? "true" : "false";
        ElementsCollection modelsNameList = $$x("//div[@class='_3_phr']//legend[contains(text(),'Производитель')]/..//span");
        SelenideElement checkBox = modelsNameList.find(text(nameField));
        checkBox.click();
        Assertions.assertEquals(booleanFlag, checkBox.$x("./../..//input").attr("checked")
                , "CheckBox '" + nameField + "' не выполнился");
        return this;

    }

    /**
     * Выбор количества элементов на странице
     *
     * @param number - количество элементов
     * @return MarketPage
     */
    @Step("Установить количество показываемых элементов на страницу {number}")
    public MarketPage numberOnPage(String number) {
        $x("//span[contains(text(),'Показывать по')]/../..").click();
        $x("//button[contains(text(),'Показывать по')]").shouldHave(text(number)).click();
        ElementsCollection resultsAfterSearch = $$x("//div[@data-apiary-widget-name='@MarketNode/SearchResults']//h3");
        $x("//div[@class='_2Lvbi _1oZmP']").should(disappear);
        Assertions.assertEquals(String.valueOf(resultsAfterSearch.size()), number,
                "Количество элементов на странице не совпадает");
        return this;
    }

    @Step("Проверка первого элемента")
    public MarketPage findFirst() {
        ElementsCollection resultsAfterSearch = $$x("//div[@data-apiary-widget-name='@MarketNode/SearchResults']//h3");
        String firstSearch = resultsAfterSearch.get(0).getText().split("\\(")[0];
        input(firstSearch);
        Assertions.assertTrue(resultsAfterSearch.stream().anyMatch(x -> x.getText().contains(firstSearch)),
                "Ноутбук с именем " + firstSearch + " не найден");
        return this;

    }

    @Step("Проверка названий на всех страницах")
    public MarketPage checkName(String name) {
        ElementsCollection resultsAfterSearch = $$x("//div[@data-apiary-widget-name='@MarketNode/SearchResults']//h3");
        SelenideElement nextPageButton = $x("//a[@aria-label='Следующая страница']");
        try {

            do {
                if (!resultsAfterSearch.stream().allMatch(x -> x.getText().contains(name)))
                    Assertions.fail("Название не совпало на странице " + title());
                nextPageButton.click();
            } while (nextPageButton.isDisplayed());
        } catch (ElementNotFound ignore) {
        }
        return this;
    }


    /**
     * Ввод в строку поиска
     *
     * @param value - что вводить
     */
    private void input(String value) {
        SelenideElement searchField = $x("//form[@action='/search']//input[@type ='text']");
        searchField.click();
        searchField.clear();
        searchField.setValue(value);
        Assertions.assertEquals(value, searchField.getAttribute("value")
                , "Поле поиска было заполнено некорректно");
        $x("//form[@action='/search']//*[local-name() = 'span'][contains(text(),'Найти')]").click();
    }

    /**
     * Ввод цены
     *
     * @param element - поле
     * @param value   - что вводить
     */
    private void inputPrice(SelenideElement element, String value) {
        element.click();
        element.clear();
        element.setValue(value);
        Assertions.assertEquals(value, element.getAttribute("value")
                , "Поле цены было заполнено некорректно");
    }

    /**
     * Закрытие рекламы
     *
     * @param element    - кнопка закрытия окна рекламы
     * @param frameXPath - путь к фрейму
     */
    private void closeAdv(SelenideElement element, String frameXPath) {
        try {
            if (frameXPath != null)
                switchTo().frame($x(frameXPath));
            element.click();
        } catch (ElementNotFound ignore) {
        } finally {
            if (frameXPath != null)
                switchTo().parentFrame();
        }

    }

}
