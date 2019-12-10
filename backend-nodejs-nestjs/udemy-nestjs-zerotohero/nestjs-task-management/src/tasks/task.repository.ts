import { EntityRepository, Repository } from 'typeorm';
import { TaskEntity } from './task.entity';
import { TaskStatus } from './task-status.enum';
import { Task } from './task.model';

@EntityRepository(TaskEntity)
export class TaskRepository extends Repository<TaskEntity> {

  async getTasksFilteredBy(status: TaskStatus | undefined, search: string | undefined): Promise<TaskEntity[]> {
    const query = this.createQueryBuilder('task');
    if (status) {
      query.andWhere('task.status = :status', { status });
    }
    if (search) {
      query.andWhere('(LOWER(task.title) LIKE LOWER(:search) OR LOWER(task.description) LIKE LOWER(:search))', { search: `%${search}%` });
    }

    return query.getMany();
  }

}