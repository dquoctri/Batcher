package com.dqtri.batcher.batch.resource;

import com.dqtri.batcher.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceWriter implements ItemWriter<Book> {
    @Override
    public void write(Chunk<? extends Book> chunk) throws Exception {

    }
}
