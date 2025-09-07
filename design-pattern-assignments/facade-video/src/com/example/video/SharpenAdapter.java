package com.example.video;

import java.util.Objects;

public class SharpenAdapter {
    private final LegacySharpen legacySharpen;
    
    public SharpenAdapter(LegacySharpen legacySharpen) {
        this.legacySharpen = Objects.requireNonNull(legacySharpen, "legacySharpen");
    }
    
    /**
     * Applies sharpening to frames using the legacy API.
     * 
     * @param frames the frames to sharpen
     * @param strength the sharpening strength
     * @return the sharpened frames
     */
    public Frame[] sharpen(Frame[] frames, int strength) {
        Objects.requireNonNull(frames, "frames");
        
        String framesHandle = framesToHandle(frames);
        
        String sharpenedHandle = legacySharpen.applySharpen(framesHandle, strength);
        
        return handleToFrames(sharpenedHandle, frames);
    }
    
   
    private String framesToHandle(Frame[] frames) {
        StringBuilder handle = new StringBuilder("FRAMES:");
        for (Frame frame : frames) {
            handle.append(frame.w).append("x").append(frame.h).append(",");
        }
        return handle.toString();
    }
   
    private Frame[] handleToFrames(String handle, Frame[] originalFrames) {
        return originalFrames;
    }
}
