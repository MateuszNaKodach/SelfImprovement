import type {ValidatedEventAPIGatewayProxyEvent} from '@libs/apiGateway';
import {formatJSONResponse} from '@libs/apiGateway';
import {middyfy} from '@libs/lambda';

import schema from './schema';

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

const hello: ValidatedEventAPIGatewayProxyEvent<typeof schema> = async (event) => {
  await sleep(4000)

  return formatJSONResponse({
    message: `UPDATED | Hello SUPER, welcome to the exciting Serverless world!`,
    event,
  });
}


export const main = middyfy(hello);
