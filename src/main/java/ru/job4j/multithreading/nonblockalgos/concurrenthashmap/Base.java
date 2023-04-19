package ru.job4j.multithreading.nonblockalgos.concurrenthashmap;

/**
 * Данный класс описывает базовую
 * модель данных.
 */
public class Base {

    private final int id;

    /**
     * Данное поле определяет
     * достоверность версии в кеше.
     */
    private final int version;

    /**
     * Данное поле описывает имя.
     * Это поле бизнес модели.
     * В нашем случае оно одно.
     */
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public Base(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Base{"
                + "id=" + id
                + ", version=" + version
                + ", name='" + name + '\''
                + '}';
    }
}
