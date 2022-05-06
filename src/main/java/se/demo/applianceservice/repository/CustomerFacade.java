package se.demo.applianceservice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import se.demo.applianceservice.repository.util.FileReader;

import java.util.Map;

@Slf4j
@Repository
public class CustomerFacade {

    private String createCustomerTableSql;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FileReader fileReader;

    @Autowired
    public CustomerFacade(NamedParameterJdbcTemplate jdbcTemplate,
                          FileReader fileReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.fileReader = fileReader;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadSqlStatements() {
        log.debug("loadSqlStatements()");
        createCustomerTableSql = fileReader.readFileContent("create_customer_table.sql");
        log.debug("loadSqlStatements(), done.");
    }

    public void createCustomerTable() {
        log.info("createCustomerTable()");
        jdbcTemplate.update(createCustomerTableSql, Map.of());
        log.info("createCustomerTable(), done.");
    }
}
