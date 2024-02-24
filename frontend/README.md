# Nest!
Create a program that allows your school’s Career and Technical Education Department to collect and store information about business and community partners. This program should include information on at least 25 different partners (real or fictional), with details such as, but not limited to, the type of organization, resources available, and direct contact information for an individual. The program should enable users to search and filter the information as needed.

## Required Features
- documentation
- commenting
- dynamic data backup
- intelligent feature (interactive Q&A, recommended feed)
- results update dynamically?
- output report
- 25 different partners
- type of org, resources available, contact info
- search
- input validation

## Running
1. Create a new Firebase project 
2. Add a web app to the project
3. Create a .env file in the "frontend" directory in this format:

```bash
PUBLIC_FIREBASE_API_KEY=""
PUBLIC_FIREBASE_AUTH_DOMAIN=""
PUBLIC_FIREBASE_PROJECT_ID=""
PUBLIC_FIREBASE_STORAGE_BUCKET=""
PUBLIC_FIREBASE_MESSAGE_SENDER_ID=""
PUBLIC_FIREBASE_APP_ID=""
```

4. Run `npm run dev` in the "frontend" directory.