const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {

    data() {
        return {

            // 搜索框
            

            // 分页
            pageNumber: 1,
            pageSize: 10,
            totalRecords: 1,
            
            // 列表表头、内容
            headers: [
              { key: 'name', title: '名称', sortable: false},
              { key: 'url', title: '网址', sortable: false},
              { key: 'username', title: '账号', sortable: false},
              { key: 'password', title: '密码', sortable: false},
              { key: 'remark', title: '备注', sortable: false},
              { key: 'create_time', title: '创建时间', sortable: false},
              { key: 'update_time', title: '更新时间', sortable: false},
            ],
            serverDatas: [
                {
                    "id": 123,
                    "name": "AA",
                    "url": "BB",
                    "username": "BB",
                    "password": "BB",
                    "remark": "BB",
                    "create_time": "BB",
                    "update_time": "BB",
                }
            ],
            
            loading: false,
            randomPassword: null,
            decryptFlag: true,
        }
    },

    methods: {

        // 随机密码
        apiGetRandomPassword() {
            axios.get('/accountmanager/password/get')
                .then(function (response) {
                    let resJson = response.data;
                    randomPassword = resJson.data;
                    console.log(randomPassword);
                })
                .catch(function (error) {
                })
                .finally(function () {
                });
        },

        // 根据id查询

        // 列表查询
        apiQueryList() {
            this.loading = true;
            axios.post('/accountmanager/account/query', {
                page_number: this.pageNumber,
                page_size: this.pageSize,
                decrypt: this.decryptFlag,
                fuzzy_name: null
            })
            .then(function (response) {
                let resJson = response.data;
                console.log(resJson);
                this.loading = false;
                this.serverDatas = [];
                this.totalRecords = resJson.data.total_records;
                
            })
            .catch(function (error) {
            })
            .finally(function () {
            });
        },

        // 新增

        // 修改

        // 删除

        // 导入
        
        // 导出

    },

}

const app = createApp(App)
app.use(vuetify).mount('#app')