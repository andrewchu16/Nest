<script lang="ts">
	import { onMount } from "svelte";
	import Body from "$lib/components/Body.svelte";

    let loading = true;

    onMount(() => {
        loading = false;
    });
</script>

<svelte:head>
    <title>My Projects</title>
</svelte:head>
<Body>
    <svelte:fragment slot="page-content">
        My projects
    </svelte:fragment>
</Body>