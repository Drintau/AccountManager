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

    },

}

const app = createApp(App)
app.use(vuetify).mount('#app')