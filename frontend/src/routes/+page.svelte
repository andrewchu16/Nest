<script lang="ts">
	import { onMount } from "svelte";
	import { userStore } from "../stores/userStore";

    let loading = true;

    onMount(() => {
        loading = false;
    });
</script>

<svelte:head>
    <title>Dashboard</title>
</svelte:head>
dashboard