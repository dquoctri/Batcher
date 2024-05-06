package com.dqtri.batcher.versioning;

public class ApiVersionQueryParamUtil {
    /**
     * Extracts the API version from the query parameters.
     * @param requestUri The full request URI including query parameters.
     * @return The API version as a String, or null if not specified.
     */
    public static String getVersionFromQuery(String requestUri) {
        // Example implementation, consider URL parsing libraries for robustness
        if (requestUri.contains("version=")) {
            return requestUri.split("version=")[1].split("&")[0];
        }
        return null;
    }

    // Other utility methods...
}
