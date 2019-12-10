import { Task, TaskStatus } from './task.model';
export declare class TasksService {
    private readonly tasks;
    getAllTasks(): Task[];
    createTask(title: string, description: string): {
        title: string;
        description: string;
        status: TaskStatus;
        id: string;
    };
}
