package customevents.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import customevents.hsqldb.CreateLogEventsTable;
import customevents.hsqldb.UpdateLogEventsTable;
import customevents.model.LogEvents;
import customevents.util.LogCustomEventsUtil;

public class LogCustomEventsService {
	
	public static void main(String[] args) {
		List<String> eventsListFromLogs = LogCustomEventsUtil.getEventsListFromLogs();
		List<LogEvents> logEventsList = new LinkedList<>();
		
		if (!eventsListFromLogs.isEmpty()) {
			eventsListFromLogs.forEach(event -> {
				ObjectMapper mapper = new ObjectMapper();
				
				try {
					LogEvents readValue = mapper.readValue(event, LogEvents.class);
					System.out.println(readValue);
					
					logEventsList.add(readValue);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			});
		}
		
		// Flag events that take longer than 4ms
		Map<String, Long> finalEventsMap = LogCustomEventsUtil.getFinalEventsMap(logEventsList);
		
		if (!finalEventsMap.isEmpty()) {
			finalEventsMap.forEach((eventId, duration) -> {
				if (duration > 4) {
					System.out.println("Event " + eventId + " took longer than 4ms.");
				}
			});
		}
		
		// Insert event details to hsqldb
		CreateLogEventsTable.createLogEventsTable(); // will return 0 if table already exists
		
		// Insert event details into events_tbl
		UpdateLogEventsTable.updateLogEventsTable(logEventsList, finalEventsMap);
	}
	
}
