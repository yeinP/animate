<template>
<div class="container">
  <div class="bodyContentBody">
    <h3 v-if="!isEditMode">{{ noticeInfo.noticeTitle }}</h3>
    <input v-else type="text" v-model="editedTitle" class="title">



    <ul class="recodeInfo">
      <li><strong>조회수 : </strong>{{ noticeInfo.noticeCount }}</li>
      <li><strong>작성일 : </strong>{{ noticeInfo.noticeRegdate }}</li>
    </ul>

    <div class="imgBox">
      <div class="imgAr" v-if="!isEditMode">
      <div v-if="noticeInfo.noticeFileDto && noticeInfo.noticeFileDto.length > 0 && noticeInfo.noticeFileDto.some(file => file.noticeFileKind === 'F')" class="fileContent">
        <div class="file"><i class="fa fa-folder-open-o" aria-hidden="true"></i><span>첨부파일</span></div>
        <div class="fileBox">
        <div v-for="file in noticeInfo.noticeFileDto" :key="file" >
          <a :href="file.noticeFileUrl" v-if="file.noticeFileKind === 'F'" target="_blank" class="file-link">{{ file.noticeFileName}}</a>
        </div>
        </div>
      </div>
      <div class="ImgContent">
        <div v-for="img in noticeInfo.noticeFileDto" :key="img">
          <img :src="img.noticeFileUrl" alt="Notice Image"  class="img" v-if="img.noticeFileKind === 'I'"/>
        </div>
      </div>
      </div>
      <div class="imgMo" v-else>
        <div v-for="file in noticeInfo.noticeFileDto" :key="file">
          <span>{{file.noticeFileName}}</span>
        </div>
      </div>
    </div>








    <div class="content">
      <div v-if="!isEditMode" v-html="convertLineBreaks(noticeInfo.noticeContent)"></div>
      <textarea v-else v-model="editedContent" class="modifyContent"></textarea>
    </div>
  </div>
  <div class="alter" v-if="isCurrentUserAuthor()">
    <div class="text" v-if="!isEditMode">
      <span @click="toggleEditMode"><i class="fa fa-pencil-square-o fa-2x" aria-hidden="true"></i> 수정</span>
      <button @click="deleteNotice">삭제</button>
    </div>
    <div class="modify" v-else>
      <button @click="modifyBtn">작성</button>
    </div>
  </div>
</div>

</template>

<script>
import { onMounted, ref } from "vue";
import store from "@/scrpits/store";
import axios from "axios";

export default {
  name: 'NoticeArticle',
  props:['noticeList'],
  setup(){
    const userNo = ref(store.state.user.userNo);
    onMounted(()=>{
      store.watch(()=> store.state.user.userNo,(newValue) =>{
        userNo.value = newValue;
      });
    });
    return { userNo };
  },
  data(){
    return{
      noticeNo: null,
      noticeInfo: [],
      isEditMode: false,
      editedTitle: '',
      editedContent: '',
    }
  },
  mounted() {
    this.noticeNo = this.$route.params.noticeNo;
    this.getNoticeArticle(this.noticeNo);
  },
  methods:{
    async getNoticeArticle(noticeNo) {
      try {
        const response = await axios.get(`http://localhost:9090/notice/${noticeNo}`);
        this.noticeInfo = response.data;
        this.editedTitle = response.data.noticeTitle; // 수정된 제목의 기본값 설정
        this.editedContent = response.data.noticeContent; // 수정된 내용의 기본값 설정
        console.log(response.data);
      } catch (error) {
        console.error("Error fetching article:", error);
      }
    },
    toggleEditMode() {
      this.isEditMode = !this.isEditMode;
    },
    convertLineBreaks(text) {
      return text ? text.replace(/\n/g, '<br>') : '';
    },
    isCurrentUserAuthor() {
      return 1 === this.userNo;
    },
    deleteNotice() {
      axios.post(`/animate/notice/delete/${this.noticeNo}`)
          .then(() => {
            console.log('Notice deleted successfully');
          })
          .catch(error => {
            console.error('Error deleting notice:', error);
          });

      function extractFileNameFromUrl(url) {
        const parts = url.split('/');
        return parts[parts.length - 1];
      }

      const fileNames = this.noticeInfo.noticeFileDto.noticeFileUrl.map(url => extractFileNameFromUrl(url));


      axios.delete('/delete/noticeUrl', {
        params: {
          fileName: fileNames
        }
      })
          .then(() => {
            console.log('Files deleted successfully');
          })
          .catch(error => {
            console.error('Error deleting files:', error);
          });
    },
    modifyBtn(){
      axios.post(`/animate/`)

    }
  }

}
</script>

<style scoped>
.bodyContentBody{
  margin-top: 4rem;
  border-top: rgba(204 204 105) solid 2px;
}
h3{
  border-bottom: #d1d3d4 solid 1px;
  background: #f7f9fb;
  font-weight: 600;
  font-size: 20px;
  margin: 0;
  padding: 3px 0;
  text-align: center;
}
ul{
  border-bottom: #d1d3d4 solid 1px;
  display: inline-block;
  width: 100%;
  list-style: none;
  padding-right: 1rem;


}
li{
  padding: 1px 0 1px 30px;
  color: #474747;
  font-size: 15px;
  display: list-item;
  text-align: -webkit-match-parent;
  float: right;

}
.img{
  width: 50%;
  height: 30%;
}
.ImgContent{
  text-align: center;
}
.fileContent{
  display: flex;
  justify-content: flex-end;
  margin: 0 0 1rem 0;
}
i{
  color:#6b6b6b;
  margin: 0 0.3rem 0 0;
}
.file{
  color:#6b6b6b;
  margin: 0 1rem 0 0;
  padding: 10px 0 0 0;
}


.fileBox {
  min-height: 60px;
  padding: 10px 30px 10px 25px;
  border: 1px solid #e0e0e0;
  background-color: #fff;
  box-shadow: 0 1px 2px 0 rgba(0,0,0,.08);
  text-align: left;
  box-sizing: border-box;
}
a{
  cursor: pointer;
  text-decoration: none;
  color:black;
  font-size:15px;
}
.content{
  text-align: center;
  maring: 0 0 2rem 0;
}
.title{
  border: 1px solid #d5d2d2;
  padding: 0.5rem;
  font-size: 1.3rem;
  margin: 10px 0 5px 0;
  width: 100%;
  border-radius: 10px;
}
.modifyContent{
  margin: 1rem 0 0 0;
  width: 100%;
  border: 1px solid #d5d2d2;
  padding: 0.5rem;
  height: 300px;
  overflow-y: auto;
}
.alter{
  display: flex;
  flex-direction: row-reverse;

}
</style>