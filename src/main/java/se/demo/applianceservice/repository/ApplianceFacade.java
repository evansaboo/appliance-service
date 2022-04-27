package se.demo.applianceservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import se.demo.applianceservice.domain.Appliance;
import se.demo.applianceservice.repository.util.FileReader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplianceFacade {

    String update_appliance_status_sql;
    String get_appliance_info_sql;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FileReader fileReader;

    public void createApplianceTable() {
        /* NOTE: Fetching the content of the file from the file system for every method call is not efficient.
        It should be loaded into the memory at application startup.
         */
        String sqlStatement = fileReader.readFileContent("create_appliance_table.sql");
        jdbcTemplate.execute(sqlStatement);
    }

    public void updateApplianceStatus(String applianceId) {
        String sqlStatement = fileReader.readFileContent("update_appliance_status.sql");

        jdbcTemplate.update(sqlStatement, applianceId);
    }

    public List<Appliance> getApplianceStatus(String applianceId) {
        String sqlStatement = fileReader.readFileContent("get_appliance_info.sql");

        return jdbcTemplate.query(sqlStatement,
                extractApplianceFromResultSet(),
                applianceId);
    }

    private ResultSetExtractor<List<Appliance>> extractApplianceFromResultSet() {
        return rs -> {
            List<Appliance> appliances = new ArrayList<>();
            while(rs.next()) {
                Appliance appliance = Appliance.builder()
                        .applianceId(rs.getString("APPLIANCE_ID"))
                        .lastConnectionPing(rs.getObject("LAST_CONNECTION_PING", LocalDateTime.class))
                        .build();
                appliances.add(appliance);
            }
            return appliances;
        };
    }

}
