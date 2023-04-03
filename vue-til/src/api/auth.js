import { instance } from './index';

// 로그인, 회원 가입, 회원 탈퇴
// 회원가입 API
function registerUser(userData) {
  return instance.post('auth/signup', userData);
}

// 로그인 API
function loginUser(userData) {
  return instance.post('auth/login', userData);
}

export { registerUser, loginUser };
