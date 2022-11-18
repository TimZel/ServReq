import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    public static final String CRLF = "\r\n";
    public static String method;
    public static String path;
    public static String http;
    public static String contentType;
    public static HashMap<String, String> mapHeadersValues = new HashMap<>();
    public static String body;
    public static String[] words;
    public static final Pattern patternBody = Pattern.compile("(^[^\\n\\p{Lu}].+)");
    public static final Pattern patternHeaderValue = Pattern.compile("(^\\p{Lu}[^\\s].+\\:)(\\s.*)");
    public Request(String method, String path, String http, String contentType, HashMap<String, String> mapHeadersValues, String body) {
        this.method = method;
        this.path = path;
        this.http = http;
        this.contentType = contentType;
        this.mapHeadersValues = mapHeadersValues;
        this.body = body;
    }
    public static Request parse(InputStream isr) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(isr));//принимаю поток
        char[] charArray = new char[500];//создаю чар-массив для считывания потока
        int actualBuffered = reader.read(charArray,0,500);//считываю данные в массив от начала до конца и получаю актуальное размер
        String request = String.valueOf(charArray, 0, actualBuffered);//преобразую в строку
        words = request.split(CRLF);//преобразую стринг-массив, рассекая на КРЛФ
        //создаю переменные для обработки данных
        String pathExeHttp = "";
        HashMap<String, String> mapHeadersValues = new HashMap<>();
        String contentType = "";
        String body = "";
        for (int i = 0; i < words.length; i++) {
            Matcher matcherBody = patternBody.matcher(words[i]);
            Matcher matcherHeaderValue = patternHeaderValue.matcher(words[i]);
            pathExeHttp = words[0];
            if(matcherHeaderValue.find()) {
                mapHeadersValues.put(matcherHeaderValue.group(1), matcherHeaderValue.group(2));
                if(matcherHeaderValue.group(1).equals("Content-Type:")) {
                    contentType = matcherHeaderValue.group(2);
                }
            }
            if(matcherBody.find()) {
                body = words[i];
            }
        }
        String[] pathExeHttpArray = pathExeHttp.split(" ");
        String method = "";
        String path = "";
        String http = "";
        for(int i = 0; i < pathExeHttpArray.length; i++) {
            method = pathExeHttpArray[0];
            path = pathExeHttpArray[1];
            http = pathExeHttpArray[2];
        }
        return new Request(method, path, http, contentType, mapHeadersValues, body);
    }
}







