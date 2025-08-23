const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {
  // 规定：
  // 前端表格一行数据称为 rowData （复数 rowDatas ），其中与后端交互的真实数据部分称为 recordData （复数 recordDatas ），单独的一条记录也称为 recordData
  // 新增用 add ，编辑用 edit ，删除用 del ，查询用 query，处理事件用 handle，导入用 import，导出用 export，清除用 clear，展示用 show；后面加变量名/业务名

  // 数据定义、配置
  data() {
    return {

      // 查询是否解密
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
//        { key: 'create_time', title: '创建时间', sortable: false },
//        { key: 'update_time', title: '更新时间', sortable: false },
        { key: 'actions', title: '操作', sortable: false },
      ],
      recordDatas: [],
      loading: false,

      // 一条记录的数据
      recordDataDialogFlag: false,
      recordDataDialogTitle: null,
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
    // 规定：
    // 请求后端接口不用单独一个方法出来，目前没有能够重复用的，以后有再拆分

    // 处理分页页码变更
    handlePageNumberChange(newPageNumber) {
      this.pageNumber = newPageNumber;
    },
    // 查询一批数据
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
        this.recordDatas = resJson.data.list;
        this.totalRecords = resJson.data.total_records;
        this.loading = false;
      } catch (error) {
        console.error(error);
      }
    },

    // 查询随机密码
    async queryRandomPassword() {
      try {
        let response = await axios.get('/accountmanager/password/get');
        let resJson = response.data;
        this.handleResJson(resJson);
        this.recordDataPassword = resJson.data;
      } catch (error) {
        console.error(error);
      }
    },
    // 展示一条记录数据对话框内容
    showRecordDataDialog(rowData) {
      if (rowData != null) {
        this.recordDataDialogTitle = '修改账号';
        let recordData = Object.assign({}, rowData);
        this.recordDataId = recordData.id;
        this.recordDataName = recordData.name;
        this.recordDataUrl = recordData.url;
        this.recordDataUsername = recordData.username;
        this.recordDataPassword = recordData.password;
        this.recordDataRemark = recordData.remark;
      } else {
        this.clearRecordDataDialog();
        this.recordDataDialogTitle = '新增账号';
      }
      this.recordDataDialogFlag = true;
    },
    // 新增或编辑记录
    async addOrEditRecordData() {
      if (this.recordDataId != null) {
        try {
          let response = await axios.post('/accountmanager/account/update',
                                         {
                                           id: this.recordDataId,
                                           name: this.recordDataName,
                                           url: this.recordDataUrl,
                                           username: this.recordDataUsername,
                                           password: this.recordDataPassword,
                                           remark: this.recordDataRemark
                                         });
          let resJson = response.data;
          let succesFlag = this.handleResJson(resJson);
          if (succesFlag) {
            this.clearRecordDataDialog();
            this.queryRecordDatas();
          }
        } catch (error) {
          console.error(error);
        }
      } else {
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
          if (succesFlag) {
            this.clearRecordDataDialog();
            this.queryRecordDatas();
          }
        } catch (error) {
          console.error(error);
        }
      }
    },
    // 清空一条记录数据对话框内容
    clearRecordDataDialog() {
      this.recordDataDialogFlag = false;
      this.recordDataId = null;
      this.recordDataName = null;
      this.recordDataUrl = null;
      this.recordDataUsername = null;
      this.recordDataPassword = null;
      this.recordDataRemark = null;
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

    // 展示导入导出对话框
    showImexDialog() {
      this.imexDialogFlag = true;
    },
    // 处理上传文件变更
    handleUploadFileChange(event) {
      this.uploadFile = event.target.files[0];
    },
    // 导入记录
    async importRecordDatas() {
      if (this.uploadFile === null) {
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
      if (succesFlag) {
        this.clearImexDialog();
        this.queryRecordDatas();
      }
    },
    // 导出
    exportRecordDatas() {
      window.location.href = '/accountmanager/account/export';
    },
    // 清空上传文件
    clearImexDialog() {
      this.uploadFile = null;
      this.imexDialogFlag = false;
    },

    // 删除对话框-展示
    showDelDialog(rowData) {
      let recordData = Object.assign({}, rowData);
      this.delId = recordData.id;
      this.delDialogFlag = true;
    },
    // 删除对话框-取消
    clearDelDialog() {
      this.delId = null;
      this.delDialogFlag = false;
    },
    // 删除对话框-确定
    async delRecordData() {
      try {
        let response = await axios.post('/accountmanager/account/delete',
                                       {
                                         id: this.delId
                                       });
        let resJson = response.data;
        let succesFlag = this.handleResJson(resJson);
        if (succesFlag) {
          this.clearDelDialog();
          this.queryRecordDatas();
        }
      } catch (error) {
        console.error(error);
      }
    },

    // 处理响应
    handleResJson(resJson) {
      let bizCode = resJson.code;
      if ("000000" == bizCode) {
        return true;
      } else {
        this.errMsg = resJson.message;
        this.snackbar = true;
        return false;
      }
    },

  },

}

const app = createApp(App)
app.use(vuetify).mount('#app')