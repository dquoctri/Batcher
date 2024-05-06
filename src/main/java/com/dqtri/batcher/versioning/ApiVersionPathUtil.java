package com.dqtri.batcher.versioning;

public class ApiVersionPathUtil {
    /**
     * Extracts the version part from the URI path.
     * @param requestUri The full request URI.
     * @return The API version as a String.
     */
    public static String extractVersion(String requestUri) {
        // Assuming the version is always the second segment
        String[] parts = requestUri.split("/");
        return parts.length > 2 ? parts[2] : null; // Example: /api/v1/users -> v1
    }

    // Other utility methods...
}
