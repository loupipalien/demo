package com.ltchen.demo.hadoop;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        int number = 10;
        //原始数二进制
        printInfo(number);
        number = number << 1;
        //左移一位
        printInfo(number);
        number = number >> 1;
        //右移一位
        printInfo(number);
        
        int n = 01;
        System.out.println(n);
        printInfo(n);
        System.out.println(n >>> 6 & 7);
        System.out.println(n >>> 3 & 7);
        System.out.println(n & 7);
        System.out.println(n >>> 9 & 1);
    }
    
    /**
     * 输出一个int的二进制数
     * @param num
     */
    private static void printInfo(int num){
        System.out.println(Integer.toBinaryString(num));
    }
}
