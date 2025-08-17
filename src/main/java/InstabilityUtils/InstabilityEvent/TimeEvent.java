package InstabilityUtils.InstabilityEvent;

import GenericUtils.RandomUtils;
import RiftEvent2.RiftEvent2;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TimeEvent {

    static int currentTime = 0;
    public static List<Integer> TimeLoopTasks = new ArrayList<Integer>();
    public static void TimeLooper() {
        try {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    int rand = RandomUtils.Randomint(2, 1);
                    if(rand == 1){
                        currentTime-=100 + RiftEvent2.getInstance().percentInstab * 5;
                    }
                    if(rand == 2){
                        currentTime+=100 + RiftEvent2.getInstance().percentInstab * 5;
                    }


                    if(currentTime > 24000 || currentTime < 0)
                    {
                        currentTime = 0;
                    }
                    Bukkit.getWorld(RiftEvent2.getInstance().WorldName).setTime(currentTime);

                    TimeLooper();
                }
            }.runTaskLater(RiftEvent2.getInstance(), 1);
            TimeLoopTasks.add(task.getTaskId());
        } catch (UnsupportedOperationException e) {
            // Log a warning message
            Bukkit.getLogger().warning("[RiftEvent] Failed to schedule Time loop: " + e.getMessage());
        }
    }
    public static void CancelAllTasks() {
        TimeLoopTasks.forEach(taskId -> {
            Bukkit.getScheduler().cancelTask(taskId);
        });
        TimeLoopTasks.clear();
    }
}
