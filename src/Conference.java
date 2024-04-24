import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.StructuredTaskScope;


record Conference(String title, int duration, Speaker speaker, double popularity) implements Session {

	private static final Random random = new Random();

	public Conference {
		if (!EventSchedule.VALID_REQUEST.isBound()) {
			throw new IllegalStateException("The request state is not bound");
		} else if (!EventSchedule.VALID_REQUEST.get()
				.equals("Eligible")) {
			throw new IllegalStateException("Request state is " + EventSchedule.VALID_REQUEST.get());
		}
	}

	public static class ConferenceScope extends StructuredTaskScope<Conference> {

		private final Collection<Conference> conferences = new ConcurrentLinkedQueue<>();

		@Override
		protected void handleComplete(Subtask<? extends Conference> subtask) {
			switch (subtask.state()) {
				case UNAVAILABLE -> throw new IllegalStateException("Conference session details pending...");
				case SUCCESS -> this.conferences.add(subtask.get());
				case FAILED -> subtask.exception();
			}
		}

		protected Conference findPopularity() {
			return this.conferences.stream()
					.max(Comparator.comparing(Conference::popularity))
					.orElseThrow(IllegalStateException::new);
		}
	}

	public static Conference findPopularity(String title, int duration, Speaker speaker) {

		try (var scope = new Conference.ConferenceScope()) {

			scope.fork(() -> readPopularity1(title, duration, speaker));
			scope.fork(() -> readPopularity2(title, duration, speaker));
			scope.fork(() -> readPopularity3(title, duration, speaker));
			scope.fork(() -> readPopularity4(title, duration, speaker));

			scope.join();

			return scope.findPopularity();

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static Conference readPopularity1(String title, int duration, Speaker speaker) {
		double popularity = random.nextDouble(0, 100);
		try {
			Thread.sleep((int) popularity);
			return new Conference(title, duration, speaker, popularity);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static Conference readPopularity2(String title, int duration, Speaker speaker) {
		double popularity = random.nextDouble(0, 75);
		try {
			Thread.sleep((int) popularity);
			return new Conference(title, duration, speaker, popularity);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static Conference readPopularity3(String title, int duration, Speaker speaker) {
		double popularity = random.nextDouble(0, 75);
		try {
			Thread.sleep((int) popularity);
			return new Conference(title, duration, speaker, popularity);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static Conference readPopularity4(String title, int duration, Speaker speaker) {
		double popularity = random.nextDouble(0, 50);
		try {
			Thread.sleep((int) popularity);
			return new Conference(title, duration, speaker, popularity);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
