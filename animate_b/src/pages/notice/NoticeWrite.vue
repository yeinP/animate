<template>
  <div class="container">
    <div class="noticeBtn" >
      <button @click="writeNotice" >
        작성하기
      </button>
    </div>
    <div class="p-5 bg-body-tertiary border rounded-3">
      <div class="first">
        <input type="text"  placeholder="제목" class="title" v-model="noticeTitle">
      </div>
      <hr/>
      <div class="selectNotice">
        <span>중요 공지</span>
        <div class="form-check form-switch">
          <input class="form-check-input" type="checkbox" v-model="noticeStatus" true-value="1" false-value="0" id="flexSwitchCheckDefault">
          <label class="form-check-label" for="flexSwitchCheckDefault">선택시 공지사항의 상단에 표시됩니다.</label>
        </div>
      </div>
      <hr/>
      <div class="fileBox">
        <span class="is">파일 선택</span>
        <div class="files">
          <button  @click="openFileSelector" class="i"><i class="fa fa-file-archive-o" aria-hidden="true"></i><span>파일첨부</span></button>
          <input type="file" ref="fileInput" style="display: none" @change="handleFileChange" multiple/>
          <div class="selected-images-container">
            <div v-for="(file, index) in selectedFiles" :key="index" class="selected-image">
              <button @click="removeImage(index)" class="remove-image-button">X</button>
              <span>{{ file.name }}</span>
            </div>
          </div>
        </div>
      </div>
      <hr/>
      <div>

        <textarea type="text" placeholder="본문을 작성해주세요." class="content" v-model="noticeContent"></textarea>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {onMounted, ref} from "vue";
import store from "@/scrpits/store";

export default {
  name: 'NoticeWrite',
  data(){
    return{
      noticeTitle: '',
      noticeContent:'',
      noticeStatus: 0,
      selectedFiles:[],
      noticeFileKind:'',
    }
  },
  setup(){
    const userNo = ref(store.state.user.userNo);
    onMounted(()=>{
      store.watch(()=> store.state.user.userNo,(newValue) =>{
        userNo.value = newValue;
      });
    });
    return { userNo };
  },
  mounted() {

  },
  methods:{
    openFileSelector() {
      this.$refs.fileInput.click();
    },
    handleFileChange(event) {
      const filesArray = Array.from(event.target.files);
      filesArray.forEach(file => {
        const fileObject = {
          name: file.name,
          file: file
        };
        this.selectedFiles.push(fileObject);
      });
    },
    removeImage(index) {
      const updatedSelectedFiles = [...this.selectedFiles];
      updatedSelectedFiles.splice(index, 1);
      this.selectedFiles = updatedSelectedFiles;
    },
    writeNotice(){
      const uploadconfirm = confirm("작성하시겠습니까?");
      if(uploadconfirm == true){
        const formData = new FormData();
        if (this.selectedFiles.length > 0) {
          this.selectedFiles.forEach(file => {
            formData.append(`files`, file.file);
          });
        }
        formData.append('noticeTitle', this.noticeTitle);
        formData.append('noticeContent', this.noticeContent);
        formData.append('noticeStatus', this.noticeStatus);

        axios.post('/animate/upload/notice', formData).then(response => {
          window.alert("작성완료되었습니다.")
          this.$router.push({ path: '/notice'}).then(()=>{
            window.location.reload();
          })
          console.log(formData);
          console.log(response);
        })
            .catch(error =>{
              console.log(error);
            })
      }else {
        return false;
      }
    }

  }
}
</script>


<style scoped>
.noticeBtn{display: flex;
  justify-content: flex-end;
  margin: 0 0 1rem 0;
}
.noticeBtn button{

    width: 120px;
    height: 40px;
    font-size: large;
    border: var(--bs-border-width) var(--bs-border-style) var(--bs-border-color) !important;

}

.rounded-3{
  min-height: 100%;
}
.title{
  font-size: 30px;
  border:0;
  width: 100%;
  height: 70px;
  background-color: rgba(248 249 250);
  color: #858588;
}

input[type="text"]:focus, textarea:focus{
  outline: none;
  border: 0;
}
.content{
  font-size: 20px;
  border:0;
  width: 100%;
  height: 800px;
  background-color: rgba(248 249 250);
  color: #858588;
  text-align: left;
  position: relative;
  word-wrap: break-word;
  white-space: pre-wrap;
}
.first{
  display: flex;
}
.i{
  color:#858588;
  font-size: 16px;
  text-align: center;
  cursor: pointer;
  margin: 0 0 0.5rem 0;
  background-color: #f8f9fa;
  padding: 0.2rem;
  border: #929294 1px solid;
}
 .is {
   width: 13%;
   color: #2d2d2d;
   font-size: 16px;
   text-align:left;
 }
.fileBox{
   display: flex;
}
.files{
  width: 100%;
}
.rounded-3{
  min-height: 100%;
}
.title{
  font-size: 30px;
  border:0;
  width: 100%;
  height: 70px;
  background-color: rgba(248 249 250);
  color: #858588;
}
.i:hover{
  color: black;
}
i{
  width: 25px;

}

input[type="text"]:focus, textarea:focus{
  outline: none;
  border: 0;
}
.content{
  font-size: 20px;
  border:0;
  width: 100%;
  height: 800px;
  background-color: rgba(248 249 250);
  color: #858588;
  text-align: left;
  position: relative;
  word-wrap: break-word;
  white-space: pre-wrap;
}
.first{
  display: flex;
}

.selected-images-container{
  overflow-y: auto;
  height: 100px;
  background-color: white;
  border: #6b6b6b 1px solid;
}
.remove-image-button{
  border: 0;
  background-color: white;
  font-size: 13px;
}
.selectNotice{
  display: flex;
}
.selectNotice span {
  width: 13%;
  color: #2d2d2d;
  font-size: 16px;
  text-align: left;
}
.form-check-input:checked {
  background-color: #cacd66;
  border-color: #cccb65;
}

.form-check-input:focus {
  border-color: #cccb65;
  outline: 0;
  box-shadow: 0 0 0 0.25rem rgba(204,203,101,.25);
}

.form-switch .form-check-input:focus {
  --bs-form-switch-bg: url(data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='-4 -4 8 8'%3e%3ccircle r='3' fill='%23cccb65'/%3e%3c/svg%3e);
}

form-check-label{
  font-size: 14px;
}

</style>