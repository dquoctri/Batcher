package com.dqtri.batcher.config;

import com.dqtri.batcher.audit.ActionType;
import com.dqtri.batcher.audit.AuditAction;
import com.dqtri.batcher.model.Book;
import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import com.dqtri.batcher.repository.BookRepository;
import com.dqtri.batcher.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Profile({"development", "test"})
@Configuration
public class SeedConfiguration {

    private final BookRepository bookRepository;

    private final ResourceRepository resourceRepository;

    /**
     * init value of books for testing database
     * Run this if app.db.init.enabled = true
     * @return the runner to execute
     */
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "db.init.enabled", havingValue = "true")
    @AuditAction(value = "SEEDING_BOOKS", type = ActionType.SYSTEM)
    public CommandLineRunner bookCommandLineRunner() {
        return args -> {
            log.info("Seeding books is running.....");
            Book b1 = new Book("Book A");
            Book b2 = new Book("Book B");
            Book b3 = new Book("Book C");
            Book b4 = new Book("Book D");
            bookRepository.saveAll(List.of(b1, b2, b3, b4));
            log.info("Seeding books is completed successfully!");
        };
    }

    /**
     * init value of resources for testing database
     * Run this if app.db.init.enabled = true
     * @return the runner to execute
     */
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "db.init.enabled", havingValue = "true")
    @AuditAction(value = "SEEDING_RESOURCES", type = ActionType.SYSTEM)
    public CommandLineRunner resourceCommandLineRunner() {
        return args -> {
            log.info("Seeding resources is running.....");
            Resource r1 = new Resource("Resource R1", Status.ACTIVE);
            Resource r2 = new Resource("Resource R2", Status.LOCKED);
            Resource r3 = new Resource("Resource R3", Status.DELETED);
            Resource r4 = new Resource("Resource R4", Status.ACTIVE);
            Resource r5 = new Resource("Resource R5", Status.ACTIVE);
            Resource r6 = new Resource("Resource R6", Status.ACTIVE);
            Resource r7 = new Resource("Resource R7", Status.ACTIVE);
            resourceRepository.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7));
            log.info("Seeding resources is completed successfully!");
        };
    }
}
