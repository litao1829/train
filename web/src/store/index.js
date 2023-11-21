import { createStore } from "vuex";

export default createStore({
  state: {},
  getters: {},
  mutations: {
    setMember(state, _member) {
      state.member = _member;
    },
  },
  actions: {},
  modules: {},
});
