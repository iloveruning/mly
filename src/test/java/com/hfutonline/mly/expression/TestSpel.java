package com.hfutonline.mly.expression;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author chenliangliang
 * @date 2018/2/23
 */
public class TestSpel {

    @Test
    public void test(){

        Lock lock=new ReentrantLock();
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        readWriteLock.writeLock().tryLock();


    }
}
