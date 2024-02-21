export default interface UserData {
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    profileImagePath: string | undefined;
    bio: string | undefined;
    
}

export type userID = string;