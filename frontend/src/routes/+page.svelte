<script lang="ts">
	let buttonHeight: number;
	$: console.log(buttonHeight);
</script>

<svelte:head>
	<title>Nestwork Landing Page</title>
</svelte:head>

<div class="w-screen h-screen flex flex-col justify-center items-center bg-light-900">
	<h1 class="font-display font-bold text-8xl text-dark-600 mb-8 text-center">
		Welcome to <span class="text-grad-1">Nestwork</span>.
	</h1>
	<div class="flex items-center gap-7">
		<a
			href="/login"
			class="landing-btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800"
			bind:clientHeight={buttonHeight}>Open Nestwork</a
		>
		<a
			href="/signup"
			style="height: {buttonHeight}px;"
			class="landing-btn hover:text-accent2-400 border-dashed border-accent2 hover:border-accent2-400 border-2 box-border transition-colors text-accent2 flex items-center justify-center duration-200"
			>Sign up</a
		>
	</div>
</div>
