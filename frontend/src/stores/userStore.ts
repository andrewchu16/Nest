import type UserData from '$lib/models/userData.model';
import { type User } from 'firebase/auth';
// import { dedupe } from './dedupe';
import { writable } from 'svelte/store';

export interface UserStoreData {
	user: User | null;
	data: UserData | null;
}

// export const userStore = dedupe<UserStoreData>(readable<UserStoreData>({
//     user: null,
//     data: null
// }));

export const userStore = writable<UserStoreData>({
	user: null,
	data: null
});
