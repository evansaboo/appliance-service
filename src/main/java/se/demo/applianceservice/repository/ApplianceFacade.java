package se.demo.applianceservice.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import se.demo.applianceservice.domain.Appliance;
import se.demo.applianceservice.repository.util.FileReader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ApplianceFacade {

    private String createApplianceTableSql;
    private String updateApplianceStatusSql;
    private String getApplianceInfoSql;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FileReader fileReader;

    @Autowired
    public ApplianceFacade(NamedParameterJdbcTemplate jdbcTemplate,
                           FileReader fileReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.fileReader = fileReader;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void loadSqlStatements() {
        log.debug("loadSqlStatements()");

        createApplianceTableSql = fileReader.readFileContent("create_appliance_table.sql");
        updateApplianceStatusSql = fileReader.readFileContent("update_appliance_status.sql");
        getApplianceInfoSql = fileReader.readFileContent("get_appliance_info.sql");

        log.debug("loadSqlStatements(), done.");
    }
    public void createApplianceTable() {
        log.info("createApplianceTable()");
        jdbcTemplate.update(createApplianceTableSql, Map.of());
        log.info("createApplianceTable(), done.");
    }

    public void updateApplianceStatus(String applianceId) {
        log.info("updateApplianceStatus(), applianceId: {}", applianceId);

        Map<String, Object> sqlParams = Map.of("applianceId", applianceId);
        jdbcTemplate.update(updateApplianceStatusSql, sqlParams);

        log.info("updateApplianceStatus(), done.");
    }

    public List<Appliance> getApplianceStatus(String applianceId) {
        log.info("getApplianceStatus(), applianceId: {}", applianceId);

        Map<String, Object> sqlParams = Map.of("applianceId", applianceId);
        List<Appliance> appliances = jdbcTemplate.query(
                getApplianceInfoSql,
                sqlParams,
                extractApplianceFromResultSet()
        );

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
