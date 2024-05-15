import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

record Workshop(String title, List<String> steps, int duration, Speaker speaker, List<String> tags) implements Session {
	private static final Random random = new Random();
	private static final Logger logger = Logger.getLogger(Workshop.class.getName());

	public static Workshop findPopularity(String title, List<String> steps, int duration, Speaker speaker) {

		try (var scope = new StructuredTaskScope.ShutdownOnSuccess<Workshop>()) {

			var first = scope.fork(() -> readTags1(title, steps, duration, speaker));
			var second = scope.fork(() -> readTags2(title, steps, duration, speaker));
			var third = scope.fork(() -> readTags3(title, steps, duration, speaker));
			var fourth = scope.fork(() -> readTags4(title, steps, duration, speaker));

			scope.join();

			ConsoleHandler handler = new ConsoleHandler();
			handler.setFormatter(new SimpleFormatter());
			logger.addHandler(handler);
			logger.setLevel(Level.OFF);
			logger.log(Level.INFO,"first = " + first.state());
			logger.log(Level.INFO,"second = " + second.state());
			logger.log(Level.INFO,"third = " + third.state());
			logger.log(Level.INFO,"fourth = " + fourth.state());

			Workshop workshop = scope.result();
			return workshop;

		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static Workshop readTags1(String title, List<String> steps, int duration, Speaker speaker) {
		int level = random.nextInt(0, 7);
		try {
			Thread.sleep(level);
			return new Workshop(title, steps, duration, speaker, EventSchedule.TAGS.subList(0, level));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static Workshop readTags2(String title, List<String> steps, int duration, Speaker speaker) {
		int level = random.nextInt(0, 4);
		try {
			Thread.sleep(level);
			return new Workshop(title, steps, duration, speaker, EventSchedule.TAGS.subList(0, level));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static Workshop readTags3(String title, List<String> steps, int duration, Speaker speaker) {
		int level = random.nextInt(0, 6);
		try {
			Thread.sleep(level);
			return new Workshop(title, steps, duration, speaker, EventSchedule.TAGS.subList(0, level));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static Workshop readTags4(String title, List<String> steps, int duration, Speaker speaker) {
		int level = random.nextInt(0, 7);
		try {
			Thread.sleep(level);
			return new Workshop(title, steps, duration, speaker, EventSchedule.TAGS.subList(0, level));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}