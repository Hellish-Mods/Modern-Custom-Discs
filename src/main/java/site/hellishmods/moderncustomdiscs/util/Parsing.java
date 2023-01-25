package site.hellishmods.moderncustomdiscs.util;

public class Parsing {
    // Code partially stolen from https://github.com/BlakeBr0/MysticalCustomization/blob/1.16/src/main/java/com/blakebr0/mysticalcustomization/util/ParsingUtils.java
    public static int parseColorHex(String s) { // Parses color string to color bytes
        if (s.startsWith("#")) {s = s.substring(1);}
        
        try {return Integer.parseInt(s, 16);}
        catch (NumberFormatException e) {return 0xffffff;} // In case of an error, return white
    }
}
