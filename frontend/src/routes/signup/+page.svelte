<script>
	import Authenticate from '$lib/components/Authenticate.svelte';
	import Circle from '$lib/assets/Circle.svelte';
	import { userStore } from '$lib/stores/userStore';
	import { goto } from '$app/navigation';

	$: if ($userStore.user !== null) {
		goto("/");
	}
</script>

<svelte:head>
	<title>Sign Up</title>
</svelte:head>

<div class="absolute -left-32 -top-40">
	<Circle color="#475d3c" radius={500} />
</div>
<div class="absolute right-14 bottom-14">
	<Circle color="#f5ecf1" radius={280} />
</div>
<div class="flex w-screen h-screen justify-center items-center bg-light-900">
	<Authenticate signup={true} />
</div>
