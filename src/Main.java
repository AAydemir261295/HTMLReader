import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        String url = "https://www.google.com/search?q=%D1%88%D0%B5%D1%81%D1%82%D0%B8%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA&sxsrf=AOaemvIJbpBy0-16B5JzDHVN89gL621kZQ:1638458353799&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjx0qCBtcX0AhWLtYsKHTGTDRwQ_AUoAXoECAIQAw&biw=1448&bih=782&dpr=1.25";

        Reader reader = new Reader();
        ArrayList<Tag> pics = reader.readHTML(readURL(url)).findByClass("yWs4tf");
        ArrayList<Tag> tmp = reader.readHTML(readURL(url)).getHTMLblock();
        pics.forEach(x -> System.out.println(x.getTagStroke())); // вывод блока по классу
        tmp.forEach(x -> System.out.println(x.getTagStroke()));
    }

    public static String readURL(String url) {
        String fileContents = "";
        String currentLine = "";
        try {
            URL link = new URL(url);
            URLConnection connection = link.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0"); // избегаем 403 ошибки
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            fileContents = reader.readLine();
            while (currentLine != null) {
                currentLine = reader.readLine();
                fileContents += "\n" + currentLine;
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error Message", JOptionPane.OK_OPTION);
            e.printStackTrace();

        }
        return fileContents;
    }
}
