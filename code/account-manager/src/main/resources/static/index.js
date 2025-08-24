const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {

    data() {
        return {
            show1: false,
            show2: false,
        }
    },

    methods: {

        changeShow(num) {
            if (num == 1) {
                this.show1 = true;
                this.show2 = false;
            } else if (num == 2) {
                this.show1 = false;
                this.show2 = true;
            } else {
                this.show1 = true;
                this.show2 = true;
            }
        }

    }

}

const app = createApp(App)
app.use(vuetify).mount('#app')