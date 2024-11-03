const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {

  // 数据定义、配置
  data() {
    return {
      // 规定：js 数据里面的属性命名倾向后端含义，方便传参识别，前端框架的属性命名是框架的规则

      // 是否解密
      decryptFlag: true,

      // 搜索框
      fuzzyName: null,

      // 分页
      pageNumber: 1,
      pageSize: 8,
      pageSizeOptions: [{ value: 5, title: '5' }, { value: 8, title: '8' }, { value: 10, title: '10' }],
      pageSizeOptionsText: "每页记录数",
      totalRecords: 0,

      // 数据列表
      headers: [
        { key: 'name', title: '名称', sortable: false },
        { key: 'url', title: '网址', sortable: false },
        { key: 'username', title: '账号', sortable: false },
        { key: 'password', title: '密码', sortable: false },
        { key: 'remark', title: '备注', sortable: false },
        { key: 'create_time', title: '创建时间', sortable: false },
        { key: 'update_time', title: '更新时间', sortable: false },
      ],
      pageData: [],
      loading: false,

      // 随机密码
      randomPassword: null,

      // 新增对话框
      addDialog: false,
      addAppName: null,
      addAppUrl: null,
      addUsername: null,
      addPassword: null,
      addRemark: null,
    }
  },

  // 方法
  methods: {
    // 规定：这里的方法属于前端使用，命令跟前端操作或者数据变更含义一致，里面调用后端的方法只是其操作的一部分

    // 随机密码
    async getRandomPassword() {
      try {
        let response = await axios.get('/accountmanager/password/get');
        let resJson = response.data;
        this.handleResJson(resJson);
        this.randomPassword = resJson.data;
        return resJson.data;
      } catch (error) {
        console.error(error);
      }
    },

    // 根据id查询
    async queryById() {

    },

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
                                         fuzzy_name: this.fuzzyName
                                       });
        let resJson = response.data;
        this.handleResJson(resJson);
        this.pageData = resJson.data.list;
        this.totalRecords = resJson.data.total_records;
        this.loading = false;
      } catch (error) {
        console.error(error);
      }
    },

    // 新增
    async addAccount() {
      try {
        let response = await axios.post('/accountmanager/account/add',
                                       {
                                         name: this.addAppName,
                                         url: this.addAppUrl,
                                         username: this.addUsername,
                                         password: this.addPassword,
                                         remark: this.addRemark
                                       });
        let resJson = response.data;
        this.handleResJson(resJson);
        this.clearAddDialog();
        this.loadItems();
      } catch (error) {
        console.error(error);
      }
    },

    // 新增 随机密码
    async addRandomPassword() {
      let randomPassword = await this.getRandomPassword();
      this.addPassword = randomPassword;
    },

    // 修改
    async editAccount() {

    },

    // 删除
    async deleteAccount() {

    },

    // 导入
    async importAccount() {

    },

    // 导出
    exportAccount() {
      window.location.href='/accountmanager/account/export';
    },

    // 处理响应
    handleResJson(resJson) {
      let bizCode = resJson.code;
      if("000000" == bizCode) {

      } else {
        console.log(resJson);
      }
    },

    // 清空新增对话框内容
    clearAddDialog() {
      this.addDialog = false;
      this.addAppName = null;
      this.addAppUrl = null;
      this.addUsername = null;
      this.addPassword = null;
      this.addRemark = null;
    },

  },

}

const app = createApp(App)
app.use(vuetify).mount('#app')