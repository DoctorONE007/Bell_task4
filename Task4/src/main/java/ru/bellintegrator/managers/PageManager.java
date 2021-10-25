package ru.bellintegrator.managers;

import ru.bellintegrator.pages.MarketPage;
import ru.bellintegrator.pages.YandexPage;


/**
 * Класс для управления страничками
 */
public class PageManager {

    /**
     * Менеджер страничек
     */
    private static PageManager pageManager;

    /**
     * Стартовая страничка
     */
    private MarketPage marketPage;
    /**
     * Старница яндекса
     */
    private YandexPage yandexPage;



    /**
     * Конструктор специально был объявлен как private (singleton паттерн)
     *
     * @see PageManager#getPageManager()
     */
    private PageManager() {
    }

    /**
     * Ленивая инициализация PageManager
     *
     * @return PageManager
     */
    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    /**
     * Ленивая инициализация {@link MarketPage}
     *
     * @return MarketPage
     */
    public MarketPage getMarketPage() {
        if (marketPage == null) {
            marketPage = new MarketPage();
        }
        return marketPage;
    }
    /**
     * Ленивая инициализация {@link YandexPage}
     *
     * @return YandexPage
     */
    public YandexPage getYandexPage() {
        if (yandexPage == null) {
            yandexPage = new YandexPage();
        }
        return yandexPage;
    }

}
