module.exports = {
  preset: '@vue/cli-plugin-unit-jest',
  testMatch: [
    '<rootDir>/src/**/*.spec.(js|jsx|ts|tsx)|**/__tests__/*.(js|jsx|ts|tsx)',
  ],
  transformIgnorePatterns: ['node_modules/(?!axios)'],
};
