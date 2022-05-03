# appliance-service
Simple service which keeps track of connected appliances

1. Update the credentials in application.yml to connect to postgres server.
2. Call the REST endpoint "api/database/table/create/customer-and-appliance" to generate customers and their appliances.
3. You can either:
   1. Call "/api/appliance/ping/{applianceId}" to update last appliance connection status.
   2. Call "/api/appliance/status/{applianceId}" to check if the appliance is connected and get the last time it was connected (it pinged).