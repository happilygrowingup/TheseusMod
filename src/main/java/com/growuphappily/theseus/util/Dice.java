package com.growuphappily.theseus.util;

import java.util.Random;

public class Dice {

    public static int onedX(int X){
        Random rand = new Random();
        return rand.nextInt(X) +1;
    }

    public static int[] NdX(int N,int X){
        int[] list = new int[N];
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            list[i] = rand.nextInt(X) +1;
        }
        return list;
    }
}
