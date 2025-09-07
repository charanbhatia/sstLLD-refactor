package com.example.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TextStyleFactory {
    private static final Map<String, TextStyle> cache = new HashMap<>();
    
    /**
     * Get a TextStyle instance. If one with the same properties exists,
     * returns the cached instance. Otherwise creates and caches a new one.
     * 
     * @param font the font name
     * @param size the font size
     * @param bold whether the text is bold
     * @return a TextStyle instance (potentially shared)
     */
    public static TextStyle getTextStyle(String font, int size, boolean bold) {
        Objects.requireNonNull(font, "font");
        
        // Create cache key using the suggested format: "Inter|14|B"
        String key = font + "|" + size + "|" + (bold ? "B" : "N");
        
        // Return cached instance if exists, otherwise create and cache new one
        return cache.computeIfAbsent(key, k -> new TextStyle(font, size, bold));
    }
    
   
    public static int getCacheSize() {
        return cache.size();
    }
    
    
    public static void clearCache() {
        cache.clear();
    }
    
   
    public static void printCacheStats() {
        System.out.println("TextStyleFactory cache contains " + cache.size() + " unique styles:");
        for (Map.Entry<String, TextStyle> entry : cache.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
