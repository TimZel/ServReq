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
        Pattern pattern1 = Pattern.compile ("^(GET){1,1}\\b\\s.+\\b(info\\.txt){1,1}\\b\\s(HTTP\\/){1,1}.+$");
        //создаю объект pattern1 типа Pattern на основе реджекс
        //для поиска(GET в начале строки и HTTP)
        while (count < 5) {//пока верно
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
                        "<h1>Welcome, client " + count  + "</h1>\n\n\n");
                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Администратор\\IdeaProjects\\mavFolder\\serverProj2\\src\\main\\java\\pack\\info.txt"));
                String s;
                while((s = br.readLine()) != null) {//считываю файл по адресу
                    osw.write(s);//передаю клиенту
                }
                br.close();//закрываю поток
            } else {
                osw.write("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "   <title>500 Internal Server Error</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "   <h1>Internal Server Error</h1>\n" +
                        "   <p>Your browser sent a request that this server could not understand.</p>\n" +
                        "   <p>The request line contained invalid characters following the protocol string.</p>\n" +
                        "</body>\n" +
                        "</html>");
            }
            osw.flush();//сбрасываю поток

            isr.close();//закрываю поток
            osw.close();//закрываю поток

            clientSocket.close();//закрываю сокет
        }
        serverSocket.close();
    }
}
