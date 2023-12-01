<template>
  <p>
    <a-space>
      <a-input
        v-model:value="params.version"
        aria-placeholder="请选择版本"
        allowClear
      ></a-input>
      <a-button type="primary" @click="handleQuery()">查询</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table
    :dataSource="apps"
    :columns="columns"
    :pagination="pagination"
    @change="handleTableChange"
    :loading="loading"
  >
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'operation'">
        <a-space>
          <a-popconfirm
            title="删除后不可恢复，确认删除?"
            @confirm="onDelete(record)"
            ok-text="确认"
            cancel-text="取消"
          >
            <a style="color: red">删除</a>
          </a-popconfirm>
          <a @click="onEdit(record)">编辑</a>
        </a-space>
      </template>
    </template>
  </a-table>

  <a-modal
    v-model:visible="visible"
    title="App添加"
    @ok="handleOk"
    ok-text="确认"
    cancel-text="取消"
  >
    <a-form
      :model="skToken"
      :label-col="{ span: 4 }"
      :wrapper-col="{ span: 20 }"
    >
      <a-form-item label="版本号">
        <a-input v-model:value="app.version" />
      </a-form-item>
      <a-form-item label="更新摘要">
        <a-input v-model:value="app.summary" />
      </a-form-item>
      <a-form-item label="下载地址">
        <a-input v-model:value="app.download" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { notification } from 'ant-design-vue';
import axios from 'axios';

const params = ref({
  version: null
});

const visible = ref(false);
let app = ref({
  id: undefined,
  version: undefined,
  summary: undefined,
  download: undefined,
  createTime: undefined,
  updateTime: undefined
});
const apps = ref([]);
// 分页的三个属性名是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
let loading = ref(false);
const columns = [
  {
    title: '版本号',
    dataIndex: 'version',
    key: 'version'
  },
  {
    title: '更新摘要',
    dataIndex: 'summary',
    key: 'summary'
  },
  {
    title: '下载地址',
    dataIndex: 'download',
    key: 'download'
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
];

const onAdd = () => {
  app.value = {};
  visible.value = true;
};

const onEdit = (record) => {
  app.value = window.Tool.copy(record);
  visible.value = true;
};

const onDelete = (record) => {
  axios.delete('/business/admin/app/delete/' + record.id).then((response) => {
    const data = response.data;
    if (data.success) {
      notification.success({ description: '删除成功！' });
      handleQuery({
        page: pagination.value.current,
        size: pagination.value.pageSize
      });
    } else {
      notification.error({ description: data.message });
    }
  });
};
const handleQuery = (param) => {
  if (!param) {
    param = {
      page: 1,
      size: pagination.value.pageSize
    };
  }
  loading.value = true;
  axios
    .get('/business/admin/app/query-list', {
      params: {
        page: param.page,
        size: param.size,
        version: params.value.version
      }
    })
    .then((response) => {
      loading.value = false;
      let data = response.data;
      if (data.success) {
        apps.value = data.content.list;
        // 设置分页控件的值
        pagination.value.current = param.page;
        pagination.value.total = data.content.total;
      } else {
        notification.error({ description: data.message });
      }
    });
};

const handleOk = () => {
  axios.post('/business/admin/app/save', app.value).then((response) => {
    let data = response.data;
    if (data.success) {
      notification.success({ description: '保存成功！' });
      visible.value = false;
      handleQuery({
        page: pagination.value.current,
        size: pagination.value.pageSize
      });
    } else {
      notification.error({ description: data.message });
    }
  });
};

const handleTableChange = (pagination) => {
  handleQuery({
    page: pagination.current,
    size: pagination.pageSize
  });
};

onMounted(() => {
  handleQuery({
    page: 1,
    size: pagination.value.pageSize
  });
});
</script>
