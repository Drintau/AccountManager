const { createApp } = Vue
const { createVuetify } = Vuetify

const vuetify = createVuetify()

const App = {
  // 规定：
  // 命名尽量跟后端对齐
  // 模块：账号 account ，分类 category ，迁移 transfer ，设置 config ，欢迎 hello ，报错 err
  // 与后端交互业务：新增 add ，编辑 edit （包含增删改操作），删除 del ，查询 query ，上传 upload ，导入 import，导出 export
  // 与后端交互数据：请求 req ，响应 res
  // 前端组件：表格列表 table，表格一行 row
  // 前端组件数据：一条 Data ，多条 Datas
  // 前端组件业务：展示 show ，清除 clear ，处理事件 handle
  // 其他名称：标记 Flag ，字段名 ，组件属性名 ，信息 Msg ，全部 All
  // 属性名：模块+交互业务+前端组件名+（前端数据+字段名）/（前端组件业务+标记），如 categoryEditDialogShowFalg
  // 方法名：模块+交互业务+前端组件名+前端组件业务，如 accountTableShow ，categoryEditDialogShow ， categoryDelDialogShow
  // 注意：无对应，或者对应多个，则不写

  // 数据定义、配置
  data() {
    return {

      // 页面显示控制
      helloShowFlag: true,
      accountShowFlag: false,
      categoryShowFlag: false,
      transferShowFlag: false,
      configShowFlag: false,

      // 账号管理模块 account
      // 是否解密
      accountDecryptFlag: true,
      // 账号列表分类筛选
      accountTableQueryCategoryId: null,
      // 账号列表应用名称搜索框
      accountTableQueryKeywordAppName: null,
      // 分页
      accountTablePageNum: 1,
      accountTablePageSize: 8,
      accountTablePageSizeOptions: [{ value: 5, title: '5' }, { value: 8, title: '8' }, { value: 10, title: '10' }],
      accountTablePageSizeOptionsText: "每页记录数",
      accountTableTotal: 0,
      // 账号列表表头
      accountTableHeaders: [
        { key: 'id', title: 'ID', sortable: false },
        { key: 'app_name', title: '名称', sortable: false },
        { key: 'app_url', title: '网址', sortable: false },
        { key: 'username', title: '账号', sortable: false },
        { key: 'password', title: '密码', sortable: false },
        { key: 'remark', title: '备注', sortable: false },
        //        { key: 'create_time', title: '创建时间', sortable: false },
        //        { key: 'update_time', title: '更新时间', sortable: false },
        { key: 'actions', title: '操作', sortable: false },
      ],
      accountTableDatas: [],
      accountTableLoading: false,
      // 一条账号记录的数据
      accountDialogShowFlag: false,
      accountDialogTitle: null,
      accountDialogDataId: null,
      accountDialogDataAppName: null,
      accountDialogDataAppUrl: null,
      accountDialogDataUsername: null,
      accountDialogDataPassword: null,
      accountDialogDataRemark: null,
      // 删除
      delDialogFlag: false,
      delId: null,

      // 分类管理模块 category
      // 分类表格属性
      categoryTableHeaders: [
        { key: 'id', title: 'ID', sortable: false, headerProps: { class: 'd-none' }, cellProps: { class: 'd-none' }},
        { key: 'category_name', title: '分类名称', sortable: false },
        { key: 'actions', title: '操作', sortable: false, align: 'end' },
      ],
      categoryTableDatas: [],
      categoryTableLoading: false,
      // 一条分类记录的数据
      categoryDialogDataId: null,
      categoryDialogDataCategoryName: null,
      // 分类编辑对话框属性
      categoryEditDialogShowFlag: false,
      categoryEditDialogTitle: null,
      // 分类删除对话框属性
      categoryDelDialogShowFlag: false,

      // 迁移模块 transfer
      transferDialogShowFlag: false,
      uploadFile: null,

      // 系统设置模块 config
      configTableHeaders: [
        { key: 'id', title: 'ID', sortable: false, headerProps: { class: 'd-none' }, cellProps: { class: 'd-none' }},
        { key: 'config_key', title: '配置项', sortable: false },
        { key: 'config_value', title: '配置值', sortable: false },
        { key: 'remark', title: '说明', sortable: false },
        { key: 'actions', title: '操作', sortable: false, align: 'end' },
      ],
      configTableDatas: [],
      configTableLoading: false,
      // 一条配置记录的数据
      configEditDialogShowFlag: false,
      configDialogDataId: null,
      configDialogDataConfigKey: null,
      configDialogDataConfigValue: null,
      configDialogDataRemark: null,

      // 报错信息
      errSnackbarShowFlag: false,
      errSnackbarTimeout: 5000,
      errSnackbarMsg: null,

    }
  },

  // 方法
  methods: {
    // 规定：
    // 请求后端接口不用单独一个方法出来，目前没有能够重复用的，以后有再拆分

    // 页面显示控制
    moduleShow(param) {
      if ('accountShowFlag' === param) {
        this.helloShowFlag = false;
        this.accountShowFlag = true;
        this.categoryShowFlag = false;
        this.transferShowFlag = false;
        this.configShowFlag = false;
      } else if ('categoryShowFlag' === param) {
        this.helloShowFlag = false;
        this.accountShowFlag = false;
        this.categoryShowFlag = true;
        this.transferShowFlag = false;
        this.configShowFlag = false;
      } else if ('transferShowFlag' === param) {
        this.helloShowFlag = false;
        this.accountShowFlag = false;
        this.categoryShowFlag = false;
        this.transferShowFlag = true;
        this.configShowFlag = false;
      } else if ('configShowFlag' === param) {
        this.helloShowFlag = false;
        this.accountShowFlag = false;
        this.categoryShowFlag = false;
        this.transferShowFlag = false;
        this.configShowFlag = true;
      } else {
        this.helloShowFlag = true;
        this.accountShowFlag = false;
        this.categoryShowFlag = false;
        this.transferShowFlag = false;
        this.configShowFlag = false;
      }
    },

    // 处理分页页码变更
    handlePageNumberChange(newPageNumber) {
      this.pageNumber = newPageNumber;
    },
    // 查询一批数据
    async queryRecordDatas() {
      this.loading = true;
      try {
        let response = await axios.post('/accountmanager/account/find',
          {
            page_num: this.pageNumber,
            page_size: this.pageSize,
            decrypt: this.decryptFlag,
            category_id: this.categoryId,
            keyword_app_name: this.keywordAppName
          });
        let resJson = response.data;
        this.handleRes(resJson);
        this.recordDatas = resJson.data.list;
        this.totalRecords = resJson.data.total;
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
        this.handleRes(resJson);
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
          let successFlag = this.handleRes(resJson);
          if (successFlag) {
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
          let successFlag = this.handleRes(resJson);
          if (successFlag) {
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
        this.handleRes(resJson);
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
    // 导入数据
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
      let successFlag = this.handleRes(resJson);
      if (successFlag) {
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
        let successFlag = this.handleRes(resJson);
        if (successFlag) {
          this.clearDelDialog();
          this.queryRecordDatas();
        }
      } catch (error) {
        console.error(error);
      }
    },

    // 分类-查询全部
    async categoryTableQueryAll() {
      this.categoryTableLoading = true;
      try {
        let response = await axios.post('/accountmanager/category/all',{});
        let res = response.data;
        this.handleRes(res);
        this.categoryTableDatas = res.data.list;
        this.categoryTableLoading = false;
      } catch (error) {
        console.error(error);
      }
    },
    // 分类-清理编辑对话框
    categoryEditDialogClear() {
      this.categoryEditDialogShowFlag = false;
      this.categoryDialogDataId = null;
      this.categoryDialogDataCategoryName = null;
      // this.categoryEditDialogTitle = null; 这一句会让Dialog关闭时高度变化，不好看
    },
    // 分类-展示编辑对话框
    categoryEditDialogShow(categoryRow) {
      this.categoryEditDialogClear();
      if (categoryRow != null) {
        this.categoryEditDialogTitle = '修改分类';
        let categoryDialogData = Object.assign({}, categoryRow);
        this.categoryDialogDataId = categoryDialogData.id;
        this.categoryDialogDataCategoryName = categoryDialogData.category_name;
      } else {
        this.categoryEditDialogTitle = '新增分类';
      }
      this.categoryEditDialogShowFlag = true;
    },
    // 分类-编辑
    async categoryEdit() {
      if (this.categoryDialogDataId != null) {
        try {
          let response = await axios.post('/accountmanager/category/update',
            {
              id: this.categoryDialogDataId,
              category_name: this.categoryDialogDataCategoryName
            });
          let res = response.data;
          let successFlag = this.handleRes(res);
          if (successFlag) {
            this.categoryEditDialogClear();
            this.categoryTableQueryAll();
          }
        } catch (error) {
          console.error(error);
        }
      } else {
        try {
          let response = await axios.post('/accountmanager/category/add',
            {
              category_name: this.categoryDialogDataCategoryName
            });
          let res = response.data;
          let successFlag = this.handleRes(res);
          if (successFlag) {
            this.categoryEditDialogClear();
            this.categoryTableQueryAll();
          }
        } catch (error) {
          console.error(error);
        }
      }
    },
    // 分类-展示删除确认框
    categoryDelDialogShow(categoryRow) {
      let categoryDelDialogData = Object.assign({}, categoryRow);
      this.categoryDialogDataId = categoryDelDialogData.id;
      this.categoryDialogDataCategoryName = categoryDelDialogData.category_name;
      this.categoryDelDialogShowFlag = true;
    },
    // 分类-清理删除对话框
    categoryDelDialogClear() {
      this.categoryDelDialogShowFlag = false;
      this.categoryDialogDataId = null;
      this.categoryDialogDataCategoryName = null;
    },
    // 分类-删除
    async categoryDel() {
      try {
        let response = await axios.post('/accountmanager/category/delete',
          {
            id: this.categoryDialogDataId
          });
        let res = response.data;
        let successFlag = this.handleRes(res);
        if (successFlag) {
          this.categoryDelDialogClear();
          this.categoryTableQueryAll();
        }
      } catch (error) {
        console.error(error);
      }
    },

    // 设置-查询全部
    async configTableQueryAll() {
      this.configTableLoading = true;
      try {
        let response = await axios.post('/accountmanager/config/all',{});
        let res = response.data;
        this.handleRes(res);
        this.configTableDatas = res.data.list;
        this.configTableLoading = false;
      } catch (error) {
        console.error(error);
      }
    },
    // 设置-清理编辑对话框
    configEditDialogClear() {
      this.configEditDialogShowFlag = false;
      this.configDialogDataId = null;
      this.configDialogDataConfigKey = null;
      this.configDialogDataConfigValue = null;
      this.configDialogDataRemark = null;
    },
    // 设置-展示编辑对话框
    configEditDialogShow(configRow) {
      let configDialogData = Object.assign({}, configRow);
      this.configDialogDataId = configDialogData.id;
      this.configDialogDataConfigKey = configDialogData.config_key;
      this.configDialogDataConfigValue = configDialogData.config_value;
      this.configDialogDataRemark = configDialogData.remark;
      this.configEditDialogShowFlag = true;
    },
    // 设置-编辑
    async configEdit() {
      try {
        let response = await axios.post('/accountmanager/config/update',
          {
            config_key: this.configDialogDataConfigKey,
            config_value: this.configDialogDataConfigValue
          });
        let res = response.data;
        let successFlag = this.handleRes(res);
        if (successFlag) {
          this.configEditDialogClear();
          this.configTableQueryAll();
        }
      } catch (error) {
        console.error(error);
      }
    },

    // 处理响应
    handleRes(res) {
      let bizCode = res.code;
      if ("000000" == bizCode) {
        return true;
      } else {
        this.errSnackbarMsg = res.message;
        this.errSnackbarShowFlag = true;
        return false;
      }
    },

  },

}

const app = createApp(App)
app.use(vuetify).mount('#app')