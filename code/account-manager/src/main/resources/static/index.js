const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {
    data() {
        return {

        }
    },
    methods: {

    },
}

const app = createApp(App)
app.use(vuetify).mount('#app')