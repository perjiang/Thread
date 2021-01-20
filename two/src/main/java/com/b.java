package com;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Scanner;

public class b {
    public static void main(String[] args) {
        int score[] = {2,3,5,6,7,1,4,9,8};
        for (int i = 0; i < score.length - 1; i++) {
            for (int j =0;j<score.length-1-i;j++){
                if (score[j]>score[j+1]){
                    int t = score[j];
                    score[j]=score[j+1];
                    score[j+1]=t;
                }
            }
        }

        for (int i = 1; i < score.length-1; i++) {
            System.out.println(score[i]);
        }


    }
}
