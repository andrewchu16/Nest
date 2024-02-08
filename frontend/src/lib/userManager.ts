import type { UserCredential } from 'firebase/auth';
import type UserData from './models/userData.model';
import { Firestore, setDoc, doc, getDoc } from 'firebase/firestore';
import { firestore } from './firestore';
import { auth } from './auth';

const createUserManager = () => {
	let db: Firestore;

	firestore.subscribe((value) => (db = value));

	async function login(email: string, password: string) {
		auth.signIn(email, password).then((userCredential: UserCredential) => {
			if (!checkUserAccountExists(userCredential.user.uid)) {
				const userData: UserData = {
					username: "new user",
					firstName: "auto generated",
					lastName: "auto generated",
					email: "generic@email.com"
				};

				createUserAccount(userData, userCredential);
			}
		}).catch((e) => {
			console.log(`Invalid email/password email=${email} password=${password}` + e);
		});

		
	}

	async function signUp(userData: UserData, password: string) {
		auth.signUp(userData.email, password).then((userCredential: UserCredential) => {
			createUserAccount(userData, userCredential);
		});
	}

	async function createUserAccount(userData: UserData, userCredential: UserCredential) {
			const user = userCredential.user;

			setDoc(doc(db, 'users', user.uid), userData);
	}

	async function checkUserAccountExists(uid: string): Promise<boolean> {
		const docRef = doc(db, `users/${uid}`);
		const docSnap = await getDoc(docRef);

		return new Promise<boolean>((resolve) => resolve(docSnap.exists()));
	}

	async function getUserAccount(uid: string): Promise<UserData> {
		const docRef = doc(db, `users/${uid}`);
		const docSnap = await getDoc(docRef);
		
		if (docSnap.exists()) {
			const docData = docSnap.data();
			const userData = {
				username: docData.get("username").toString(),
				firstName: docData.get("firstName").toString(),
				lastName: docData.get("lastName").toString(),
				email: docData.get("email").toString()
			}

			return new Promise<UserData>((resolve) => {
				resolve(userData);
			})
		}

		return new Promise<UserData>((resolve, reject) => {
			reject(new Error(`User Doc for ${uid} does not exist.`));
		})
	}

	return {
		login,
		signUp,
		checkUserAccountExists,
		createUserAccount,
		getUserAccount
	};
};

export const userManager = createUserManager();
