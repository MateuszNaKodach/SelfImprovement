import { TaskStatus } from '../task.model';
import { IsEnum, IsIn, IsNotEmpty, IsOptional } from 'class-validator';

export class GetTasksFilterDto {

  @IsOptional()
  @IsEnum(TaskStatus)
  status: TaskStatus;

  @IsOptional()
  @IsNotEmpty()
  search: string;

  constructor(status: TaskStatus, search: string) {
    this.search = search;
    this.status = status;

  }

}
