package se.demo.applianceservice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import se.demo.applianceservice.domain.Appliance;
import se.demo.applianceservice.repository.util.FileReader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ApplianceFacade {

    private String createApplianceTableSql;
    private String updateApplianceStatusSql;
    private String getApplianceInfoSql;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FileReader fileReader;

    @EventListener(ApplicationReadyEvent.class)
    public void loadSqlStatements() {
        log.info("loadSqlStatements()");

        createApplianceTableSql = fileReader.readFileContent("create_appliance_table.sql");
        updateApplianceStatusSql = fileReader.readFileContent("update_appliance_status.sql");
        getApplianceInfoSql = fileReader.readFileContent("get_appliance_info.sql");

        log.info("loadSqlStatements(), done.");
    }
    public void createApplianceTable() {
        log.info("createApplianceTable()");
        jdbcTemplate.execute(createApplianceTableSql);
        log.info("createApplianceTable(), done.");
    }

    public void updateApplianceStatus(String applianceId) {
        log.info("updateApplianceStatus()");
        jdbcTemplate.update(updateApplianceStatusSql, applianceId);
        log.info("updateApplianceStatus(), done.");
    }

    public List<Appliance> getApplianceStatus(String applianceId) {
        log.info("getApplianceStatus(), applianceId: {}", applianceId);

        List<Appliance> appliances = jdbcTemplate.query(getApplianceInfoSql,
                extractApplianceFromResultSet(),
                applianceId);

        log.info("getApplianceStatus(), done. appliances: {}", appliances);
        return appliances;
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
