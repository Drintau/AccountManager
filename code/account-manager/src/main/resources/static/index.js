const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {

    data() {
        return {

        }
    },

    methods: {

        onClick () {
            console.log("Hello");
        },
        onClick2 () {
            console.log("Hello2");
        },

        // 获取随机密码
        getRandomPassword() {
            axios.get('/accountmanager/password/get')
                .then(function (response) {
                    console.log(response.data);
                    console.log(response.data.code);
                    console.log(response.data.message);
                    console.log(response.data.data);
                })
                .catch(function (error) {
                    console.log(error);
                })
                .finally(function () {
                    console.log("发出请求");
                });
        },

    },

}

const app = createApp(App)
app.use(vuetify).mount('#app')