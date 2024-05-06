import java.io.BufferedReader;
import java.io.FileReader;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;


public class EventSchedule {

	public static ScopedValue<String> VALID_REQUEST = ScopedValue.newInstance();
	public static List<String> TAGS = Arrays.asList("Java", "JVM", "Cloud", "Tools", "JDK", "Practices", "Performance");

	void main(String[] args) throws Exception {

		String line;
		BufferedReader br = new BufferedReader(new FileReader("sessions.csv"));
		List<Slot<Session, Timeframe>> slots = new ArrayList<>();
		LocalDateTime start = LocalDateTime.now();

		while ((line = br.readLine()) != null) {
			String[] values = line.split(",");
			switch (values[0]) {
				case "keynote" -> {
					Speaker speaker = new Speaker(values[4], values[5], values[6]);
					Keynote keynote = new Keynote(values[1], values[2], Integer.parseInt(values[3]), speaker);
					slots.add(new Slot<>(keynote, new Timeframe(start, start.plusMinutes(keynote.duration()))));
					start = start.plusMinutes(keynote.duration());
				}
				case "workshop" -> {
					Speaker speaker = new Speaker(values[4], values[5], values[6]);
					Workshop workshop = new Workshop(values[1], Arrays.asList(values[2].split("|")), Integer.parseInt(values[3]), speaker);
					slots.add(new Slot<>(workshop, new Timeframe(start, start.plusMinutes(workshop.duration()))));
					start = start.plusMinutes(workshop.duration());
				}

				case "conference" -> {
					Speaker speaker = new Speaker(values[3], values[4], values[5]);
					Conference conference = new Conference(values[1], Integer.parseInt(values[2]), speaker);
					slots.add(new Slot<>(conference, new Timeframe(start, start.plusMinutes(conference.duration()))));
					start = start.plusMinutes(conference.duration());
				}
			}
		}
		announceSchedule(slots);
	}

	static void announceSchedule(List<Slot<Session, Timeframe>> slots) {
		for (Slot<Session, Timeframe> slot : slots) {
			switch (slot) {
				case Slot<Session, Timeframe> (Conference c, Timeframe t) -> System.out.println(announce(c, t));
				case Slot<Session, Timeframe>(Keynote _, Timeframe t) -> System.out.println(announce("keynote", t));
				case Slot<Session, Timeframe>(Workshop _, Timeframe t) -> System.out.println(announce("workshop", t));
			}
		}
	}

	private static String announce(Session session, Timeframe t) {
		String announcement = "A great session about {0} at {1}! Do not miss it!";
		String title = switch (session) {
			case Conference c ->  c.title();
			case Workshop w -> w.title();
			case Keynote k -> k.title();
		};
		return MessageFormat.format(announcement, title, t.start());
	}


	private static String announce(String message, Timeframe t) {
		String announcement = "A great {0} session starts at {1}! Do not miss it!";
		return MessageFormat.format(announcement, message, t.start());
	}

}