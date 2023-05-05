package util;

public class Text {
    public static String capitalizeEachWord(String str) {
        String words[] = str.split("\\s");  
        String capitalizeWord="";

        for(String w:words){  
            String first=w.substring(0,1);  
            String afterfirst=w.substring(1);  
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
        }
        
        return capitalizeWord.trim();  
    }

    public static void main(String[] args) {
        System.out.println(capitalizeEachWord("abil halo halo"));
    }
}
