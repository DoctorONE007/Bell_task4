package ru.bellintegrator.pages;

import ru.bellintegrator.managers.PageManager;



/**
 * Базовый класс всех страничек
 */
public class BasePage {


    /**
     * Менеджер страничек
     *
     * @see PageManager
     */
    protected PageManager pageManager = PageManager.getPageManager();

}
