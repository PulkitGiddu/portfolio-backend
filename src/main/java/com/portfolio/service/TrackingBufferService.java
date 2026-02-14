package com.portfolio.service;

import com.portfolio.model.PageView;
import com.portfolio.repository.PageViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingBufferService {

    private final PageViewRepository pageViewRepository;
    private final ConcurrentLinkedQueue<PageView> pageViewBuffer = new ConcurrentLinkedQueue<>();

    private static final int BATCH_SIZE = 50;

    public void addPageView(String pagePath, String ipAddress, String userAgent) {
        PageView view = PageView.builder()
                .pagePath(pagePath)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .timestamp(LocalDateTime.now())
                .build();
        pageViewBuffer.add(view);

        if (pageViewBuffer.size() >= BATCH_SIZE) {
            flushBuffer();
        }
    }

    @Scheduled(fixedRate = 300000) // Flush every 5 minutes
    @Transactional
    public void flushBuffer() {
        if (pageViewBuffer.isEmpty()) {
            return;
        }

        List<PageView> viewsToSave = new ArrayList<>();
        PageView view;
        while ((view = pageViewBuffer.poll()) != null) {
            viewsToSave.add(view);
        }

        if (!viewsToSave.isEmpty()) {
            log.info("Flushing {} page views to database", viewsToSave.size());
            pageViewRepository.saveAll(viewsToSave);
        }
    }
}
