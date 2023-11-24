<template>
  <p>
    <a-space>
      <a-input v-model:value="params.nickname" allowClear></a-input>
      <a-button type="primary" @click="handleQuery()">查询</a-button>
      <a-button type="primary" @click="onAdd">新增</a-button>
    </a-space>
  </p>
  <a-table
    :dataSource="progammers"
    :columns="columns"
    :pagination="pagination"
    @change="handleTableChange"
    :loading="loading"
  >
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'avatar'">
        <a-avatar :size="64" :src="record.avatar"> </a-avatar>
      </template>
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
    title=""
    @ok="handleOk"
    ok-text="确认"
    cancel-text="取消"
  >
    <a-form
      :model="progammer"
      :label-col="{ span: 4 }"
      :wrapper-col="{ span: 20 }"
    >
      <a-form-item label="账号">
        <a-input v-model:value="progammer.account" />
      </a-form-item>
      <a-form-item label="手机号">
        <a-input v-model:value="progammer.mobile" />
      </a-form-item>
      <a-form-item label="昵称">
        <a-input v-model:value="progammer.nickname" />
      </a-form-item>
      <a-form-item label="头像">
        <a-input v-model:value="progammer.avatar" />
      </a-form-item>
      <a-form-item label="签名">
        <a-input v-model:value="progammer.description" />
      </a-form-item>
      <a-form-item label="主页">
        <a-input v-model:value="progammer.homepage" />
      </a-form-item>
      <a-form-item label="技能">
        <a-input v-model:value="progammer.skill" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { notification } from 'ant-design-vue';
import axios from 'axios';

const visible = ref(false);
let progammer = ref({
  id: undefined,
  account: undefined,
  mobile: undefined,
  nickname: undefined,
  avatar: undefined,
  description: undefined,
  homepage: undefined,
  skill: undefined,
  createTime: undefined,
  updateTime: undefined
});
const progammers = ref([]);
// 分页的三个属性名是固定的
const pagination = ref({
  total: 0,
  current: 1,
  pageSize: 10
});
let loading = ref(false);
const columns = [
  {
    title: '账号',
    dataIndex: 'account',
    key: 'account'
  },
  {
    title: '手机',
    dataIndex: 'mobile',
    key: 'mobile'
  },
  {
    title: '昵称',
    dataIndex: 'nickname',
    key: 'nickname'
  },
  {
    title: '头像',
    dataIndex: 'avatar',
    key: 'avatar'
  },
  {
    title: '签名',
    dataIndex: 'description',
    key: 'description'
  },
  {
    title: '主页',
    dataIndex: 'homepage',
    key: 'homepage'
  },
  {
    title: '技能',
    dataIndex: 'skill',
    key: 'skill'
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
];

const onAdd = () => {
  progammer.value = {};
  visible.value = true;
};

const onEdit = (record) => {
  progammer.value = window.Tool.copy(record);
  visible.value = true;
};

const onDelete = (record) => {
  axios
    .delete('/business/admin/progammer/delete/' + record.id)
    .then((response) => {
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

const handleOk = () => {
  axios
    .post('/business/admin/progammer/save', progammer.value)
    .then((response) => {
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

const params = ref({
  nickname: null
});

const handleQuery = (param) => {
  if (!param) {
    param = {
      page: 1,
      size: pagination.value.pageSize
    };
  }
  loading.value = true;
  axios
    .get('/business/admin/progammer/query-list', {
      params: {
        page: param.page,
        size: param.size,
        nickname: params.value.nickname
      }
    })
    .then((response) => {
      loading.value = false;
      let data = response.data;
      if (data.success) {
        progammers.value = data.content.list;
        // 设置分页控件的值
        pagination.value.current = param.page;
        pagination.value.total = data.content.total;
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
