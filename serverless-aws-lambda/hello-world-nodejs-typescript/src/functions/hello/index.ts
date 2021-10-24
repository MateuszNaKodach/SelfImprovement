import schema from './schema';
import { handlerPath } from '@libs/handlerResolver';
import {AWS} from "@serverless/typescript";

type ValueOf<T> = T[keyof T];


const helloFunction : ValueOf<AWS['functions']> = {
  handler: `${handlerPath(__dirname)}/handler.main`,
  events: [
    {
      http: {
        method: 'post',
        path: 'hello',
        request: {
          schema: {
            'application/json': schema
          }
        }
      }
    }
  ]
};
export default helloFunction
