<script lang="ts">
	import { goto } from "$app/navigation";
	import { auth } from "$lib/firebase";
	import { signInWithEmailAndPassword } from "firebase/auth";

	export let signup = false;

	let buttonHeight: number;

	let errorMessage = '';

	let firstName: string;
	let lastName: string;
	let userName: string;
	let email: string;
	let password: string;
	let confirmPassword: string;
    let waiting = false;

	const submitForm = async () => {
        if (waiting) {
            return;
        }

        waiting = true;
		if (signup) {
			await createNewAccount();
		} else {
			await login();
		}
        waiting = false;
	};

	const login = async () => {
        console.log("logging in");
        try {
            const userCredential = await signInWithEmailAndPassword(auth, email, password);
            goto("/");
        } catch (err) {
            console.log(err);
            if (err === "auth/invalid-credential") {
                errorMessage = "Invalid email/password.";
            }
        }
    };

	const createNewAccount = async () => {
        console.log("signing up");
        if (password !== confirmPassword) {
            errorMessage = "Passwords do not match.";
            return;
        }
    };
</script>

<div class="w-screen h-screen flex items-center justify-center bg-light-900">
	<form class="flex flex-col gap-6" on:submit|preventDefault={submitForm}>
		<h1 class="font-display text-5xl font-bold text-dark drop-shadow-lg">
			{#if signup}
				Sign Up
			{:else}
				Login
			{/if}
		</h1>
		<div class="flex flex-col gap-4 mb-3 w-[400px]">
			{#if signup}
				<input
					placeholder="First Name"
					class="text-field flex-grow"
					required
					type="text"
					name="first-name"
					bind:value={firstName}
				/>
				<input
					placeholder="Last Name"
					class="text-field flex-grow"
					required
					type="text"
					name="lastname"
					bind:value={lastName}
				/>
			{/if}
			<input
				placeholder="Email"
				class="text-field flex-grow"
				required
				type="email"
				name="lastname"
				bind:value={email}
			/>
			<input
				placeholder="Password"
				class="text-field flex-grow"
				required
				type="password"
				name="password"
				bind:value={password}
			/>
			{#if signup}
				<input
					placeholder="Confirm Password"
					class="text-field flex-grow"
					required
					type="password"
					name="confirmPassword"
					bind:value={confirmPassword}
				/>
			{/if}
		</div>
		<div class="flex gap-6">
			{#if signup}
				<button
					class="login-btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800 leading-none"
					bind:clientHeight={buttonHeight}
					type="submit">Sign up</button
				>
			{:else}
				<button
					class="login-btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800 leading-none"
					bind:clientHeight={buttonHeight}
					type="submit">Sign in</button
				>
			{/if}
			{#if signup}
				<a
					href="/login"
					style="height: {buttonHeight}px;"
					class="login-btn hover:text-accent2-400 border-dashed border-accent2 hover:border-accent2-400 border-2 box-border transition-colors text-accent2 flex items-center justify-center duration-200"
					>Have an account?</a
				>
			{:else}
				<a
					href="/signup"
					style="height: {buttonHeight}px;"
					class="login-btn hover:text-accent2-400 border-dashed border-accent2 hover:border-accent2-400 border-2 box-border transition-colors text-accent2 flex items-center justify-center duration-200"
					>Sign up?</a
				>
			{/if}
		</div>
		{#if errorMessage}
			<div class="bg-red-300 border-red-600 border-2 rounded-lg">
				Error: {errorMessage}
			</div>
		{/if}
	</form>
</div>
