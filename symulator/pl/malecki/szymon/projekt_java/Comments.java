package pl.malecki.szymon.projekt_java;

public class Comments {
    private static String text = "";

    public static void addText(String command) {
        text += command + "\n";
    }

    public static void deleteComments() {
        text = "";
    }

    public static String getText() {
        return text;
    }
}
