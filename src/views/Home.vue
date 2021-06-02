<template>
  <div class="home">
    <h2>Welcome</h2>
    <br/>
    <button @click="signIn()">Sign in</button>
    <br/>
    <EventsList></EventsList>
  
  <modal name="loginModal">
    <div class="loginDiv">
      <button class="button" @click="$modal.hide('loginModal')">X</button>
      
      <table>
        <tr>
          <td>
            Username:
          </td>
          <td>
            <input type="text" name="username" v-model="username">
          </td>
        </tr>
        <tr>
          <td>
            Password:
          </td>
          <td>
            <input type="password" name="password" v-model="password">
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <button @click="findUser()">Sign in</button>
          </td>
        </tr>
      </table>
    </div>
  </modal>
  
  </div>



</template>

<script>
  import EventsList from '../components/EventsList.vue';
  import users from "../data/users.js"
  export default{
    name: 'Home',
    components: {
      EventsList
    },
    methods:{
      signIn(){
        this.$modal.show("loginModal")
      },
      findUser(){
        // alert(this.username);
        var user=users.find(user=>user.username==this.username && user.password==this.password);
        if(user)
        {
          alert("OK")
        }
        else
        {
          alert("No user")
        }

      }
    },
    data(){
      return {
        username:"",
        password:""
      }
    }
  }
</script>

<style>
.loginDiv{
  height:100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  
  
}
.button{
  position: absolute;
  right:0;
  top:0;
}
</style>