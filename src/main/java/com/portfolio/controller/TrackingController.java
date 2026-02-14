package com.portfolio.controller;

import com.portfolio.service.TrackingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    @PostMapping("/view")
    public ResponseEntity<Void> logPageView(
            @RequestParam String pagePath,
            @RequestHeader(value = "User-Agent", defaultValue = "unknown") String userAgent,
            HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        trackingService.logPageView(pagePath, ipAddress, userAgent);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    @PreAuthorize("isAuthenticated()") // Ensure only admins can access this
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(trackingService.getTrackingStats());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalPageViews() {
        return ResponseEntity.ok(trackingService.getTotalPageViews());
    }
}
