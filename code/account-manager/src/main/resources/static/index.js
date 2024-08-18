const mydata = {
    data() {
        return {
            msg: "测试内容",
        };
    },
};

Vue.createApp(mydata).mount("#app");