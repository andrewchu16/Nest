import {
	signInWithEmailAndPassword,
	type Auth,
	type UserCredential,
	createUserWithEmailAndPassword,
	signInWithCustomToken
} from 'firebase/auth';
import type UserData from './models/userData.model';
import { Firestore, setDoc, doc, getDoc } from 'firebase/firestore';
import { firestore } from './firestore';
import { auth as authStore } from './auth';

const createUserManager = () => {
	let db: Firestore;
	let auth: Auth;

	firestore.subscribe((value) => (db = value));
	authStore.subscribe((value) => (auth = value));

	async function login(email: string, password: string): Promise<UserCredential> {
		const userCredential = await signInWithEmailAndPassword(auth, email, password);

		if (!checkUserAccountExists(userCredential.user.uid)) {
			const userData: UserData = {
				username: 'new user',
				firstName: 'auto generated',
				lastName: 'auto generated',
				email: 'generic@email.com'
			};

			createUserAccount(userData, userCredential);
		}
		return userCredential;
	}

	async function loginWithToken(customToken: string): Promise<UserCredential> {
		const userCredential = await signInWithCustomToken(auth, customToken);
		return userCredential;
	}

	async function signUp(userData: UserData, password: string): Promise<UserCredential> {
		const userCredential = await createUserWithEmailAndPassword(auth, userData.email, password);
		createUserAccount(userData, userCredential);
		return userCredential;
	}

	async function createUserAccount(userData: UserData, userCredential: UserCredential) {
		const user = userCredential.user;

		setDoc(doc(db, 'users', user.uid), userData);
	}

	async function checkUserAccountExists(uid: string): Promise<boolean> {
		const docRef = doc(db, `users/${uid}`);
		const docSnap = await getDoc(docRef);

		return docSnap.exists();
	}

	async function getUserAccount(uid: string): Promise<UserData> {
		const docRef = doc(db, `users/${uid}`);
		const docSnap = await getDoc(docRef);

		if (docSnap.exists()) {
			const docData = docSnap.data();
			const userData = {
				username: docData.get('username').toString(),
				firstName: docData.get('firstName').toString(),
				lastName: docData.get('lastName').toString(),
				email: docData.get('email').toString()
			};

			return userData;
		}

		return new Promise<UserData>((resolve, reject) => {
			reject(new Error(`User Doc for ${uid} does not exist.`));
		});
	}

	return {
		login,
		loginWithToken,
		signUp,
		checkUserAccountExists,
		createUserAccount,
		getUserAccount
	};
};

export const userManager = createUserManager();
