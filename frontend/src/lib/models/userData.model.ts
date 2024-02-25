import type { OrgID } from "./organization.model";

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
	organization: OrgID | null;
	description: string | null;
}