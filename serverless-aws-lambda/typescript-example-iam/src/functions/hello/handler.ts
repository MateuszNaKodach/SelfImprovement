import type { ValidatedEventAPIGatewayProxyEvent } from '@libs/apiGateway';
import { formatJSONResponse } from '@libs/apiGateway';
import { middyfy } from '@libs/lambda';
import {LambdaClient, ListFunctionsCommand} from "@aws-sdk/client-lambda";

import schema from './schema';

const hello: ValidatedEventAPIGatewayProxyEvent<typeof schema> = async (event) => {
  const client = new LambdaClient({region: process.env.AWS_REGION})
  const output = await client.send(new ListFunctionsCommand({}))

  return formatJSONResponse({
    functions: output.Functions
  });
}

export const main = middyfy(hello);
