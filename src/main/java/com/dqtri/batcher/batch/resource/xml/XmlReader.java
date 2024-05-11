package com.dqtri.batcher.batch.resource.xml;

import com.dqtri.batcher.annotation.DemoTag;
import com.thoughtworks.xstream.security.ExplicitTypePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class XmlReader {
    @Bean
    @StepScope
    public StaxEventItemReader<Trade> xmlItemReader() {
        FileSystemResource fileSystemResource = new FileSystemResource("data/trades.xml");
        return new StaxEventItemReaderBuilder<Trade>()
                .name("xmlItemReader")
                .resource(fileSystemResource)
                .addFragmentRootElements("trade")
                .unmarshaller(tradeMarshaller())
                .build();
    }

    /**
     * cannot format the output [issue](https://github.com/spring-projects/spring-batch/issues/1754)
     * @return writer
     */
    @Bean
    @StepScope
    public StaxEventItemWriter<Trade> xmlItemWriter() {
        FileSystemResource resource = new FileSystemResource("data/outputFile.xml");
        return new StaxEventItemWriterBuilder<Trade>()
                .name("xmlItemWriter")
                .marshaller(tradeMarshaller())
                .resource(resource)
                .rootTagName("trades")
                .standalone(true)
                .overwriteOutput(true)
                .build();
    }

    @Bean
    public XStreamMarshaller tradeMarshaller() {
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("trade", Trade.class);
        aliases.put("price", BigDecimal.class);
        aliases.put("isin", String.class);
        aliases.put("customer", String.class);
        aliases.put("quantity", Long.class);

        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        marshaller.setTypePermissions(new ExplicitTypePermission(new Class[] { Trade.class }));
        return marshaller;
    }

    @DemoTag(value = "Demo 110", description = "Xml reader writer")
    @Bean
    public Job xmlJob(JobRepository jobRepository, Step xmlChunkStep) {
        return new JobBuilder("xmlJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(xmlChunkStep)
                .build();
    }


    @DemoTag(value = "Demo 110", description = "Demo config chunk size")
    @Bean
    public Step xmlChunkStep(JobRepository jobRepository,
                             PlatformTransactionManager transactionManager,
                             TradeItemWriteListener listener) {
        return new StepBuilder("xmlChunkStep", jobRepository)
                .<Trade, Trade>chunk(2, transactionManager) //With a commit-interval of 5
                .reader(xmlItemReader())
                .writer(xmlItemWriter())
                .listener(listener)
                .build();
    }
}
