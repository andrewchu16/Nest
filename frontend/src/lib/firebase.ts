import { dev } from '$app/environment';
import {
	getApps,
	type FirebaseOptions,
	initializeApp,
	getApp,
	deleteApp,
	type FirebaseError
} from 'firebase/app';
import {
	PUBLIC_FIREBASE_API_KEY,
	PUBLIC_FIREBASE_AUTH_DOMAIN,
	PUBLIC_FIREBASE_PROJECT_ID,
	PUBLIC_FIREBASE_STORAGE_BUCKET,
	PUBLIC_FIREBASE_MESSAGE_SENDER_ID,
	PUBLIC_FIREBASE_APP_ID
} from '$env/static/public';
import { connectFirestoreEmulator, getFirestore } from 'firebase/firestore';
import { connectAuthEmulator, getAuth, type Persistence } from 'firebase/auth';
import { connectStorageEmulator, getStorage } from 'firebase/storage';

const firebaseConfig: FirebaseOptions = {
	apiKey: PUBLIC_FIREBASE_API_KEY,
	authDomain: PUBLIC_FIREBASE_AUTH_DOMAIN,
	projectId: PUBLIC_FIREBASE_PROJECT_ID,
	storageBucket: PUBLIC_FIREBASE_STORAGE_BUCKET,
	messagingSenderId: PUBLIC_FIREBASE_MESSAGE_SENDER_ID,
	appId: PUBLIC_FIREBASE_APP_ID
};

const createApp = () => {
	console.log('creating app');
	let app;
	if (getApps().length) {
		app = getApp();
		deleteApp(app);
	}
	app = initializeApp(firebaseConfig);

	return app;
};

const createFirestore = () => {
	console.log('creating firestore');
	const firestore = getFirestore(app);

	if (dev) {
		connectFirestoreEmulator(firestore, 'localhost', 8080);
	}

	return firestore;
};

const createAuth = () => {
	console.log('creating auth');
	const auth = getAuth(app);

	if (dev) {
		connectAuthEmulator(auth, 'http://localhost:9099');
	}

	auth.setPersistence({ type: 'SESSION' });

	return auth;
};

const createStorage = () => {
	console.log('creating firebase storage');
	const storage = getStorage(app);

	if (dev) {
		connectStorageEmulator(storage, 'localhost', 9199);
	}
};

export const convertFirebaseErrorToString = (error: FirebaseError): string => {
	switch (error.code) {
		case 'auth/invalid-credential':
			return 'Invalid email/password.';
		case 'auth/user-not-found':
			return 'Account not found.';
		case 'auth/wrong-password':
			return 'Wrong password.';
		case 'auth/popup-closed-by-user':
			return 'Login popup closed.';
	}
	return error.code;
};

export const app = createApp();
export const db = createFirestore();
export const auth = createAuth();
export const storage = createStorage();
