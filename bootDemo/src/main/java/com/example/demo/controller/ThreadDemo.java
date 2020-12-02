package com.example.demo.controller;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2020-09-18 13:58
 */
public class ThreadDemo {
    public static void main(String args[]) {
        Thread t = new Thread() {

            @Override
            public void run() {
                pong();

            }
        };

        Thread t2 = new Thread() {

            @Override
            public void run() {
                pong2();

            }
        };

            t.start();
            t2.start();


        System.out.println("ping ");
    }

    static void pong() {
        System.out.println("pong ");

    }

    static void pong2() {
        System.out.println("pong2 ");

    }
}
