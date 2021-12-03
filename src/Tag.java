public class Tag {

    private Pair pair = new Pair();
    private String tagStroke;
    private String classAttribute;

    public String getTagStroke() {
        return tagStroke;
    }

    public Pair getPair() {
        return pair;
    }

    public String getElementClass() {
        // возвращает класс тега
        if(classAttribute == null){
            return "";
        }
        else {
            return classAttribute;
        }
    }

    public Tag(String tagStroke) {
        this.tagStroke = tagStroke;
    }

    public void addClass(String str){
        this.classAttribute = str;
    }


}
