package com.example.javasample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaDao {
    Logger logger = LoggerFactory.getLogger(AreaDao.class);
    private final JdbcTemplate jdbcTemplate;

    record AreaInfo(
            Integer id,
            String name) {}

    @Autowired
    AreaDao(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public List<AreaInfo> findAll() {
        var query = "SELECT * FROM area";
        List<AreaInfo> results = jdbcTemplate.query(query, new DataClassRowMapper<>(AreaInfo.class));
        return results;
    }

}
