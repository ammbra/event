import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

//TODO have slots for event sessions with a timeframe
//TODO a timeframe has a start and end time
//TODO a session can be a conference, a keynote, a workshop
//TODO a conference has a title, a duration and a speaker
//TODO a keynote has a title, a theme, a duration and a speaker
//TODO a workshop has a title, steps to execute, a duration and a speaker
public class EventSchedule {

	public static void main(String[] args) throws Exception {

		String line;
		BufferedReader br = new BufferedReader(new FileReader("sessions.csv"));
		Map<String, LocalDateTime> slots = new HashMap<>();
		LocalDateTime start = LocalDateTime.now();

		while ((line = br.readLine()) != null) {
			String[] values = line.split(",");
			switch (values[0]) {
				case "keynote", "workshop" -> {
					slots.put(values[1], start);
					start = start.plusMinutes(Integer.parseInt(values[3]));
				}

				case "conference" -> {
					slots.put(values[1], start);
					start = start.plusMinutes(Integer.parseInt(values[2]));
				}
			}
		}
		announceSchedule(slots);
	}

	static void announceSchedule(Map<String, LocalDateTime> slots) {
		for (Map.Entry<String, LocalDateTime> slot : slots.entrySet()) {
			System.out.println(announce(slot.getKey(), slot.getValue()));
		}
	}

	private static String announce(String message, LocalDateTime t) {
		String announcement = "A great {0} session starts at {1}! Do not miss it!";
		return MessageFormat.format(announcement, message, t);
	}

}