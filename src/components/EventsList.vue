<template>
    <div id='eventslist'>
        <table>
            <tr v-for='event in events' :key='event'>
                <td>
                    <router-link :to='/eventdetails/+event.id'>
                        <Event :myevent="event"/>
                    </router-link>
                    
                </td>
            </tr>
        </table>
    </div>
</template>

<style>
    #eventslist{
        display: flex;
        justify-content: center;
    }

    #eventslist table{
        align-self: center;
    }
</style>

<script>
    import Event from './Event.vue';
    import allEvents from '../data/events.js';

    export default{
        name: 'EventsList',
        components: {
            Event
        },
        data(){
            return {
                events: allEvents
            }
        }
    }
</script>
