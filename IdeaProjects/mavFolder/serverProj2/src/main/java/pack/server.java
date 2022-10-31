package pack;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class server {
    public static void main(String[] args) throws IOException {
        int count = 1;//счетчик
        ServerSocket serverSocket  = new ServerSocket(12000);//серверный сокет с портом коннекта
        Socket socket;//сокет подключения с клиентом
        while (count < 6) {//пока верно
            socket = serverSocket.accept();//создаю сокет для подключения и ожидаю подключения
            System.out.println("Client " + count + " entered ");//отображаю вход клиента и его номер
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());//открываю выводящий поток
            osw.write("HTTP/1.0 200 OK\n" +
                    "Content-type: text/html\n" +
                    "\n" + "<h1>client " + count + "</h1>");
            count++;//уеличиваю счетчик
            osw.flush();
            osw.close();//закрываю поток
            socket.close();//закрываю сокет
            if (count == 6) {
                serverSocket.close();
            }
        }
    }
}
