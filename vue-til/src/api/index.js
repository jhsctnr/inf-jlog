import axios from 'axios';
import { setInterceptors } from './common/interceptors';

function createInstance() {
  return axios.create({
    baseURL: '/api',
  });
}

// 엑시오스 초기화 함수
function createInstanceWithAuth(url) {
  const instance = axios.create({
    baseURL: url,
  });
  return setInterceptors(instance);
}

function createInstanceWithAuthForProd(url) {
  const instance = axios.create({
    baseURL: url,
  });
  return setInterceptors(instance);
}

export const instance = createInstance();
export const posts = createInstanceWithAuth('/api/posts');
export const products = createInstanceWithAuthForProd('/api');
