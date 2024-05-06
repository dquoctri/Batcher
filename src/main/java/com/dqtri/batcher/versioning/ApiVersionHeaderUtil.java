package com.dqtri.batcher.versioning;

import jakarta.servlet.http.HttpServletRequest;

public class ApiVersionHeaderUtil {
    /**
     * Retrieves the API version from custom header.
     * @param request The HTTP request object.
     * @return The API version as a String, or null if not provided.
     */
    public static String getVersionFromHeader(HttpServletRequest request) {
        return request.getHeader("X-API-Version");
    }

    // Other utility methods...
}
