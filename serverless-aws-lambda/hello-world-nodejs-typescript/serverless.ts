import type { AWS } from '@serverless/typescript';

import hello from '@functions/hello';

const serverlessConfiguration: AWS = {
  service: 'hello-world-nodejs-typescript',
  frameworkVersion: '2',
  custom: {
    esbuild: {
      bundle: true,
      minify: false,
      sourcemap: true,
      exclude: ['aws-sdk'],
      target: 'node14',
      define: { 'require.resolve': undefined },
      platform: 'node',
    },
  },
  plugins: ['serverless-esbuild'],
  provider: {
    name: 'aws',
    runtime: 'nodejs14.x',
    apiGateway: {
      minimumCompressionSize: 1024,
      shouldStartNameWithService: true,
    },
    environment: {
      AWS_NODEJS_CONNECTION_REUSE_ENABLED: '1',
      NODE_OPTIONS: '--enable-source-maps --stack-trace-limit=1000',
      ENV_VARIABLE_2: 'env_var_2'
    },
    lambdaHashingVersion: '20201221',
    profile: 'meetmycode-serverless-admin',
    region: 'eu-central-1'
  },
  // import the function via paths
  functions: { helloShortTimeout: {...hello, timeout: 3, memorySize: 128 }, helloLongTimeout: {...hello, timeout: 6, memorySize: 256 } },
};

module.exports = serverlessConfiguration;
