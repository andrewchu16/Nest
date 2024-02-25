<script lang="ts">
	import { goto } from '$app/navigation';
	import Circle from '$lib/assets/Circle.svelte';
	import Authenticate from '$lib/components/Authenticate.svelte';
	import { userStore } from '$lib/stores/userStore';

	$: screenWidth = 0;
	$: screenHeight = 0;
</script>

<svelte:head>
	<title>Login</title>
</svelte:head>
<svelte:window bind:innerWidth={screenWidth} bind:innerHeight={screenHeight} />

<div class="absolute -left-32 -top-40">
	<Circle radius={500} />
</div>
<div class="absolute right-14 bottom-14">
	<Circle color="#a4c4b5" radius={280} />
</div>
<div class="flex w-screen h-screen justify-center items-center bg-light-900">
	<Authenticate signup={false} />
</div>
