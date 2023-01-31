package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

        @Override
        public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                        var process = new char[] {'-', '\\', '|', '/'};
                        for (char c : process) {
                                System.out.print("\r load: " + c);
                                try {
                                        Thread.sleep(500);
                                } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                }
                        }
                }
        }

        public static void main(String[] args) throws InterruptedException {
                Thread progress = new Thread(new ConsoleProgress());
                progress.start();
                Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
                progress.interrupt();
        }
}
