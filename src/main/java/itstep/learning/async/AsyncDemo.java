package itstep.learning.async;

import javax.management.ObjectName;

public class AsyncDemo {
    public void run() {
        System.out.println("Async Demo");
        //treadDemo();
        percentDemo();
    }

    private void threadDemo() {
        /*
        Многопоточность - программирование с использованием объектов системного типа Thread
        Объекты принимают в конструктор другие объекты функциональных интерфейсов
        В Java функциональным интерфейсом называют те, у которых декларирован только один метод
         */
        Thread thread = new Thread(
                new Runnable() {                //Анонимный тип, который имплементируют Runnable
                    @Override                       // переназначает его метод
                    public void run() {             // и инстанцируется (стаёт объектом)
                        System.out.println("Hello Thread");     // Традиционно для Java, создание нового
                        // объекта (thread) не создает сам потока,
                        // а только программную сущность
                        //
                    }
                });
        thread.start(); //асинхронный запуск
        //thread.run(); // синхронный запуск
        System.out.println("Hello Main");
    }

    private static double sum;
    private static final Object sumLock = new Object();

    private void percentDemo() {
        sum = 100.0;
        for (int i = 0; i <= 12; i++) {
            new Thread(new Rate(i)).start();
        }
    }

    private static class Rate implements Runnable {
        private final int month;

        public Rate(int month) {
            this.month = month;
        }

        @Override
        public void run() {
            System.out.println("Rate: " + month + " started");
            double percent = 0;
            try {
                Thread.sleep(500);
                percent = 10.0;
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                return;
            }
            synchronized (sumLock) {
                sum *= (1 + percent / 100.0);
                System.out.println("Rate: " + month + " finished with wum " + sum);
            }
        }
    }
}
/*
Синхронность - последовательное выполнение частей кода
----- ======
Асинхронность - любое отклонение синхронности
------  - - - - -     -- -- -
======  = = = = =     =  =  ===

Реализации:
- Многозадачность: использование объектов уровня ЯП/платформы(таких, как Promise, Task, Future, Coroutine)
- Многопоточность: использования системных ресурсов - потоков(какие они существуют в системе)
- Многопроцесность: использование системных ресурсов - процессов
- сетевые технологии
    = grid
    = network
 */
