<script lang="ts">
	import Navigation from './Navigation.svelte';

	export let navVisible = true;
</script>

<div class="flex w-screen h-screen overflow-x-hidden">
	{#if navVisible}
		<div class="h-screen w-[280px]">
			<div class="fixed h-full w-[280px]">
				<Navigation />
			</div>
		</div>
	{/if}
	<main class="flex-grow">
		<slot name="page-content" />
	</main>
</div>
