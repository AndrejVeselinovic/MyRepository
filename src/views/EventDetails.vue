<template>
    <div id='eventdetails'>
        <h2>{{event.name}}</h2>
        <br/>
        <img :src="'/images/' +event.picture">
    </div>
</template>

<script>
    import allEvents from '../data/events.js'

    export default{
        name: 'EventDetails',
        mounted(){
            var id = Number(this.$route.params.id);
            this.event = this.events.find(event=>event.id==id);
        },
        data(){
            return {
                event: {},
                events: allEvents
            }
        }
    }
</script>