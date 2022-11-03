package pack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class server {
    public static void main(String[] args) throws IOException {
        int count = 0;//счетчик
        ServerSocket serverSocket  = new ServerSocket(12000);//серверный сокет с портом коннекта
        Socket clientSocket;//сокет подключения с клиентом
        //для поиска(GET в начале строки и HTTP, а также названия файла)
        Pattern pattern1 = Pattern.compile ("^(GET){1,1}\\b\\s.+\\b(info\\.txt){1,1}\\b\\s(HTTP\\/){1,1}.+$");//создаю объект pattern1 типа Pattern на основе реджекс
        Pattern pattern2 = Pattern.compile ("^(GET){1,1}\\b\\s.+\\b(file1\\.txt){1,1}\\b\\s(HTTP\\/){1,1}.+$");//создаю объект pattern2 типа Pattern на основе реджекс
        Pattern pattern3 = Pattern.compile ("^(GET){1,1}\\b\\s.+\\b(file2\\.txt){1,1}\\b\\s(HTTP\\/){1,1}.+$");//создаю объект pattern3 типа Pattern на основе реджекс

        File dir = new File("C:\\Users\\Администратор\\IdeaProjects\\mavFolder\\serverProj2\\src\\main\\java\\pack");//путь к директории с файлами

        while (count < 5) {//пока верно
            clientSocket = serverSocket.accept();//создаю сокет для подключения и ожидаю подключения
            System.out.println("Client " + (++count) + " entered ");//отображаю вход клиента и его номер

            BufferedReader isr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //открываю входящий поток
            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));//открываю выводящий поток

            String request = isr.readLine();//считываю входящую инфу

            Matcher matcher1 = pattern1.matcher(request);//создаю объект matcher1 типа Matcher на основе поступившей от клиента информации
            Matcher matcher2 = pattern2.matcher(request);//создаю объект matcher2 типа Matcher на основе поступившей от клиента информации
            Matcher matcher3 = pattern3.matcher(request);//создаю объект matcher3 типа Matcher на основе поступившей от клиента информации

            if(matcher1.find()||matcher2.find()||matcher3.find()) { //определяю метод http
                //если не один из трех GET, то ошибка е500
                List<File> fileList = new ArrayList<>();//список для файлов из директории
                for (File file : dir.listFiles()){//перебираю массив объектов(путей) к файлам в директории dir
                    fileList.add(file);//переписываю пути доступа к файлам
                }
                String[] fileArray = new String[fileList.size()];//массив строк размером со список fileList
                for (int i = 0; i < fileArray.length; i++) {
                    fileArray[i] = fileList.get(i).toString();//переписываю все пути к файла из списка в массив
                }
                for (int i = 0; i < fileArray.length; i++) {//ищу путь к файлу из запроса
                    if (request.contains(fileArray[i])) {//если путь к файлу из запроса  есть в списке
                        String filePath = fileArray[i];//копирую сюда путь к файлу
                        osw.write("HTTP/1.0 200 OK\n" +
                                "Content-type: text/html\n" +
                                "Content-length: " +
                                request.length() +"\n" +
                                "<h1>Welcome, client " + count  + "</h1>\n\n\n");//сообщаю данные о запросе клиенту
                        BufferedReader br = new BufferedReader(new FileReader(filePath));//открываю поток для чтения файла по адресу filePath
                        String s;
                        while((s = br.readLine()) != null) {//считываю файл по адресу filePath
                            osw.write(s);//передаю клиенту
                        }
                        br.close();//закрываю поток
                    }
                }
            } else {//если метод не тот - ошибка
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
        serverSocket.close();//закрываю поток сервера(выключаю сервер)
    }
}
