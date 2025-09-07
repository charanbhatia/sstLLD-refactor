package com.example.imports;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class CsvProfileImporter implements ProfileImporter {
    private final NaiveCsvReader csvReader;
    private final ProfileService profileService;
    
    public CsvProfileImporter(NaiveCsvReader csvReader, ProfileService profileService) {
        this.csvReader = Objects.requireNonNull(csvReader, "csvReader");
        this.profileService = Objects.requireNonNull(profileService, "profileService");
    }
    
    @Override
    public int importFrom(Path csvFile) {
        Objects.requireNonNull(csvFile, "csvFile");
        
        List<String[]> rows = csvReader.read(csvFile);
        int successfulImports = 0;
        
        // Skip header row if present (assumes first row is header)
        boolean firstRow = true;
        
        for (String[] row : rows) {
            if (firstRow) {
                firstRow = false;
                // Skip header row if it looks like headers (contains "id", "email", "displayName")
                if (row.length >= 3 && 
                    row[0].trim().equalsIgnoreCase("id") && 
                    row[1].trim().equalsIgnoreCase("email") && 
                    row[2].trim().equalsIgnoreCase("displayName")) {
                    continue;
                }
            }
            
            if (isValidRow(row)) {
                try {
                    String id = row[0].trim();
                    String email = row[1].trim();
                    String displayName = row.length > 2 ? row[2].trim() : "";
                    
                    profileService.createProfile(id, email, displayName);
                    successfulImports++;
                } catch (Exception e) {
                    System.err.println("Failed to create profile for row: " + String.join(",", row) + 
                                     " - Reason: " + e.getMessage());
                }
            } else {
                System.err.println("Skipping invalid row: " + String.join(",", row) + 
                                 " - Missing required fields (id, email) or invalid email format");
            }
        }
        
        return successfulImports;
    }
    
    private boolean isValidRow(String[] row) {
        // Must have at least 2 columns (id and email)
        if (row.length < 2) {
            return false;
        }
        
        String id = row[0].trim();
        String email = row[1].trim();
        
        // Check if id and email are not empty
        if (id.isEmpty() || email.isEmpty()) {
            return false;
        }
        
        // Basic email validation - must contain @
        if (!email.contains("@")) {
            return false;
        }
        
        return true;
    }
}
