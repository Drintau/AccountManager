const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {
  // 规定：
  // 前端表格一行数据称为 rowData （复数 rowDatas ），其中与后端交互的真实数据部分称为 recordData （复数 recordDatas ），单独的一条记录也称为 recordData
  // 新增用 add ，编辑用 edit ，删除用 del ，查询用 query

  // 数据定义、配置
  data() {
    return {

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
        { key: 'id', title: 'ID', sortable: false },
        { key: 'name', title: '名称', sortable: false },
        { key: 'url', title: '网址', sortable: false },
        { key: 'username', title: '账号', sortable: false },
        { key: 'password', title: '密码', sortable: false },
        { key: 'remark', title: '备注', sortable: false },
        { key: 'create_time', title: '创建时间', sortable: false },
        { key: 'update_time', title: '更新时间', sortable: false },
        { key: 'actions', title: '操作', sortable: false },
      ],
      recordDatas: [],
      loading: false,

      // 记录数据
      recordDataDialogFlag: false,
      recordDataId: null,
      recordDataName: null,
      recordDataUrl: null,
      recordDataUsername: null,
      recordDataPassword: null,
      recordDataRemark: null,

      // 导入导出
      imexDialogFlag: false,
      uploadFile: null,

      // 删除
      delDialogFlag: false,
      delId: null,

      // 报错信息
      snackbar: false,
      timeout: 5000,
      errMsg: null,
      
    }
  },

  // 方法
  methods: {
    // 规定：这里的方法属于前端使用，命名跟前端操作或者数据变更含义一致，里面调用后端的方法只是其操作的一部分

    // 随机密码
    async getRandomPassword() {
      try {
        let response = await axios.get('/accountmanager/password/get');
        let resJson = response.data;
        this.handleResJson(resJson);
        return resJson.data;
      } catch (error) {
        console.error(error);
      }
    },

    // 查询一条记录数据
    async queryRecordData(recordId) {
      try {
        let response = await axios.get('/accountmanager/account/get', 
                                      {
                                        params: {
                                          id: recordId
                                        }
                                      });
        let resJson = response.data;
        this.handleResJson(resJson);
        return resJson.data;
      } catch (error) {
        console.error(error);
      }
    },

    // 分页页码变更
    handlePageChange(newPageNumber) {
      this.pageNumber = newPageNumber;
    },

    // 列表查询
    async queryRecordDatas() {
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
        this.items = resJson.data.list;
        this.totalRecords = resJson.data.total_records;
        this.loading = false;
      } catch (error) {
        console.error(error);
      }
    },

    // 新增
    async addRecordData() {
      try {
        let response = await axios.post('/accountmanager/account/add',
                                       {
                                         name: this.recordDataName,
                                         url: this.recordDataUrl,
                                         username: this.recordDataUsername,
                                         password: this.recordDataPassword,
                                         remark: this.recordDataRemark
                                       });
        let resJson = response.data;
        let succesFlag = this.handleResJson(resJson);
        if(succesFlag) {
          this.clearDataDialog();
          this.queryItems();
        }
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

    // 选择文件
    handleFileChange(event) {
      this.uploadFile = event.target.files[0];
    },

    // 导入
    async importAccount() {
      if(this.uploadFile === null) {
        this.errMsg = "请选择文件";
        this.snackbar = true;
        return;
      }
      let response = await axios({
                                  method: 'post',
                                  url: '/accountmanager/account/import',
                                  data: {
                                    file: this.uploadFile
                                  },
                                  headers: {
                                    'Content-Type': 'multipart/form-data'
                                  }
                                });
      let resJson = response.data;
      let succesFlag = this.handleResJson(resJson);
      if(succesFlag) {
        this.clearImexDialog();
        this.queryItems();
      }
    },

    // 导出
    exportAccount() {
      window.location.href='/accountmanager/account/export';
    },

    // 删除对话框-弹出
    delDialog(rowItem) {
      let itemData = Object.assign({}, rowItem);
      this.delId = itemData.id;
      this.delDialogFlag = true;
    },
    // 删除对话框-取消
    clearDelDialog() {
      this.delId = null;
      this.delDialog = false;
    },
    // 删除对话框-确定
    async confirmDel() {
      try {
        let response = await axios.post('/accountmanager/account/delete',
                                       {
                                         id: this.delId
                                       });
        let resJson = response.data;
        let succesFlag = this.handleResJson(resJson);
        if(succesFlag) {
          this.clearDelDialog();
          this.queryItems();
        }
      } catch (error) {
        console.error(error);
      }
    },

    // 处理响应
    handleResJson(resJson) {
      let bizCode = resJson.code;
      if("000000" == bizCode) {
        return true;
      } else {
        this.errMsg = resJson.message;
        this.snackbar = true;
        return false;
      }
    },

    // 清空数据对话框内容
    clearDataDialog() {
      this.dataDialogFlag = false;
      this.dataId = null;
      this.dataName = null;
      this.dataUrl = null;
      this.dataUsername = null;
      this.dataPassword = null;
      this.dataRemark = null;
    },

    // 清空上传文件
    clearImexDialog() {
      this.uploadFile = null;
      this.imexDialog = false;
    }

  },

}

const app = createApp(App)
app.use(vuetify).mount('#app')