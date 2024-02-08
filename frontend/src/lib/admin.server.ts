import { initializeApp } from 'firebase-admin/app';
import { getAuth } from 'firebase-admin/auth';

export const app = initializeApp({ projectId: 'nest-cnlc' });
export const auth = getAuth(app);