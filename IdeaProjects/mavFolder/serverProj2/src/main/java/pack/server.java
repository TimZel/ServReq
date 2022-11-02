package pack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class server {
    public static void main(String[] args) throws IOException {
        int count = 0;//счетчик
        ServerSocket serverSocket  = new ServerSocket(12000);//серверный сокет с портом коннекта
        Socket clientSocket;//сокет подключения с клиентом
        Pattern pattern1 = Pattern.compile ("^GET.+");//создаю объект pattern1 типа Pattern на основе реджекс для поиска (GET в начале строки и рандомный текст после)
        while (count < 6) {//пока верно
            clientSocket = serverSocket.accept();//создаю сокет для подключения и ожидаю подключения
            System.out.println("Client " + (++count) + " entered ");//отображаю вход клиента и его номер
            BufferedReader isr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //открываю входящий поток
            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));//открываю выводящий поток
            String request = isr.readLine();//считываю входящую инфу
            Matcher matcher1 = pattern1.matcher(request);//создаю объект matcher1 типа Matcher на основе поступившей от клиента информации
            if(matcher1.find()) { //определяю метод http
                //если не GET, то ошибка е500
                osw.write("HTTP/1.0 200 OK\n" +
                        "Content-type: text/html\n" +
                        "Content-length: " +
                        request.length() +"\n" +
                        "<h1>Welcome, client " + count + "</h1>");
            } else {
                osw.write("500\nInternal Server Error");
            }
            osw.flush();//сбрасываю поток

            isr.close();//закрываю поток
            osw.close();//закрываю поток

            clientSocket.close();//закрываю сокет
            if (count == 6) { //останавливаю сервер
                serverSocket.close();
            }
        }
    }
}
