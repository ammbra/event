import java.util.List;

record Workshop(String title, List<String> steps, int duration, Speaker speaker) implements Session {
}
