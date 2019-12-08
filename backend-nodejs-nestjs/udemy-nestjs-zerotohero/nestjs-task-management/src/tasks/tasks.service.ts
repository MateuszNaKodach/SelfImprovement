import { Injectable } from '@nestjs/common';
import { Task, TaskStatus } from './task.model';
import * as uuid from 'uuid';

@Injectable()
export class TasksService {

  private readonly tasks: Task[] = [];

  getAllTasks(): Task[] {
    return this.tasks.slice();
  }

  createTask(title: string, description: string) {
    const task = {
      title,
      description,
      status: TaskStatus.OPEN,
      id: uuid.v4(),
    };
    this.tasks.push(task);
    return task;
  }

}
