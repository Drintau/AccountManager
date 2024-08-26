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

        // 随机密码
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

        // 列表查询
        list(pageNumber, pageSize, decryptFlag) {
            axios.post('/accountmanager/account/query', {
                page_number: pageNumber,
                page_size: pageSize,
                decrypt: decryptFlag,
                fuzzy_name: null
            })
            .then(function (response) {
                console.log(response.data);
            })
            .catch(function (error) {

            })
            .finally(function () {
                console.log("发出请求");
            });
        },

    },

}

const app = createApp(App)
app.use(vuetify).mount('#app')