<script lang="ts">
	import { enhance } from '$app/forms';
	import { auth } from '$lib/auth';
	import { getIdToken, onAuthStateChanged } from 'firebase/auth';
	import type { PageData } from '../$types';
	import { onMount } from 'svelte';
	import { goto } from '$app/navigation';
	let buttonHeight: number;

	export let data: PageData;

	onMount(() => {
		if (data.isLoggedIn) {
			goto("/");
		}
	})
</script>

<svelte:head>
	<title>Login</title>
</svelte:head>

<div class="w-screen h-screen flex items-center justify-center bg-light-900">
	<div>
		<h1 class="font-display text-5xl font-bold text-dark drop-shadow-lg mb-5">Login</h1>
		<div class="flex flex-col gap-4 mb-6">
			<input placeholder="Email" class="text-field w-[400px]" required type="email" />
			<input placeholder="Password" class="text-field w-[400px]" required type="password" />
		</div>
		<form
			class="flex gap-6"
			method="POST"
			use:enhance={async ({ formData }) => {
				const email = formData.get('email')?.toString();
				const password = formData.get('password')?.toString();

				try {
					if (email && password) {
						auth.signIn(email, password).then((userCred) => {
							goto("/");
						}).catch((error) => {
							console.log(error);
						});
					}
				} catch (e) {
					console.log('e');
				}
			}}
		>
			<button
				class="login-btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800 leading-none"
				bind:clientHeight={buttonHeight}
				type="submit">Sign in</button
			>
			<a
				href="/signup"
				style="height: {buttonHeight}px;"
				class="login-btn hover:text-accent2-400 border-dashed border-accent2 hover:border-accent2-400 border-2 box-border transition-colors text-accent2 flex items-center justify-center duration-200"
				>Sign up</a
			>
		</form>
	</div>
</div>
