import type { userID } from "./userData.model";

export default interface Organization {
    name: string;
    owners: userID[];
    members: userID[];
    thumbnail: string | undefined;
}

export type orgID = string;