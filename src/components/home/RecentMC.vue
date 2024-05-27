<template>

    <div class="row g-0 border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
      <div class="col p-4 d-flex flex-column position-static">
        <strong class="d-inline-block mb-2 msStatusBox" disabled :class="msStatusClass">{{ mcStatusLabel }}</strong>

        <h6 class="mb-0"> {{recentmc.mcBreed}} </h6>
        <span><i class="fa fa-map-marker"></i>{{recentmc.mcLoc}}</span>
        <div class="mb-1 text-body-secondary">{{formattedDate}}</div>
        <p class="card-text mb-auto"></p>
        <span class="icon-link gap-1 icon-link-hover stretched-link" >
        더보기
        <svg class="bi"><use xlink:href="#chevron-right"/></svg>
      </span>
      </div>
      <div class="col-auto d-none d-lg-block">
        <svg class="bd-placeholder-img" width="200" height="250" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
          <rect width="100%" height="100%" fill="#ced4da"/>
          <image :href="recentmc.mcImgUrl" x="10%"  width="80%" height="100%" preserveAspectRatio="xMidYMid slice"></image>
        </svg>
      </div>
    </div>
</template>

<script>
import {onMounted, ref} from "vue";
import store from "@/scrpits/store";
import moment from "moment/moment";


export default {
  name:'RecentMC',
  props:['recentmc'],
  data(){
    return{
      showModal: false
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
  methods:{
  },
  computed:{
    formattedDate(){
      return moment(this.recentmc.mcRegdate).format('YYYY.MM.DD');
    },
    msStatusClass() {
      switch (this.recentmc.mcStatus) {
        case 1:
          return "btn-miss";
        case 2:
          return "btn-care";
        default:
          return "btn-comp";
      }
    },
    mcStatusLabel(){
      switch (this.recentmc.mcStatus){
        case 1:
          return "실종";
        case 2:
          return "제보";
        default:
          return "완료";
      }
    },

  }
}

</script>

<style scoped>
.btn-miss{
  width: 50px;
  height: 25px;
  text-align: center;
  border-radius: 0.5rem;
  border-color: #ff5656;
  background-color: #ff5656;
  color: white;
  font-weight: 600;
  margin-right: 10px;
}

.btn-care{
  width: 50px;
  height: 25px;
  text-align: center;
  border-radius: 0.5rem;
  border-color: #e7c300;
  background-color: #e7c300;
  color: #ffffff;
  font-weight: 600;
  margin-right: 10px;
}
.btn-comp{
  width: 50px;
  height: 25px;
  text-align: center;
  border-radius: 0.5rem;
  border-color: #696969;
  background-color: #696969;
  color: #ffffff;
  font-weight: 600;
  margin-right: 10px;
}

</style>