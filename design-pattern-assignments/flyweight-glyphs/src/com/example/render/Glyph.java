package com.example.render;

public class Glyph {
    // Extrinsic state: unique per glyph instance
    private final char ch;
    
    // Intrinsic state: shared TextStyle flyweight
    private final TextStyle style;

    public Glyph(char ch, TextStyle style) {
        this.ch = ch;
        this.style = style;
    }

    public int drawCost() { 
        return style.getDrawCost(); 
    }
    
    public char getCh() { 
        return ch; 
    }
    
    public TextStyle getStyle() {
        return style;
    }
    
    // Legacy methods for compatibility (delegate to TextStyle)
    public String getFont() { 
        return style.getFont(); 
    }
    
    public int getSize() { 
        return style.getSize(); 
    }
    
    public boolean isBold() { 
        return style.isBold(); 
    }
}
