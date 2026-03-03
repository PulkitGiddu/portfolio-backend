package com.portfolio.service;

import com.portfolio.model.ContactMessage;
import com.portfolio.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactMessageRepository repository;
    private final EmailService emailService;

    public ContactService(ContactMessageRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Transactional
    public ContactMessage saveMessage(ContactMessage message) {
        log.debug("Saving contact message to database for sender: {}", message.getSenderEmail());
        ContactMessage saved = repository.save(message);
        log.info("Successfully saved contact message to database with ID: {}", saved.getId());

        // Send email asynchronously
        log.debug("Initiating asynchronous email notification for message ID: {}", saved.getId());
        emailService.sendContactNotification(
                saved.getSenderName(),
                saved.getSenderEmail(),
                saved.getMessage(),
                saved.getVoiceMemoData(),
                saved.getVoiceMemoContentType());

        return saved;
    }
}
