<template>
<div class="container">
  <div class="writeBtn" v-if="userNo == 1"><button @click="write" class="noticeBtn"><i class="fa fa-upload" aria-hidden="true"></i>공지 작성</button></div>
  <table class="table">
    <thead class="table-light">
      <tr>
        <td>no.</td>
        <td>제목</td>
        <td>작성일</td>
        <td>조회수</td>
      </tr>
    </thead>
    <tbody>
    <tr v-for="top in noticeTopList" :key="top" class="top">
      <td style="width: 10%; text-align: center">공지</td>
      <td  style="width:50%" @click="goArticle(top)">{{top.noticeTitle}}</td>
      <td  style="width: 30%; text-align: center">{{formattedDate(top.noticeRegdate)}}</td>
      <td  style="width: 10%; text-align: center">{{top.noticeCount}}</td>
    </tr>
    <tr v-for="(notice, index) in pagenoticeList" :key="index">
      <td style="width: 10%; text-align: center">{{ noticeList.length - index }}</td>
        <td  style="width:50%; cursor: pointer"  @click="goArticle(notice)">{{notice.noticeTitle}}</td>
        <td  style="width: 30%; text-align: center">{{formattedDate(notice.noticeRegdate)}}</td>
        <td  style="width: 10%; text-align: center">{{notice.noticeCount}}</td>
      </tr>
    </tbody>
  </table>
  <div class="pagination d-flex justify-content-center">
    <button @click="prevPage" :disabled="currentPage === 1">
      <i class="fa fa-chevron-left" aria-hidden="true"></i>
    </button>
    <span v-for="page in totalPages" :key="page" @click="goToPage(page)" :class="{ active: page === currentPage }" style="margin: 0 5px; cursor: pointer">{{ page }}</span>
    <button @click="nextPage" :disabled="currentPage * itemsPerPage >= noticeList.length">
      <i class="fa fa-chevron-right" aria-hidden="true"></i>
    </button>
  </div>
</div>
</template>

<script>
import router from "@/scrpits/router";
import axios from "axios";
import moment from "moment";
import {onMounted, ref} from "vue";
import store from "@/scrpits/store";

export default {
  name: 'Notice',
  setup(){
    const userNo = ref(store.state.user.userNo);
    onMounted(()=>{
      store.watch(()=> store.state.user.userNo,(newValue) =>{
        userNo.value = newValue;
      });
    });
    const formattedDate = (date) => {
      return moment(date).format('YYYY.MM.DD');
    };
    return { userNo, formattedDate };
  },
  data(){
    return{
      noticeList:[],
      noticeTopList:[],
      currentPage: 1,
      itemsPerPage: 10,
      totalPages:0,
    }
  },
  computed:{

    pagenoticeList(){
      const start = (this.currentPage -1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.noticeList.slice(start, end);
    }

  },
  methods:{
    nextPage() {
      if (this.currentPage * this.itemsPerPage < this.noticeList.length) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    goToPage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
      }
    },
    write(){
      router.push({path: '/notice/write'})
    },
    getNoticeList(){
      axios.get('http://localhost:9090/notice/list')
          .then((response) => {
            this.noticeList = response.data;
            this.totalPages = Math.ceil(this.noticeList.length / this.itemsPerPage);
            if (this.currentPage > this.totalPages) {
              this.currentPage = this.totalPages;
            }
          }).catch((error)=>{
        console.error("데이터 오류" , error);
      })

    },
    getNoticeTopList(){
      axios.get('http://localhost:9090/notice/list/top')
          .then((response) => {
            this.noticeTopList = response.data;

          }).catch((error)=>{
        console.error("top 오류" , error);
      })
    },
    goArticle(notice){
      axios.post(`/animate/notice/${notice.noticeNo}/increaseCount`)
          .then(() => {
            router.push({
              name: 'NoticeArticle',
              params: { noticeNo: notice.noticeNo }
            });
          })
          .catch(error => {
            console.error("조회수 증가 오류", error);
          });


    }

  },

  mounted() {
    this.getNoticeList();
    this.getNoticeTopList();
  },

}
</script>
<style scoped>
.writeBtn{
  display: flex;
  flex-direction: row-reverse;
  margin-bottom: 10px;
}


.noticeBtn {
  width: 180px;
  height: 35px;
  background-color: rgba(205, 205, 103, 1);
  border: 0;
  color: #fff;
  border-radius: 4px;
  font-weight: bold;
}
i{
  margin: 0 0.3rem 0 0;
}
thead{
  text-align: center;
}
.top{
  font-size: 18px;
  font-weight: bold;

}
.top td{
  background-color: rgba(252, 250, 215, 0.65);
}
.table-light td{
  background-color: #b7b7b7;
  color: white;
}
.pagination button{
  background-color: white;
  border: white;
}
.fa {
  display: inline-block;
  font: normal normal normal 14px / 1 FontAwesome;
  font-size: inherit;
  text-rendering: auto;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.table{
  margin: 5rem 0 5rem 0;
}
.justify-content-center{
  margin: 0 0 5rem 0;
}
</style>