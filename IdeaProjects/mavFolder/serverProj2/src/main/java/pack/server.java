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
        //для поиска(GET в начале строки и HTTP, а также названия файла)
        Pattern pattern1 = Pattern.compile ("(C:).+(.txt)");//создаю объект pattern1 типа Pattern на основе реджекс
        while (count < 5) {//пока верно
            clientSocket = serverSocket.accept();//создаю сокет для подключения и ожидаю подключения
            System.out.println("Client " + (++count) + " entered ");//отображаю вход клиента и его номер

            BufferedReader isr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //открываю входящий поток
            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));//открываю выводящий поток

            String request = isr.readLine();//считываю входящую инфу
            String filePath = "";
            Matcher matcher1 = pattern1.matcher(request);//создаю объект matcher1 типа Matcher на основе поступившей от клиента информации
            if(matcher1.find()) {
                filePath = matcher1.group();
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));//открываю поток для чтения файла по адресу filePath
                String s;
                s = br.readLine();
                if ( s != null ) {
                    osw.write("HTTP/1.0 200 OK\n" +
                            "Content-type: text/html\n" +
                            "Content-length: " +
                            request.length() + "\n" +
                            "<h1>Welcome, client " + count + "</h1>\n\n\n" + s);//сообщаю данные о запросе клиенту
                }
                br.close();//закрываю поток
            } catch (FileNotFoundException ex) {
                System.out.println("Исключение: " + ex);
                osw.write("HTTP/1.0 404 Not Found\n" +
                        "Content-type: text/html\n" +
                        "Content-length: " +
                        request.length() + "\n" +
                        "<h1>Welcome, client " + count + "\n" +
                        " Pls check your request and try again</h1>\n\n\n");
            }

            osw.flush();//сбрасываю поток
            isr.close();//закрываю поток
            osw.close();//закрываю поток
            clientSocket.close();//закрываю сокет
        }
        serverSocket.close();//закрываю поток сервера(выключаю сервер)
    }
}
