import { Injectable } from '@nestjs/common';
import { Task, TaskStatus } from './task.model';
import * as uuid from 'uuid';
import { CreateTaskDto } from './dto/create-task.dto';
import { GetTasksFilterDto } from './dto/get-tasks-filter.dto';

@Injectable()
export class TasksService {

  private readonly tasks: Task[] = [];

  getAllTasks(): Task[] {
    return this.tasks.slice();
  }

  getTasksFilteredBy(filterDto: GetTasksFilterDto): Task[] {
    const { status, search } = filterDto;
    let tasks = this.getAllTasks();
    if (status) {
      tasks = tasks.filter(task => task.status === status);
    }
    if (search) {
      tasks = tasks.filter(task =>
        (task.title + task.description).toLowerCase().includes(search.toLowerCase()),
      );
    }
    return tasks;
  }

  getTaskById(id: string): Task | undefined {
    return this.tasks.find(task => task.id === id);
  }

  createTask(createTaskDto: CreateTaskDto): Task {
    const task = {
      ...createTaskDto,
      status: TaskStatus.OPEN,
      id: uuid.v4(),
    };
    this.tasks.push(task);
    return task;
  }

  deleteTask(id: string): void {
    const toDeleteIndex = this.tasks.findIndex(task => task.id !== id);
    if (toDeleteIndex !== -1) {
      delete this.tasks[toDeleteIndex];
    }
  }

  updateTaskStatus(id: string, status: TaskStatus): Task | undefined {
    const task = this.getTaskById(id);
    if (task) {
      task.status = status;
    }
    return task;
  }
}
