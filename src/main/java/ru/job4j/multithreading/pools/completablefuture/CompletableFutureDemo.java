package ru.job4j.multithreading.pools.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    /**
     * Здесь мы воспользовались методом
     * {@link CompletableFuture#runAsync}.
     * Данный метод просто выполнит действие,
     * не возвращая результата.
     */
    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вернулся!");
                }
        );
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Мам/Пам, я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я купил " + product);
                    return product;
                }
        );
    }

    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * {@link CompletableFuture#thenRun} -
     * данный метод является callback-
     * методом. Callback-метод – это метод,
     * который будет вызван после выполнения
     * асинхронной задачи.
     *
     * <p>Обрати внимание, что все эти
     * методы (помимо thenRun() еще
     * thenApply() и thenAccept()) также
     * возвращают {@link CompletableFuture}.
     */
    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Сын: я мою руки");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Сын: Я помыл руки");
        });
        iWork();
    }

    /**
     * В отличие от
     * {@link CompletableFuture#thenRun},
     * этот метод имеет доступ к результату
     * CompletableFuture.
     */
    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * Этот метод принимает
     * {@link java.util.function.Function}.
     * Также имеет доступ к результату.
     * Как раз благодаря этому, мы можем
     * произвести преобразование
     * полученного результата.
     * Например, вы хотите, чтобы после того,
     * как сын принес молоко,
     * налил вам его в кружку.
     *
     * <p>Однако результат преобразования
     * станет доступным только при вызове get()!
     */
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());
    }

    /**
     * Для совмещения двух объектов
     * {@link CompletableFuture} можно
     * использовать thenCompose(), thenCombine().
     *
     * <p>{@link CompletableFuture#thenCompose}
     * используется, если действия зависимы.
     * Т.е. сначала должно выполниться одно,
     * а только потом другое. Например,
     * вам принципиально, чтобы сын сначала
     * выбросил мусор, а только потом
     * сходил за молоком.
     */
    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = goToTrash().thenCompose(a -> buyProduct("Milk"));
        result.get();  /*wait calculations*/
    }

    /**
     * {@link CompletableFuture#thenCombine}
     * Данный метод используется, если действия
     * могут быть выполнены независимо друг от друга.
     * Причем в качестве второго аргумента,
     * нужно передавать BiFunction – функцию,
     * которая преобразует результаты двух
     * задач во что-то одно. Например,
     * первого сына вы посылаете выбросить мусор,
     * а второго сходить за молоком.
     */
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    /**
     * Если задач много, то совместить их
     * можно с помощью методов allOf() и anyOf().
     * @param name имя того, кто моет руки.
     */
    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", моет руки");
        });
    }

    /**
     * {@link CompletableFuture#allOf}
     * Метод возвращает CompletableFuture<Void>,
     * при этом обеспечивает выполнение
     * всех задач. Например, вы зовете всех
     * членов семью к столу. Надо дождаться
     * пока все помоют руки!
     */
    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * В данном методе создаем объект
     * {@link CompletableFuture},
     * который вернет результат.
     * Для этого используем ранее изученный
     * {@link CompletableFuture#supplyAsync}.
     * @param name имя того, кто моет руки.
     */
    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", моет руки";
        });
    }

    /**
     * {@link CompletableFuture#anyOf}
     * Метод возвращает CompletableFuture<Object>.
     * Результатом будет первая выполненная
     * задача. На том же примере мы можем,
     * например, узнать, кто сейчас моет руки.
     * Результаты запуск от запуска будут различаться.
     */
    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws Exception {
        /*Пример с runAsync(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.runAsyncExample();*/

        /*Пример с supplyAsync(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.supplyAsyncExample();*/

        /*Пример с thenRun(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.thenRunExample();*/

        /*Пример с thenAccept(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.thenAcceptExample();*/

        /*Пример с thenApply(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.thenApplyExample();*/

        /*Пример с thenCompose(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.thenComposeExample();*/

        /*Пример с thenCombine(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.thenCombineExample();*/

        /*Пример с allOf(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.allOfExample();*/

        /*Пример с anyOf(). Чтобы посмотреть - раскомментируй нужный метод.*/
        /*CompletableFutureDemo.anyOfExample();*/
    }
}
