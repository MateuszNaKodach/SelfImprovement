import { IsString, Matches, MaxLength, MinLength } from 'class-validator';

export class AuthCredentialsDto {

  @IsString()
  @MinLength(4)
  @MaxLength(20)
  username: string;

  @IsString()
  @MinLength(8)
  @MaxLength(20)
  @Matches(/((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/, { message: 'password too weak' })
  password: string;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
}
