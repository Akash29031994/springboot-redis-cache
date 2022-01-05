package com.example.redis.springbootrediscache;


import org.apache.commons.lang3.RandomStringUtils;

public class Tester {

    public static void main(String[] args) {
            int i = 10;
            while (i > 0) {
                System.out.println(RandomStringUtils.randomAlphanumeric(5));
                i -= 1;
            }
    }

}
