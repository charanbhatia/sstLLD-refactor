package com.example.video;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        VideoPipelineFacade pipeline = new VideoPipelineFacade();
        
        Path out = pipeline.process(
            Path.of("in.mp4"),      // source
            Path.of("out.mp4"),     // output
            true,                   // apply grayscale
            0.5,                    // scale to 50%
            3                       // sharpen strength
        );
        
        System.out.println("Wrote " + out);
    }
}
