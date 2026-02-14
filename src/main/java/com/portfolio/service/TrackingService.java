package com.portfolio.service;

import com.portfolio.model.AdminAccessLog;
import com.portfolio.model.PageView;
import com.portfolio.repository.AdminAccessLogRepository;
import com.portfolio.repository.PageViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final AdminAccessLogRepository adminAccessLogRepository;
    private final PageViewRepository pageViewRepository;
    private final TrackingBufferService trackingBufferService;

    private static final List<String> EXCLUDED_EMAILS = List.of(
            "pulkitgiddu09@gmail.com");

    public void logAdminAccess(String email, String ipAddress, String status) {
        if (EXCLUDED_EMAILS.contains(email)) {
            return;
        }

        AdminAccessLog log = AdminAccessLog.builder()
                .email(email)
                .ipAddress(ipAddress)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
        adminAccessLogRepository.save(log);
    }

    public void logPageView(String pagePath, String ipAddress, String userAgent) {
        trackingBufferService.addPageView(pagePath, ipAddress, userAgent);
    }

    public Map<String, Object> getTrackingStats() {
        List<AdminAccessLog> adminLogs = adminAccessLogRepository.findAllByOrderByTimestampDesc();
        List<PageView> pageViews = pageViewRepository.findAllByOrderByTimestampDesc();

        Map<String, Object> stats = new HashMap<>();
        stats.put("adminLogs", adminLogs);
        stats.put("pageViews", pageViews);
        return stats;
    }

    public long getTotalPageViews() {
        return pageViewRepository.count();
    }
}
