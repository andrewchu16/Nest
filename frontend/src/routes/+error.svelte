<script lang="ts">
	import { page } from '$app/stores';
	import { redirect } from '@sveltejs/kit';
</script>

{#if $page.error}
	<h1 class="flex justify-center items-center flex-grow text-accent1">
		Error {$page.status}: {$page.error.message}
	</h1>
{:else}
	<h1 class="flex justify-center items-center flex-grow text-accent1">{$page.status}</h1>
{/if}
