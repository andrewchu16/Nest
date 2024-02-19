<script lang="ts">
	import { page } from '$app/stores';
</script>

{#if $page.error}
	<h1 class="flex justify-center items-center flex-grow text-accent1">{$page.status}: {$page.error.message}</h1>
{:else}
    <h1 class="flex justify-center items-center flex-grow text-accent1">Error {$page.status}</h1>
{/if}
