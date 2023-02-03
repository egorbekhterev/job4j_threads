package ru.job4j.threadlocal;

public class SecondThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.t1.set("Это поток 2.");
        System.out.println(ThreadLocalDemo.t1.get());
    }
}
