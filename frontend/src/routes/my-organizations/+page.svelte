<script lang="ts">
	import { onMount } from "svelte";
	import Body from "$lib/components/Body.svelte";

    let loading = true;

    onMount(() => {
        loading = false;
    });
</script>

<svelte:head>
    <title>My Organizations</title>
</svelte:head>
<Body>
    <svelte:fragment slot="page-content">
        My organizations
    </svelte:fragment>
</Body>