package com.example.report;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public class ReportBundleFacade {
    private final JsonWriter jsonWriter;
    private final Zipper zipper;
    private final AuditLog auditLog;
    
    public ReportBundleFacade() {
        this.jsonWriter = new JsonWriter();
        this.zipper = new Zipper();
        this.auditLog = new AuditLog();
    }
    
    public ReportBundleFacade(JsonWriter jsonWriter, Zipper zipper, AuditLog auditLog) {
        this.jsonWriter = Objects.requireNonNull(jsonWriter, "jsonWriter");
        this.zipper = Objects.requireNonNull(zipper, "zipper");
        this.auditLog = Objects.requireNonNull(auditLog, "auditLog");
    }
    
    /**
     * Exports report data as a zipped bundle.
     * 
     * @param data the report data to export
     * @param outDir the output directory for the zip file
     * @param baseName the base name for the files (without extension)
     * @return Path to the created zip file
     */
    public Path export(Map<String, Object> data, Path outDir, String baseName) {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(outDir, "outDir");
        Objects.requireNonNull(baseName, "baseName");
        
        Path jsonFile = jsonWriter.write(data, outDir, baseName);
        
        Path zipFile = outDir.resolve(baseName + ".zip");
        Path resultZip = zipper.zip(jsonFile, zipFile);
        
        auditLog.log("exported " + resultZip);
        
        return resultZip;
    }
}
