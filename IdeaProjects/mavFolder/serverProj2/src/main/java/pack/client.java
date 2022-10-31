package pack;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 12000);//создаю сокет подключения к серверу
        //с заданными портом и айпи
        Scanner in = new Scanner(client.getInputStream());//считываю входящий поток
        while(in.hasNext()) {
            System.out.println(in.nextLine());//вывожу инфо в консоль
        }
        in.close(); //закрываю потоки
        client.close();
    }
}