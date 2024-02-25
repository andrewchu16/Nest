<script lang="ts">
	import { goto } from '$app/navigation';
	import { auth, convertFirebaseErrorToString, db } from '$lib/firebase';
	import {
		GoogleAuthProvider,
		signInWithEmailAndPassword,
		createUserWithEmailAndPassword,
		type UserCredential,
		signInWithPopup
	} from 'firebase/auth';
	import { FirebaseError } from 'firebase/app';
	import { doc, setDoc, type DocumentData, DocumentReference, getDoc } from 'firebase/firestore';
	import type UserData from '$lib/models/userData.model';

	export let signup = false;

	let buttonHeight = 52;

	$: errorMessage = '';

	let fullName: string;
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
			await createNewAccountWithEmail();
		} else {
			await loginWithEmailAndPassword();
		}
		waiting = false;
	};

	const loginWithEmailAndPassword = async () => {
		console.log('Logging in with password');
		try {
			await signInWithEmailAndPassword(auth, email, password);
			goto('/');
		} catch (error) {
			console.log(error);
			if (error instanceof FirebaseError) {
				errorMessage = convertFirebaseErrorToString(error);
			}
		}
	};

	const loginWithGoogle = async () => {
		console.log('Logging in with Google');
		try {
			const provider = new GoogleAuthProvider();
			const userCredential: UserCredential = await signInWithPopup(auth, provider);
			const user = userCredential.user;

			// Get name from Google or use a default name.
			const nameParts = user.displayName?.trim().split(/ (?=[^ ]+$)/);
			const [firstName, lastName] = nameParts !== undefined ? nameParts : ['New User', ''];

			const userDocRef: DocumentReference<DocumentData, DocumentData> = doc(db, 'users', user.uid);
			const userDocSnap = await getDoc(userDocRef);

			// Create a new user account if doesn't exist yet.
			if (!userDocSnap.exists()) {
				const userData: UserData = {
					firstName: firstName,
					lastName: lastName,
					email: user.email ? user.email : '',
					profileImagePath: null,
					bio: null,
					positions: [{ title: 'No Position', description: null, organization: null }],
					uid: user.uid
				};
				await setDoc(userDocRef, userData);
			}

			// Redirect to dashboard.
			goto('/');
		} catch (error) {
			console.log(error);
			if (error instanceof FirebaseError) {
				errorMessage = convertFirebaseErrorToString(error);
			}
		}
	};

	const createNewAccountWithEmail = async () => {
		console.log('signing up');
		// Ensure the correct password was inputted.
		if (password !== confirmPassword) {
			errorMessage = 'Passwords do not match.';
			return;
		}

		const nameParts = fullName.trim().split(/ (?=[^ ]+$)/);
		// Ensure full name is provided.
		if (nameParts.length != 2) {
			errorMessage = 'Please enter a first and last name.';
			return;
		}

		// If the name given has as middle name, it will be added to the first name.
		const [firstName, lastName] = nameParts;
		console.log(firstName, lastName);

		try {
			const userCredential = await createUserWithEmailAndPassword(auth, email, password);

			// Create user account.
			const uid = userCredential.user.uid;
			const userData: UserData = {
				firstName: firstName,
				lastName: lastName,
				email: email,
				profileImagePath: null,
				bio: null,
				positions: [{ title: 'No Position', description: null, organization: null }],
				uid: uid
			};

			await setDoc(doc(db, 'users', uid), userData);
		} catch (error) {
			if (error instanceof FirebaseError) {
				console.log(error);
				errorMessage = convertFirebaseErrorToString(error);
			} else {
				console.log(error);
				errorMessage = 'An unknown error occured.';
			}
		}

		goto('/');
	};
</script>

<div class="flex flex-col items-center justify-center bg-light-900 gap-6">
	<form class="flex flex-col gap-6" on:submit|preventDefault={submitForm}>
		<h1 class="font-display text-5xl font-bold text-dark drop-shadow-lg">
			{#if signup}
				Sign Up
			{:else}
				Login
			{/if}
		</h1>
		<!-- Login/Sign up fields -->
		<div class="flex flex-col gap-4 mb-3 w-[400px]">
			{#if signup}
				<input
					placeholder="Name"
					class="login-text-field flex-grow"
					required
					type="text"
					name="name"
					bind:value={fullName}
				/>
			{/if}
			<input
				placeholder="Email"
				class="login-text-field flex-grow"
				required
				type="email"
				name="email"
				bind:value={email}
			/>
			<input
				placeholder="Password"
				class="login-text-field flex-grow"
				required
				type="password"
				name="password"
				bind:value={password}
			/>
			{#if signup}
				<input
					placeholder="Confirm Password"
					class="login-text-field flex-grow"
					required
					type="password"
					name="confirmPassword"
					bind:value={confirmPassword}
				/>
			{/if}
		</div>
		<div class="flex gap-6">
			<!-- Submit login/sign up buttons -->
			{#if signup}
				<button
					class="login-btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800 leading-none"
					type="submit">Sign up</button
				>
			{:else}
				<button
					class="login-btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800 leading-none"
					type="submit">Sign in</button
				>
			{/if}

			<!-- Switch login/sign up -->
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
			<div
				class="bg-light-400 border-light-300 border-2 rounded-lg px-7 py-3 text-light-100 drop-shadow-lg"
			>
				{errorMessage}
			</div>
		{/if}
		<!-- Auth 2.0 Providers -->
		<p
			class="flex items-center gap-4 before:flex-grow before:inline-block before:h-[2px] before:bg-accent3-900 text-accent3-800 after:flex-grow after:inline-block after:h-[2px] after:bg-accent3-900"
		>
			Or
		</p>
	</form>
	<button
		on:click={loginWithGoogle}
		class="text-accent3-700 border-accent3-700 border-2 px-7 py-3 rounded-xl hover:text-accent3-600 hover:border-accent3-600 shadow-sm hover:shadow-lg"
	>
		{#if signup}
			Sign up with Google
		{:else}
			Log in with Google
		{/if}</button
	>
</div>
