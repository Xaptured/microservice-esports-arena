package com.esportarena.microservices.esportsarenaapi.servicehelpers;

import com.esportarena.microservices.esportsarenaapi.clients.TheJackFolioDBClient;
import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Event;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventSchedulingServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventSchedulingServiceHelper.class);
    @Autowired
    private TheJackFolioDBClient dbClient;

    public List<Event> findEventsScheduledForToday() throws DataBaseOperationException, MapperException {
        ResponseEntity<List<Event>> response = dbClient.findEventsScheduledForToday();
        List<Event> responseBody = response.getBody();
        if(responseBody != null) {
            if (responseBody.size() == 1 && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                throw new DataBaseOperationException(responseBody.get(0).getMessage());
            } else if (responseBody.size() == 1 && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                throw new MapperException(responseBody.get(0).getMessage());
            } else {
                return responseBody;
            }
        } else {
            return null;
        }
    }

    public void updateEventStatus(String status, String eventName) throws DataBaseOperationException, ValidationException {
        if(StringUtils.isBlank(status) || StringUtils.isEmpty(eventName) || StringUtils.isEmpty(status) || StringUtils.isBlank(eventName)) {
            LOGGER.error("Validation failed in EventSchedulingServiceHelper.class : updateEventStatus");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        ResponseEntity<String> response = dbClient.updateEventStatus(status, eventName);
        String responseBody = response.getBody();
        if(responseBody.equals(StringConstants.DATABASE_ERROR)) {
            throw new DataBaseOperationException(responseBody);
        }
    }
}
