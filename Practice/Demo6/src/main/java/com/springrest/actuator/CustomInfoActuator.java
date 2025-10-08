package com.springrest.actuator;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

@Endpoint(id="custom-info")
@Component
public class CustomInfoActuator {

    private Long startTime=System.currentTimeMillis();
    private boolean displayTime=true;

    @ReadOperation
    public String getUptime()
    {
        if(displayTime)
        {
            long uptime=System.currentTimeMillis()-startTime;
            long seconds=(uptime/1000)%60;
            long minutes=(uptime/1000)/60;
            return String.format("Application is up since %d minutes and %d seconds.",minutes,seconds);
        }else{
            return "Uptime tracking is disabled";
        }
    }

    @WriteOperation
    public String toggleUptimeTracking(@Selector String action)
    {
        if("enable".equalsIgnoreCase(action))
        {
            displayTime=true;
            return "Uptime tracking is enabled";
        }else if("disable".equalsIgnoreCase(action)){
            displayTime=false;
            return "Uptime tracking is disabled";
        }
        else{
            return "Invalid action. use 'enable' or 'disable'.";
        }
    }

    @DeleteOperation
    public void resetUptimeTracking()
    {
        startTime=System.currentTimeMillis();
    }
}
