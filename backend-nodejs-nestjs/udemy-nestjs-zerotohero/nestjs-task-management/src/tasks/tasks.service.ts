import { Injectable, NotFoundException } from '@nestjs/common';
import { Task } from './task.model';
import * as uuid from 'uuid';
import { CreateTaskDto } from './dto/create-task.dto';
import { GetTasksFilterDto } from './dto/get-tasks-filter.dto';
import { TaskStatus } from './task-status.enum';
import { TaskRepository } from './task.repository';
import { InjectRepository } from '@nestjs/typeorm';
import { TaskEntity } from './task.entity';

@Injectable()
export class TasksService {

  constructor(@InjectRepository(TaskRepository)
              private readonly repository: TaskRepository) {
  }

  getTasks({ status, search }: GetTasksFilterDto): Promise<Task[]> {
    return this.repository.getTasksFilteredBy(status, search);
  }

  async getTaskById(id: string): Promise<Task> {
    const found = await this.repository.findOne(id);
    if (!found) {
      throw new NotFoundException(`Task with ID ${id} not found`);
    }
    return found;
  }

  createTask(createTaskDto: CreateTaskDto): Promise<Task> {
    const task = new TaskEntity(
      uuid.v4(),
      createTaskDto.title,
      createTaskDto.description,
      TaskStatus.OPEN,
    );
    return this.repository.save(task);
  }

  async deleteTask(id: string): Promise<void> {
    const { affected } = await this.repository.delete({ id });

    if (affected === 0) {
      throw new NotFoundException(`Task with ID ${id} not found`);
    }
  }

  async updateTaskStatus(id: string, status: TaskStatus): Promise<Task> {
    const task = await this.getTaskById(id);
    task.status = status;
    return this.repository.save(task);
  }
}
