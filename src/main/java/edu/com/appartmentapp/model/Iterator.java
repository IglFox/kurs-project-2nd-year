package edu.com.appartmentapp.model;

/**
 * Интерфейс итератора для обхода элементов контейнера
 * Обеспечивает навигацию по коллекции
 */
public interface Iterator<T> {
    boolean hasNext();  // Проверяет, есть ли следующий элемент
    T next();           // Возвращает следующий элемент
    void remove();      // Удаляет текущий элемент
}