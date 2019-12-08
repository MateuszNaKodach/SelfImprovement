import { TaskStatus } from '../task.model';

export class GetTasksFilterDto {

  constructor(public status: TaskStatus, public search: string) {

  }

}
