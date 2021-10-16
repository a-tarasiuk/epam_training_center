package by.tarasiuk.app;

import by.tarasiuk.util.Utils;

public class App {
    private App() {
    }

    public static void main(String[] args) {
        boolean result = Utils.isAllPositiveNumbers("12", "79");

        System.out.println("Result: " + result);
    }
}
