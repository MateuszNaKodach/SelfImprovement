import { Column, Entity, PrimaryColumn, PrimaryGeneratedColumn } from 'typeorm';
import { TaskStatus } from './task-status.enum';
import { Task as TaskModel } from './task.model';

@Entity()
export class TaskEntity implements TaskModel {

  @PrimaryColumn('uuid')
  id: string;

  @Column()
  title: string;

  @Column()
  description: string;

  @Column()
  status: TaskStatus;

  constructor(id: string, title: string, description: string, status: TaskStatus) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
  }
}
