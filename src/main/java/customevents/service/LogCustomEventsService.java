package customevents.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import customevents.model.LogEvents;
import customevents.util.LogCustomEventsUtil;

public class LogCustomEventsService {
	
	public static void main(String[] args) {
		List<String> eventsListFromLogs = LogCustomEventsUtil.getEventsListFromLogs();
		
		if (!eventsListFromLogs.isEmpty()) {
			eventsListFromLogs.forEach(event -> {
				ObjectMapper mapper = new ObjectMapper();
				
				try {
					LogEvents readValue = mapper.readValue(event, LogEvents.class);
					System.out.println(readValue);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
}
