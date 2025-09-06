package edu.eci.arsw.springdemo.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.springdemo.EnglishSpellChecker;
import edu.eci.arsw.springdemo.GrammarChecker;
import edu.eci.arsw.springdemo.SpanishSpellChecker;

/**
 * Clase de prueba para demostrar el uso de ambos SpellCheckers
 * @author usuario
 */
public class TestBothSpellCheckers {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Obtener GrammarChecker (que actualmente usa SpanishSpellChecker)
        GrammarChecker gc = ac.getBean(GrammarChecker.class);
        System.out.println("=== Con SpanishSpellChecker (configurado actualmente) ===");
        System.out.println(gc.check("la la la "));
        
        // También se pueden obtener directamente los spell checkers individuales
        System.out.println("\n=== Probando directamente EnglishSpellChecker ===");
        EnglishSpellChecker englishChecker = ac.getBean("englishSpellChecker", EnglishSpellChecker.class);
        System.out.println("Direct English check: " + englishChecker.checkSpell("hello world"));
        
        System.out.println("\n=== Probando directamente SpanishSpellChecker ===");
        SpanishSpellChecker spanishChecker = ac.getBean("spanishSpellChecker", SpanishSpellChecker.class);
        System.out.println("Direct Spanish check: " + spanishChecker.checkSpell("hola mundo"));
    }
}
