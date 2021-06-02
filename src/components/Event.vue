<template>
    <div id='event'>
        <b>Name:</b> <i>{{myevent.name}}</i>
        <br/>
        <b>Date:</b> <i>{{myevent.date}}</i>
        <br/>
        <b>Location:</b> <i>{{myevent.location}}</i>
    </div>
</template>

<script>
    export default{
        name: 'Event',
        props: [
            'myevent'
        ]
    }
</script>