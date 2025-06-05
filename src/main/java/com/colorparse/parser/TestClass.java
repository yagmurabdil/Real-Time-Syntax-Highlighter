package com.colorparse.parser;


public class TestClass {

    /**
     * Örnek ana metod - syntax highlighting testi için
     */
    public static void main(String[] args) {
        // Değişken tanımlama
        int a = 35;
        
        // İf bloğu - parantez kullanımı
        if(a == 35) {
            System.out.println("Doğru sayı!");
        }

        
        // For döngüsü denemesi
        for (int i = 0; i < 10; i++) {
            System.out.println("Sayaç: " + i);
        }
        
        // String ve karakter literalleri
        String test = "Bu bir test metnidir";
        char c = 'A';
        
        // Çoklu Satır Testi
        /* Bu bir çoklu
           yorum satırıdır. */
           
        // Operatör 
        int x = 10 + 5 * 2;
        boolean condition = (x > 20 && x < 30) || x == 20;
        
        // Try-catch yapısı
        try {
            int result = 10 / 5;
        } catch (ArithmeticException e) {
            System.err.println("Hata: " + e.getMessage());
        }
    }
} 