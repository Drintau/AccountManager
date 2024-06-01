const myMixin = {
  data() {
    return {
      mixinData: 'I am data from mixin.'
    };
  },
  methods: {
    mixinMethod() {
      console.log('This method is from mixin.');
    }
  }
};