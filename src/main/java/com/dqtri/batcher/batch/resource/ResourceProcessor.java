package com.dqtri.batcher.batch.resource;

import com.dqtri.batcher.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceProcessor implements ItemProcessor<Book, Book> {
    @Override
    public Book process(Book item) throws Exception {
        return null;
    }
}
