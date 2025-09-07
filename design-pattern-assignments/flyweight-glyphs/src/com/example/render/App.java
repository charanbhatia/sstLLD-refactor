package com.example.render;

public class App {
    public static void main(String[] args) {
        Renderer r = new Renderer();
        
        TextStyleFactory.clearCache();
        
        String text = "Hello Flyweight! ".repeat(2000);
        System.out.println("Rendering text with " + text.length() + " characters...");
        
        int cost = r.render(text);
        System.out.println("Cost=" + cost);
        
        // Show memory optimization results
        System.out.println("\n=== Flyweight Pattern Results ===");
        System.out.println("Text length: " + text.length() + " characters");
        System.out.println("Unique TextStyle instances created: " + TextStyleFactory.getCacheSize());
        System.out.println("Memory savings: Instead of " + text.length() + 
                         " style objects, we only created " + TextStyleFactory.getCacheSize());
        
        // Print cache details
        System.out.println("\n=== Cache Details ===");
        TextStyleFactory.printCacheStats();
        
        // Demonstrate reference equality (same style configs reuse same instance)
        System.out.println("\n=== Reference Equality Test ===");
        TextStyle style1 = TextStyleFactory.getTextStyle("Inter", 14, true);
        TextStyle style2 = TextStyleFactory.getTextStyle("Inter", 14, true);
        System.out.println("Same style instances are identical (reference equality): " + (style1 == style2));
        
        TextStyle style3 = TextStyleFactory.getTextStyle("Arial", 12, false);
        System.out.println("Different style instances are different: " + (style1 != style3));
    }
}
