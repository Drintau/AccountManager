const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {

    data() {
        return {
            itemsPerPage: 5,
            search: null,
            headers: [
              { key: 'name', title: '名称', sortable: false},
              { key: 'url', title: '网址', sortable: false},
              { key: 'username', title: '账号', sortable: false},
              { key: 'password', title: '密码', sortable: false},
              { key: 'remark', title: '备注', sortable: false},
              { key: 'create_time', title: '创建时间', sortable: false},
              { key: 'update_time', title: '更新时间', sortable: false},
            ],
            serverItems: [],
            totalItems: 0,
            loading: true,
        }
    },

    methods: {

        onClick () {
            let resJson = this.apiGetRandomPassword();
            console.log(resJson);
        },

        // 列表查询
        list(pageNumber, pageSize, decryptFlag) {
            this.loading = true;
            axios.post('/accountmanager/account/query', {
                page_number: pageNumber,
                page_size: pageSize,
                decrypt: decryptFlag,
                fuzzy_name: null
            })
            .then(function (response) {
                console.log(response.data);
                let resData = response.data;
                this.serverItems = resData.data;
                this.totalItems = resData.total_records;
                this.loading = false;
            })
            .catch(function (error) {

            })
            .finally(function () {
                console.log("发出请求");
            });
        },

        // 请求后端api
        // 随机密码
        apiGetRandomPassword() {
            axios.get('/accountmanager/password/get')
                .then(function (response) {
                    console.log(response.data);
                    return response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
                .finally(function () {
                    console.log("发出请求");
                });
        }   
        // 根据id查询
        // 列表查询
        // 新增
        // 修改
        // 删除
        // 导入
        // 导出

    },

}

const app = createApp(App)
app.use(vuetify).mount('#app')