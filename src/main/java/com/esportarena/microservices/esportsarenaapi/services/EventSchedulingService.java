package com.esportarena.microservices.esportsarenaapi.services;

import com.esportarena.microservices.esportsarenaapi.enums.EventStatus;
import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Event;
import com.esportarena.microservices.esportsarenaapi.servicehelpers.EventSchedulingServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class EventSchedulingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventSchedulingService.class);
    private static final String ONE_HOUR = "1:00:00";
    @Autowired
    private EventSchedulingServiceHelper schedulingServiceHelper;

    @Scheduled(cron = "0 */15 * * * *")
    public void updateEventStatus() {
        try {
            LOGGER.info("updateEventStatus scheduler started");
            List<Event> todayEvents = schedulingServiceHelper.findEventsScheduledForToday();

            LocalTime currentLocalTime = LocalTime.now();
            Time currentTime = Time.valueOf(currentLocalTime);
            Time oneHourTime = Time.valueOf(ONE_HOUR);
            Time timeAfterOneHour = addTime(currentTime, oneHourTime);

            if (todayEvents != null) {
                LOGGER.info("Number of events are {} has been scheduled on {} at {}", todayEvents.size(), LocalDate.now(), currentTime);
                for(Event event : todayEvents) {
                        Time eventStartTime = Time.valueOf(event.getTime());
                        Time eventDurationTime = Time.valueOf(event.getDuration());
                        Time eventEndTime = addTime(eventStartTime, eventDurationTime);

                        if(isWithinTheRange(eventStartTime, eventEndTime, timeAfterOneHour) && event.getStatus() == EventStatus.ACTIVE) {
                            schedulingServiceHelper.updateEventStatus(String.valueOf(EventStatus.ONGOING), event.getName());
                            LOGGER.info("Updated event status from ACTIVE to ONGOING");
                        }
                        if(currentTime.compareTo(eventEndTime) > 0 && event.getStatus() == EventStatus.ONGOING) {
                            schedulingServiceHelper.updateEventStatus(String.valueOf(EventStatus.COMPLETED), event.getName());
                            LOGGER.info("Updated event status from ONGOING to COMPLETED");
                        }
                }
            }
            LOGGER.info("updateEventStatus scheduler completed");
        } catch (DataBaseOperationException | MapperException | ValidationException exception) {
            LOGGER.error("Exception occurred in EventSchedulingService.class: updateEventStatus", exception);
        }
    }

    private Time addTime(Time startTime, Time endTime) {
        LocalTime startLocalTime = startTime.toLocalTime();
        LocalTime endLocalTime = endTime.toLocalTime();

        LocalTime totalTime = startLocalTime.plusHours(endLocalTime.getHour())
                .plusMinutes(endLocalTime.getMinute()).plusSeconds(endLocalTime.getSecond());

        Time eventEndTime = Time.valueOf(totalTime);

        return eventEndTime;
    }

    private boolean isWithinTheRange(Time startTime, Time endTime, Time targetTime) {
        if (targetTime.compareTo(startTime) >= 0 && targetTime.compareTo(endTime) <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
