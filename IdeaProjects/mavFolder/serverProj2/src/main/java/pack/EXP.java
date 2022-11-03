//package pack;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EXP {
//    public static void main(String[] args) {
//        File dir = new File("C:\\Users\\Администратор\\IdeaProjects\\mavFolder\\serverProj2\\src\\main\\java\\pack");
//        String req = "GET C:\\Users\\Администратор\\IdeaProjects\\mavFolder\\serverProj2\\src\\main\\java\\pack\\info.txt HTTP/1.0";
//        List<File> list = new ArrayList<>();
//        for (File file : dir.listFiles()){
//             list.add(file);
//        }
//        String[] array = new String[list.size()];
//        for (int i = 0; i < array.length; i++) {
//            array[i] = list.get(i).toString();
//        }
//        for (int i = 0; i < array.length; i++) {
//            if (req.contains(array[i])) {
//
//            }
//        }
////        String[] array = new String[list.size()];
////        array = list.toArray(array);
//
//    }
//}
