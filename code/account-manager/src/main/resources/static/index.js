const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {


}

const app = createApp(App)
app.use(vuetify).mount('#app')