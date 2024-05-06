package com.dqtri.batcher.versioning;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiVersionAcceptHeaderUtil {
    /**
     * Parses the Accept header to extract the API version.
     * @param acceptHeader The Accept header string.
     * @return The extracted version, or null if not found.
     */
    public static String getVersionFromAcceptHeader(String acceptHeader) {
        // Example parsing logic
        Pattern pattern = Pattern.compile("vnd\\.myapi\\.v(\\d+)\\+json");
        Matcher matcher = pattern.matcher(acceptHeader);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // Other utility methods...
}
