package Arboles;

public class NumHoja {
    public int content;
    public NumHoja(String content) {
        this.content = clean(content) + 1;
    }

    public int getNum(){
        content -= 1;
        return content;
    }


    public int clean(String content){
        return content.replace(".", "").replace("|", "").replace("*", "").replace("?", "").length();
    }
}
