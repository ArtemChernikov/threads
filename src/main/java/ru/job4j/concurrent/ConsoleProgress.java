package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        char[] process = {'_', '\\', '|', '/'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (char c : process) {
                    System.out.print("\r Loading..." + c);
                    Thread.sleep(500);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
