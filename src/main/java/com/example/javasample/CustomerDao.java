package com.example.javasample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.javasample.CustomerController.*;

@Service
public class CustomerDao {
    Logger logger = LoggerFactory.getLogger(CustomerDao.class);
    private final JdbcTemplate jdbcTemplate;

    record CustomerInfo(
            Integer id,
            String name,
            Integer areaId,
            String areaName) {}

    record CustomerRecord(
            Integer id,
            String name,
            String areaId
    ) {}

    @Autowired
    CustomerDao(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    List<CustomerInfo> findAll() {
        var query = """
SELECT c.id AS id,
       c.name AS name,
       a.id AS area_id,
       a.name AS area_name
 FROM customer c
 LEFT JOIN area a ON(c.area_id = a.id) 
""";
        logger.debug(query);
        List<CustomerInfo> results = jdbcTemplate.query(query, new DataClassRowMapper<>(CustomerInfo.class));
        return results;
    }

    CustomerInfo find(String id) {
        var query = """
SELECT c.id AS id,
       c.name AS name,
       a.id AS area_id,
       a.name AS area_name
 FROM customer c
 LEFT JOIN area a ON(c.area_id = a.id)
 WHERE c.id = ? 
""";
        List<CustomerInfo> result = jdbcTemplate.query(query, new DataClassRowMapper<>(CustomerInfo.class), Integer.valueOf(id));
        return result.get(0);
    }

    void delete(String id) {
        int rows = jdbcTemplate.update("DELETE FROM customer WHERE id = ?", Integer.valueOf(id));
        if (rows != 1) {
            throw new RuntimeException("削除処理で異常が発生しました");
        }
    }

    void update(CustomerRecord rec) {
        int rows = jdbcTemplate.update("UPDATE customer SET name=?, area_id=? WHERE id = ?",
                rec.name, rec.areaId, rec.id);
        if (rows != 1) {
            throw new RuntimeException("更新処理で異常が発生しました");
        }
    }

    void insert(CustomerRecord rec) {
        int rows = jdbcTemplate.update("INSERT INTO customer (name, area_id) VALUES(?, ?)",
                rec.name, rec.areaId);
        if (rows != 1) {
            throw new RuntimeException("更新処理で異常が発生しました");
        }
    }
}
