const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {

    data() {
        return {
            helloPage: true,
            managePage: false,
            migratePage: false,
            settingPage: false,
        }
    },

    methods: {

        changeShowPage(param) {
            if ('managePage' === param) {
                this.helloPage = false;
                this.managePage = true;
                this.migratePage = false;
                this.settingPage = false;
            } else if ('migratePage' === param) {
                this.helloPage = false;
                this.managePage = false;
                this.migratePage = true;
                this.settingPage = false;
            } else if ('settingPage' === param) {
                this.helloPage = false;
                this.managePage = false;
                this.migratePage = false;
                this.settingPage = true;
            } else {
                this.helloPage = true;
                this.managePage = false;
                this.migratePage = false;
                this.settingPage = false;
            }
        }

    }

}

const app = createApp(App)
app.use(vuetify).mount('#app')