const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {

    data() {
        return {

            // 是否解密
            decryptFlag: true,

            // 随机密码
            randomPassword: null,

            // 搜索框
            

            // 分页
            pageNumber: 1,
            pageSize: 5,
            pageSizeOptions: [{value: 5, title: '5'},{value: 10, title: '10'}],
            pageSizeOptionsText: "每页记录数",
            totalRecords: 0,
            
            // 数据列表
            headers: [
              { key: 'name', title: '名称', sortable: false},
              { key: 'url', title: '网址', sortable: false},
              { key: 'username', title: '账号', sortable: false},
              { key: 'password', title: '密码', sortable: false},
              { key: 'remark', title: '备注', sortable: false},
              { key: 'create_time', title: '创建时间', sortable: false},
              { key: 'update_time', title: '更新时间', sortable: false},
            ],
            pageItems: [],
            loading: false,
            
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

        // 分页页码变更
        handlePageChange(newPageNumber) {
          this.pageNumber = newPageNumber;
        },
        
        // 列表查询
        async loadItems() {
          this.loading = true;
          try {
            let response = await axios.post('/accountmanager/account/query',
                                        {
                                          page_number: this.pageNumber,
                                          page_size: this.pageSize,
                                          decrypt: this.decryptFlag,
                                          fuzzy_name: null
                                        });
            let resJson = response.data;
            this.pageItems = resJson.data.list;
            this.totalRecords = resJson.data.total_records;
            this.loading = false;
          } catch (error) {
            console.error(error);
          }
        }

        // 新增

        // 修改

        // 删除

        // 导入
        
        // 导出

    },

}

const app = createApp(App)
app.use(vuetify).mount('#app')