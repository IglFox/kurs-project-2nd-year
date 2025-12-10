package edu.com.appartmentapp.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Кастомный контейнер, аналогичный vector из STL
 * Реализует динамический массив с автоматическим расширением
 */
public class CustomContainer<T> implements Serializable {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;  // Внутренний массив для хранения элементов
    private int size;           // Текущее количество элементов

    public CustomContainer() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // Добавление элемента
    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    // Получение элемента по индексу
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    // Удаление элемента по индексу
    public void remove(int index) {
        checkIndex(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;  // Помогаем сборщику мусора
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    // Создание итератора для контейнера
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    // Вспомогательные методы
    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // Внутренний класс итератора
    private class CustomIterator implements Iterator<T> {
        private int cursor = 0;     // Текущая позиция
        private int lastRet = -1;   // Индекс последнего возвращенного элемента

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException();
            }
            lastRet = cursor;
            return (T) elements[cursor++];
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            CustomContainer.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }
}