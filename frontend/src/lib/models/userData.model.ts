export default interface UserData {
	username: string;
	firstName: string;
	lastName: string;
	email: string;
	profileImagePath: string | undefined;
	bio: string | undefined;
	position: Position[];
	uid: UserID;
}

export type UserID = string;

export interface Position {
	title: string;
	description: string | undefined;
}