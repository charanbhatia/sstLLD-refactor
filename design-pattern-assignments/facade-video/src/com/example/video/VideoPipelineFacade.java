package com.example.video;

import java.nio.file.Path;
import java.util.Objects;

public class VideoPipelineFacade {
    private final Decoder decoder;
    private final FilterEngine filterEngine;
    private final Encoder encoder;
    private final SharpenAdapter sharpenAdapter;
    
    public VideoPipelineFacade() {
        this.decoder = new Decoder();
        this.filterEngine = new FilterEngine();
        this.encoder = new Encoder();
        this.sharpenAdapter = new SharpenAdapter(new LegacySharpen());
    }
    
    public VideoPipelineFacade(Decoder decoder, FilterEngine filterEngine, 
                              Encoder encoder, SharpenAdapter sharpenAdapter) {
        this.decoder = Objects.requireNonNull(decoder, "decoder");
        this.filterEngine = Objects.requireNonNull(filterEngine, "filterEngine");
        this.encoder = Objects.requireNonNull(encoder, "encoder");
        this.sharpenAdapter = Objects.requireNonNull(sharpenAdapter, "sharpenAdapter");
    }
    
    /**
     * Processes a video file through the complete pipeline.
     * 
     * @param src the source video file path
     * @param out the output video file path
     * @param gray whether to apply grayscale filter
     * @param scale optional scaling factor (null to skip)
     * @param sharpenStrength optional sharpening strength (null to skip)
     * @return the output file path
     */
    public Path process(Path src, Path out, boolean gray, Double scale, Integer sharpenStrength) {
        Objects.requireNonNull(src, "src");
        Objects.requireNonNull(out, "out");
        
        Frame[] frames = decoder.decode(src);
        
        if (gray) {
            frames = filterEngine.grayscale(frames);
        }
        
        if (scale != null && scale != 1.0) {
            frames = filterEngine.scale(frames, scale);
        }
        
        if (sharpenStrength != null && sharpenStrength > 0) {
            frames = sharpenAdapter.sharpen(frames, sharpenStrength);
        }
        
        return encoder.encode(frames, out);
    }
}
