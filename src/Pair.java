public class Pair {

    private String open;
    private String close;
    private int openPosition; // Хранит в себе первое открытие тега
    private int closePosition; // закрытие тега нужны для выделения отдельных блоков


    public void setOpen(String open, int position) {
        this.open = open;
        this.openPosition = position;
    }

    public int getClosePosition() {
        return closePosition;
    }

    public String getOpen() {
        return open;
    }

    public void setClose(String close, int position) {
        this.close = close;
        this.closePosition = position;
    }

    public boolean tagIsClosed() {
        // Проверяем есть ли закрывающий тег
        if (open.equals(close)) {
            return true;
        } else {
            return false;
        }
    }
}
