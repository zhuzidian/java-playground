package zhu;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Runnable runnable = new ReentrantLockThread();
        new Thread(runnable, "a").start();
        new Thread(runnable, "b").start();
    }
}

class ReentrantLockThread implements Runnable{
    ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        try {
            lock.lock();
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + "こんにちは" + i);
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}