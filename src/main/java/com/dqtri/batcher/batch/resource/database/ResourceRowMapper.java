package com.dqtri.batcher.batch.resource.database;

import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResourceRowMapper implements RowMapper<Resource> {

    public static final String PK_COLUMN = "pk";
    public static final String CONTENT_COLUMN = "content";
    public static final String STATUS_COLUMN = "status";

    @Override
    public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resource resource = new Resource();
        resource.setPk(rs.getLong(PK_COLUMN));
        resource.setContent(rs.getString(CONTENT_COLUMN));
        resource.setStatus(Status.valueOf(rs.getString(STATUS_COLUMN)));
        return resource;
    }
}
