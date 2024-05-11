package com.dqtri.batcher.batch.resource;


import com.dqtri.batcher.model.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

@Slf4j
@RequiredArgsConstructor
//@StepScope
//@Component
public class ResourceReader implements ItemReader<Resource> {
    @Override
    public Resource read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
