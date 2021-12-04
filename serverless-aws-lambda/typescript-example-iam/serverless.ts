import type { AWS } from '@serverless/typescript';

import hello from '@functions/hello';

const serverlessConfiguration: AWS = {
  service: 'typescript-example-iam',
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
    },
    lambdaHashingVersion: '20201221',
    profile: 'meetmycode-serverless-admin',
    region: 'eu-central-1',
    iamRoleStatements: [
      {
        'Effect': 'Allow',
        'Action': ['lambda:*'],
        'Resource': '*'
      }
    ]
  },
  // import the function via paths
  functions: { hello },
};

module.exports = serverlessConfiguration;
