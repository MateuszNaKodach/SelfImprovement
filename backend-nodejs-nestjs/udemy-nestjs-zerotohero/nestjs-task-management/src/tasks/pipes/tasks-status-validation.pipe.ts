import { BadRequestException, PipeTransform } from '@nestjs/common';
import { TaskStatus } from '../task.model';

export class TaskStatusValidationPipe implements PipeTransform {

  transform(value: string): any {
    const input = value.toUpperCase();
    const status: TaskStatus = TaskStatus[input as keyof typeof TaskStatus];
    if (!status) {
      throw new BadRequestException(`"${input}" is an invalid status`);
    }
    return value;
  }

}
