import { TasksService } from './tasks.service';
import { Task } from './task.model';
export declare class TasksController {
    private tasksService;
    constructor(tasksService: TasksService);
    getAllTasks(): Task[];
    createTask(title: string, description: string): Task;
}
