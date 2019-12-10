import { Entity, PrimaryColumn, PrimaryGeneratedColumn, Unique } from 'typeorm';

@Entity()
@Unique(['username'])
export class User {

  @PrimaryColumn('uuid')
  id: string;

  username: string;

  password: string;

  constructor(id: string, username: string, password: string) {
    this.id = id;
    this.username = username;
    this.password = password;
  }
}
