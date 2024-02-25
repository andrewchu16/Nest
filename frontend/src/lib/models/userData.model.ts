export default interface UserData {
	firstName: string;
	lastName: string;
	email: string;
	profileImagePath: string | null;
	bio: string | null;
	positions: Position[];
	uid: UserID;
}

export type UserID = string;

export interface Position {
	title: string;
	description: string | null;
}