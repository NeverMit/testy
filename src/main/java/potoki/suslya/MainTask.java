package potoki.suslya;

import java.util.List;

public final class MainTask extends CompositeTask<SubTask> {
    public MainTask(final long id, final List<SubTask> subtasks) {
        super(id, subtasks);
    }

    @Override
    protected void perform(final SubTask subtask) {
        new Thread(subtask::perform).start();
    }
}
