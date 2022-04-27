package se.demo.applianceservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import se.demo.applianceservice.repository.util.FileReader;

@Repository
public class CustomerFacade {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FileReader fileReader;

    public void createCustomerTable() {
        String sqlStatement = fileReader.readFileContent("create_customer_table.sql");
        jdbcTemplate.execute(sqlStatement);
    }
}
