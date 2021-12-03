import java.util.ArrayList;

public class Reader {

    private ArrayList<Tag> HTMLblock = new ArrayList<>();

    public ArrayList<Tag> getHTMLblock() {
        return HTMLblock;
    }

    public Reader readHTML(String htmlString) {
        // Читаем и сохраняем HTML

        String[] splitted = htmlString.split(">");

        for (int i = 0; i < splitted.length; i++) {
            String str = splitted[i].trim();

            if (isContainOpenTag(str)) {   // Если открывающий тег имеется
                addStringToBlock(str);     // Добавляем строку в список HTML кода
                addOpenTag(str);           // Добавляем в Pair открвающий тег
                if (isContainClass(str)) { // Проверяем на наличие класса
                    getClass(str);         // Получаем и сохраняем класс в отдельную переменную
                }
            } else if (isContainCloseTag(str)) {   // Если строка с закрывающим тегом
                    addCloseTag(str);              // Добавляем закрывающий тег в Pair
                    addStringToBlock(str);         // Добавляем тег в список HTML кода
            }
        }
        return this;
    }

    public ArrayList<Tag> findByClass(String value) {
        //Находим блок тега по классу
        ArrayList<Tag> block = new ArrayList<>();

        for (int i = 0; i < HTMLblock.size() - 1; i++) {
            //Если строка содержит искомый класс
            if (HTMLblock.get(i).getElementClass().equals(value)) {
                // позиция закрытого тега в списке
                int closeTagPosition = HTMLblock.get(i).getPair().getClosePosition();
                // в случае если тег не содержит закрытого тега(Некоторые теги необязательны к закрытию)
                if (closeTagPosition == 0) {
                    block.add(HTMLblock.get(i));
                } else {
                    // если закрывающий тег имеется, сохраняем блок с первого вхождения 'i' по closePosition
                    for (int j = i; j <= closeTagPosition; j++) {
                        block.add(HTMLblock.get(j));
                    }
                }
            }
        }
        return block;
    }

    private void addCloseTag(String str) {
        String closeTag = getCloseTag(str);
        try {
            for (int i = HTMLblock.size() - 1; i >= 0; i--) {
                // Проверяем совпадает ли закрытый тег с открытым
                // Есть ли тег уже закрыт то ищем дальше не закрытый тег
                if (openTagEqualsClose(closeTag, i) && !(HTMLblock.get(i).getPair().tagIsClosed())) {
                    // Закрываем тег
                    closeIt(closeTag, i);
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println(e + "\nСтрока: " + str + "\n Тэг: " + closeTag + "\n Ошибка при закрытии тэга");
        }
    }

    private void closeIt(String closeTag, int j) {
        // Закрывает блок тегом
        HTMLblock.get(j).getPair().setClose(closeTag, HTMLblock.size());
    }

    private boolean openTagEqualsClose(String closeTag, int j) {
        // Сравнивает сходство открывающего с закрывающим тегом
        if (HTMLblock.get(j).getPair().getOpen() == null) {
            return false;
        } else if (HTMLblock.get(j).getPair().getOpen().equals(closeTag)) {
            return true;
        }
        return false;
    }

    private boolean isContainClass(String str) {
        // Проверка наличия класса в тэге
        return str.contains("class=\"");
    }

    private void getClass(String str) {
        // Находит элемент class в теге
        String[] splitted = str.split("class=\"");
        String value = splitted[1].split("\" ")[0].replaceAll("[\"><]", "");
        saveClass(value);
    }

    private void saveClass(String classValue){
        // Сохраняет класс тэга
        HTMLblock.get(HTMLblock.size() - 1).addClass(classValue);
    }

    private void addOpenTag(String str) {
        // Добавляет открывающий тег в переменную Pair
        // Помогает выделять отдельные блоки HTML кода
        String openTag = getOpenTag(str);
        HTMLblock.get(HTMLblock.size() - 1).getPair().setOpen(openTag, HTMLblock.size() - 1);
    }

    private void addStringToBlock(String str) {
        // Добавляет строку в список HTML кода
        HTMLblock.add(new Tag(str + ">"));
    }

    private boolean isContainOpenTag(String str) {
        // Проверяет строку на наличие открывающего тега
        String openTag = str.split("</")[0];
        return openTag.startsWith("<");
    }

    private boolean isContainCloseTag(String str) {
        // Проверяет строку на наличие закрывающего тега
        String closeTag = "";
        try {
            closeTag = str.split("<")[1];
        } catch (Exception e) {
            System.out.println("isContainCloseTag()" + e + "\n Ошибка в строке: " + str);
        }
        return closeTag.startsWith("/");
    }

    private String getOpenTag(String str) {
        // Возвращает открывающий тег
        return str.split(" ")[0].replace("<", "");
    }

    private String getCloseTag(String str) {
        // Возвращает закрывающий тэг
        return str.split("</")[1].replace("</", "");
    }
}
