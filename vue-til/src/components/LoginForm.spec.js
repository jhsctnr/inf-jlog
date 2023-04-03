import { shallowMount } from '@vue/test-utils';
// import Vue from 'vue';
import LoginForm from './LoginForm.vue';

describe('LoginForm.vue', () => {
  test('컴포넌트가 마운트되면 username이 존재하고 초기 값으로 설정되어 있어야 한다.', () => {
    // const instance = new Vue(LoginForm).$mount();
    const wrapper = shallowMount(LoginForm);
    expect(wrapper.vm.username).toBe('');
  });

  test('Id가 이메일 형식이 아니면 경고 메시지가 출력된다.', () => {
    const wrapper = shallowMount(LoginForm, {
      data() {
        return {
          username: 'test',
        };
      },
    });
    const warningText = wrapper.find('span.warning');
    // console.log(warningText.html());
    expect(warningText.exists()).toBeTruthy();
  });

  test('Id와 pw가 입력되지 않으면 로그인 버튼이 비활성화 된다.', () => {
    const wrapper = shallowMount(LoginForm, {
      data() {
        return {
          username: '',
          password: '',
        };
      },
    });
    const button = wrapper.find('button.btn');
    expect(button.element.disabled).toBeTruthy();
  });
});
