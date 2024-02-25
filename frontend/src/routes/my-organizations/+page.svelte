<script lang="ts">
	import { onMount } from "svelte";
	import Body from "$lib/components/Body.svelte";

    let loading = true;

    onMount(() => {
        loading = false;
    });
</script>

<svelte:head>
    <title>Dashboard</title>
</svelte:head>
<Body>
    <svelte:fragment slot="page-content">
        Dashboard
    </svelte:fragment>
</Body>