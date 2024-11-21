package test.java.com.nikitatunkel.eff_mob;

import com.nikitatunkel.eff_mob.model.Task;
import com.nikitatunkel.eff_mob.repository.TaskRepository;
import com.nikitatunkel.eff_mob.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

	private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
	private final TaskService taskService = new TaskService(taskRepository);

	@Test
	void testGetAllTasks() {
		List<Task> tasks = List.of(new Task(), new Task());
		when(taskRepository.findAll()).thenReturn(tasks);

		List<Task> result = taskService.getAllTasks();

		assertEquals(2, result.size());
		verify(taskRepository, times(1)).findAll();
	}

	@Test
	void testCreateTask() {
		Task task = new Task();
		task.setTitle("Test Task");
		when(taskRepository.save(task)).thenReturn(task);

		Task result = taskService.createTask(task);

		assertEquals("Test Task", result.getTitle());
		verify(taskRepository, times(1)).save(task);
	}

	@Test
	void testUpdateTask() {
		Task existingTask = new Task();
		existingTask.setId(1L);
		existingTask.setTitle("Old Title");

		Task updatedDetails = new Task();
		updatedDetails.setTitle("New Title");

		when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
		when(taskRepository.save(existingTask)).thenReturn(existingTask);

		Task result = taskService.updateTask(1L, updatedDetails);

		assertEquals("New Title", result.getTitle());
		verify(taskRepository, times(1)).findById(1L);
		verify(taskRepository, times(1)).save(existingTask);
	}

	@Test
	void testDeleteTask() {
		doNothing().when(taskRepository).deleteById(1L);

		taskService.deleteTask(1L);

		verify(taskRepository, times(1)).deleteById(1L);
	}
}
