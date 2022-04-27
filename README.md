# appliance-service
Simple service which keeps track of connected appliances

1. Update the credentials in application.yml to connect to postgres server.
2. Call the REST endpoint "/api/generate-customers-and-appliances" to generate customers and their appliances.
3. You can either:
   1. Call "acknowledge/{applianceId}" to acknowledge that the appliance(s) are connected.
   2. Call "appliance-connection-status/{applianceId}" to check if the appliance is connected and check the last time it was connected. 
