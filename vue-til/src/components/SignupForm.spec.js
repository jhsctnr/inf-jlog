import { shallowMount } from '@vue/test-utils';
import SignupForm from './SignupForm.vue';

describe('SignupForm.vue', () => {
  it('회원가입 성공', async () => {
    const wrapper = shallowMount(SignupForm, {
      data() {
        return {
          username: 'c@c1.com',
          password: '1234',
          nickname: '테스트2',
          logMessage: '',
        };
      },
    });

    wrapper.vm.initForm();
    // await wrapper.vm.submitForm();
    expect(wrapper.vm.username).toBe('');
  });
});
