package com.dqtri.batcher.batch.book;

import com.dqtri.batcher.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Slf4j
public class BookEntityCsvWriter implements ItemWriter<Book> {

    private static final String CSV_FILE = "output.csv";
    private FlatFileItemWriter<Book> writer;

    public BookEntityCsvWriter()
    {
        initializeCsvFile();
        this.writer = new FlatFileItemWriter<>();
        this.writer.setResource(
                new FileSystemResource(CSV_FILE));
        this.writer.setLineAggregator(
                new DelimitedLineAggregator<Book>() {
                    {
                        setDelimiter(",");
                        setFieldExtractor(
                                new BeanWrapperFieldExtractor<
                                        Book>() {
                                    {
                                        setNames(new String[] {
                                                "id", "author", "name",
                                                "price" });
                                    }
                                });
                    }
                });
    }

    private void initializeCsvFile()
    {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception e) {
                throw new RuntimeException(
                        "Error creating CSV file", e);
            }
        }
    }


    @Override
    public void write(Chunk<? extends Book> chunk) throws Exception {
        writer.write(chunk);
    }

    @Bean
    public ItemProcessor<Book, Book> bookProcessor() {
        return item -> {
            log.debug("Processor item: {}", item);
            return item;
        };
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info("beforeStep");
    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("afterStep");
        return ExitStatus.COMPLETED;
    }
}
